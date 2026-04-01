const API_BASE_URL = 'http://localhost:8080/api';

// Утилита для проверки ошибок
const handleResponse = async (response) => {
    if (!response.ok) {
        const error = await response.json().catch(() => ({ message: 'Unknown error' }));
        throw new Error(error.message || `HTTP ${response.status}`);
    }
    return response.json();
};


export const mockApi = {
    // --- AUTH API ---
    login: async (email, password) => {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        });
        const data = await handleResponse(response);
        localStorage.setItem('token', data.token);
        
        const userResponse = await fetch(`${API_BASE_URL}/users/me`, {
            headers: { 'Authorization': `Bearer ${data.token}` }
        });
        const userData = await handleResponse(userResponse);
        return userData;
    },

    register: async (userDtoInput) => {
        const response = await fetch(`${API_BASE_URL}/auth/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(userDtoInput)
        });
        return handleResponse(response);
    },

    getCurrentUser: async () => {
        const token = localStorage.getItem('token');
        if (!token) return null;
        try {
            const response = await fetch(`${API_BASE_URL}/users/me`, {
                headers: { 'Authorization': `Bearer ${token}` }
            });
            return await handleResponse(response);
        } catch {
            return null;
        }
    },

    // --- USERS API ---
    getAllUsers: async () => {
        const response = await fetch(`${API_BASE_URL}/users`, {
            headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` }
        });
        return handleResponse(response);
    },

    updateUser: async (id, userDtoInput) => {
        const response = await fetch(`${API_BASE_URL}/users/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify(userDtoInput)
        });
        const updated = await handleResponse(response);
        
        // Обновляем текущего пользователя если это он сам
        const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');
        if (currentUser.id === id) {
            localStorage.setItem('currentUser', JSON.stringify(updated));
        }
        return updated;
    },

    changePassword: async (userId, oldPassword, newPassword) => {
        const response = await fetch(
            `${API_BASE_URL}/users/${userId}/change-password?oldPassword=${encodeURIComponent(oldPassword)}&newPassword=${encodeURIComponent(newPassword)}`,
            {
                method: 'PUT',
                headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` }
            }
        );
        return handleResponse(response);
    },

    deleteUser: async (id) => {
        const response = await fetch(`${API_BASE_URL}/users/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` }
        });
        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        return null;
    },

    // --- DATA TABLES API ---
    getDataTables: async () => {
        const response = await fetch(`${API_BASE_URL}/datatables`, {
            headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` }
        });
        return handleResponse(response);
    },

    getDataTableById: async (id) => {
        const response = await fetch(`${API_BASE_URL}/datatables/${id}`, {
            headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` }
        });
        return handleResponse(response);
    },

    createDataTable: async (dto) => {
        const response = await fetch(`${API_BASE_URL}/datatables`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify(dto)
        });
        return handleResponse(response);
    },

    updateDataTable: async (id, dto) => {
        const response = await fetch(`${API_BASE_URL}/datatables/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify(dto)
        });
        return handleResponse(response);
    },

    deleteDataTable: async (id) => {
        const response = await fetch(`${API_BASE_URL}/datatables/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` }
        });
        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        return null;
    },

    // --- COLUMNS API ---
    getColumns: async (tableId, page) => {
        const params = new URLSearchParams({ tableId });
        if (page) params.append('page', page);
        
        const response = await fetch(`${API_BASE_URL}/columns?${params}`, {
            headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` }
        });
        return handleResponse(response);
    },

    createColumn: async (dto) => {
        const response = await fetch(`${API_BASE_URL}/columns`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify(dto)
        });
        return handleResponse(response);
    },

    updateColumn: async (id, dto) => {
        const response = await fetch(`${API_BASE_URL}/columns/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify(dto)
        });
        return handleResponse(response);
    },

    deleteColumn: async (id) => {
        const response = await fetch(`${API_BASE_URL}/columns/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` }
        });
        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        return null;
    },

    // --- ROWS API ---
    getRows: async (tableId) => {
        const response = await fetch(`${API_BASE_URL}/rows?tableId=${tableId}`, {
            headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` }
        });
        return handleResponse(response);
    },

    createRow: async (dto) => {
        const response = await fetch(`${API_BASE_URL}/rows`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify(dto)
        });
        return handleResponse(response);
    },

    deleteRow: async (id) => {
        const response = await fetch(`${API_BASE_URL}/rows/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` }
        });
        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        return null;
    },

    // --- CELLS API ---
    getCells: async (tableId, page) => {
        const params = new URLSearchParams({ tableId });
        if (page) params.append('page', page);
        
        const response = await fetch(`${API_BASE_URL}/cells?${params}`, {
            headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` }
        });
        return handleResponse(response);
    },

    saveCell: async (dto) => {
        const response = await fetch(`${API_BASE_URL}/cells`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify(dto)
        });
        return handleResponse(response);
    },

    updateCell: async (id, dto) => {
        const response = await fetch(`${API_BASE_URL}/cells/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify(dto)
        });
        return handleResponse(response);
    },

    // --- COEFFICIENTS API ---
    getCoefficients: async (active = true) => {
        const response = await fetch(`${API_BASE_URL}/coefficients`, {
            headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` }
        });
        const coeffs = await handleResponse(response);
        // Если нужны только активные, фильтруем на фронте
        return active ? coeffs.filter(c => c.isActive) : coeffs;
    },

    createCoefficient: async (dto) => {
        const response = await fetch(`${API_BASE_URL}/coefficients`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify(dto)
        });
        return handleResponse(response);
    },

    updateCoefficient: async (id, dto) => {
        const response = await fetch(`${API_BASE_URL}/coefficients/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify(dto)
        });
        return handleResponse(response);
    },

    deleteCoefficient: async (id) => {
        const response = await fetch(`${API_BASE_URL}/coefficients/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` }
        });
        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        return null;
    },

    // --- RULES API ---
    getRulesByCoefficient: async (coeffId) => {
        const response = await fetch(`${API_BASE_URL}/rules?coefficientId=${coeffId}`, {
            headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` }
        });
        return handleResponse(response);
    },

    createRule: async (dto) => {
        const response = await fetch(`${API_BASE_URL}/rules`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify(dto)
        });
        return handleResponse(response);
    }
};