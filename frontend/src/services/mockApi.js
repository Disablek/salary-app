// Начальные данные (чтобы сразу был админ)
const INITIAL_USERS = [
    {
        id: '123e4567-e89b-12d3-a456-426614174000',
        username: 'admin',
        email: 'admin@bntu.by',
        firstName: 'Super',
        lastName: 'User',
        surName: 'Adminovich',
        password: 'password', // В реале хешируем, тут храним так
        rolesId: [], // В DTO ролей нет, но мы имитируем роль строкой для простоты
        role: 'ROLE_SUPERUSER' // Доп поле для мока
    },
    {
        id: '223e4567-e89b-12d3-a456-426614174001',
        username: 'user',
        email: 'user@bntu.by',
        firstName: 'Ivan',
        lastName: 'Ivanov',
        surName: 'Ivanovich',
        password: 'password',
        rolesId: [],
        role: 'ROLE_USER'
    }
];

const LATENCY = 500; // имитация задержки сети

// Помощник для работы с localStorage
const getDb = () => {
    const users = localStorage.getItem('users');
    return users ? JSON.parse(users) : INITIAL_USERS;
};

const saveDb = (users) => {
    localStorage.setItem('users', JSON.stringify(users));
};


export const mockApi = {
    login: async (email, password) => {
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                const users = getDb();
                const user = users.find(u => u.email === email && u.password === password);
                if (user) {
                    const { password, ...userDtoOutput } = user;
                    resolve({ user: userDtoOutput, token: 'fake-jwt-token' });
                } else {
                    reject('Неверный email или пароль');
                }
            }, LATENCY);
        });
    },

    register: async (userDtoInput) => {
        return new Promise((resolve) => {
            setTimeout(() => {
                const users = getDb();
                const newUser = {
                    ...userDtoInput,
                    id: crypto.randomUUID(),
                    role: 'ROLE_USER'
                };
                users.push(newUser);
                saveDb(users);
                const { password, ...output } = newUser;
                resolve(output);
            }, LATENCY);
        });
    },

    getCurrentUser: async () => {
        const stored = localStorage.getItem('currentUser');
        return stored ? JSON.parse(stored) : null;
    },

    getAllUsers: async () => {
        return new Promise((resolve) => {
            setTimeout(() => {
                const users = getDb();
                const output = users.map(({ password, ...u }) => u);
                resolve(output);
            }, LATENCY);
        });
    },

    updateUser: async (id, userDtoInput) => {
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                const users = getDb();
                const index = users.findIndex(u => u.id === id);
                if (index !== -1) {
                    // Сохраняем старый пароль, так как userDtoInput может быть без него
                    const oldPassword = users[index].password;
                    const updatedUser = {
                        ...users[index],
                        ...userDtoInput,
                        password: oldPassword // Пароль меняется отдельным методом
                    };

                    users[index] = updatedUser;
                    saveDb(users);

                    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
                    if (currentUser && currentUser.id === id) {
                        const { password, ...safeUser } = updatedUser;
                        localStorage.setItem('currentUser', JSON.stringify(safeUser));
                    }

                    const { password, ...output } = updatedUser;
                    resolve(output);
                } else {
                    reject('Пользователь не найден');
                }
            }, LATENCY);
        });
    },

    // Новый метод для смены пароля
    changePassword: async (userId, oldPassword, newPassword) => {
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                const users = getDb();
                const index = users.findIndex(u => u.id === userId);
                if (index === -1) return reject('Пользователь не найден');

                if (users[index].password !== oldPassword) {
                    return reject('Старый пароль неверен');
                }

                users[index].password = newPassword;
                saveDb(users);
                resolve({ success: true });
            }, LATENCY);
        });
    },

    deleteUser: async (id) => {
        const users = getDb().filter(u => u.id !== id);
        saveDb(users);
        return Promise.resolve();
    },

    // --- DATA TABLES API ---
    getDataTables: async () => {
        const tables = JSON.parse(localStorage.getItem('datatables') || '[]');
        return Promise.resolve(tables);
    },

    getDataTableById: async (id) => {
        const tables = JSON.parse(localStorage.getItem('datatables') || '[]');
        const table = tables.find(t => t.id === id);
        return Promise.resolve(table);
    },

    createDataTable: async (dto) => {
        const tables = JSON.parse(localStorage.getItem('datatables') || '[]');
        const newTable = { ...dto, id: crypto.randomUUID() };
        tables.push(newTable);
        localStorage.setItem('datatables', JSON.stringify(tables));
        return Promise.resolve(newTable);
    },

    // --- COLUMNS API ---
    getColumns: async (tableId, page) => {
        const columns = JSON.parse(localStorage.getItem('columns') || '[]');
        // Фильтр по таблице
        let filtered = columns.filter(c => c.dataTable_id === tableId);
        // Фильтр по странице (упрощенная логика 0 - везде, 1 - стр1, 2 - стр2)
        if (page) {
            filtered = filtered.filter(c => c.activeInPage === 0 || c.activeInPage === page);
        }
        return Promise.resolve(filtered);
    },

    createColumn: async (dto) => {
        const columns = JSON.parse(localStorage.getItem('columns') || '[]');
        const newCol = { ...dto, id: crypto.randomUUID() };
        columns.push(newCol);
        localStorage.setItem('columns', JSON.stringify(columns));
        return Promise.resolve(newCol);
    },

    // --- ROWS API ---
    getRows: async (tableId) => {
        const rows = JSON.parse(localStorage.getItem('rows') || '[]');
        // Здесь мы должны бы подтянуть данные User/Employee, но для мока вернем как есть
        return Promise.resolve(rows.filter(r => r.tableId === tableId));
    },

    createRow: async (dto) => {
        const rows = JSON.parse(localStorage.getItem('rows') || '[]');
        const newRow = { ...dto, id: crypto.randomUUID() };
        rows.push(newRow);
        localStorage.setItem('rows', JSON.stringify(rows));
        return Promise.resolve(newRow);
    },

    // --- CELLS API ---
    getCells: async (tableId, page) => {
        const cells = JSON.parse(localStorage.getItem('cells') || '[]');
        // В реальном бэке фильтрация сложнее, тут просто вернем все для таблицы
        // (В моке сложно фильтровать ячейки по странице без Join, вернем все)
        return Promise.resolve(cells);
    },

    saveCell: async (dto) => {
        let cells = JSON.parse(localStorage.getItem('cells') || '[]');
        // Если ячейка уже есть (по ID или по row+col), обновляем
        const index = cells.findIndex(c => c.id === dto.id || (c.row_id === dto.row_id && c.column_id === dto.column_id));

        let savedCell;
        if (index !== -1) {
            cells[index] = { ...cells[index], ...dto };
            savedCell = cells[index];
        } else {
            savedCell = { ...dto, id: crypto.randomUUID() };
            cells.push(savedCell);
        }
        localStorage.setItem('cells', JSON.stringify(cells));
        return Promise.resolve(savedCell);
    },

    // --- COEFFICIENTS ---
    getCoefficients: async (active = true) => {
        const coeffs = JSON.parse(localStorage.getItem('coefficients') || '[]');
        return Promise.resolve(active ? coeffs.filter(c => c.isActive) : coeffs);
    },

    createCoefficient: async (dto) => {
        const coeffs = JSON.parse(localStorage.getItem('coefficients') || '[]');
        const newCoeff = { ...dto, id: crypto.randomUUID(), isActive: true };
        coeffs.push(newCoeff);
        localStorage.setItem('coefficients', JSON.stringify(coeffs));
        return Promise.resolve(newCoeff);
    },

    // --- RULES ---
    getRulesByCoefficient: async (coeffId) => {
        const rules = JSON.parse(localStorage.getItem('rules') || '[]');
        return Promise.resolve(rules.filter(r => r.coefficientId === coeffId));
    },

    createRule: async (dto) => {
        const rules = JSON.parse(localStorage.getItem('rules') || '[]');
        const newRule = { ...dto, id: crypto.randomUUID() };
        rules.push(newRule);
        localStorage.setItem('rules', JSON.stringify(rules));
        return Promise.resolve(newRule);
    }
};