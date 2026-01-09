import React, { useEffect, useState } from 'react';
import { mockApi } from '../services/mockApi';

export default function AdminPage() {
    const [users, setUsers] = useState([]);
    const [editingId, setEditingId] = useState(null);
    const [editForm, setEditForm] = useState({});

    useEffect(() => {
        loadUsers();
    }, []);

    const loadUsers = async () => {
        const data = await mockApi.getAllUsers();
        setUsers(data);
    };

    const handleDelete = async (id) => {
        if (window.confirm('Вы уверены, что хотите удалить этого пользователя?')) {
            await mockApi.deleteUser(id);
            loadUsers();
        }
    };

    const startEdit = (user) => {
        setEditingId(user.id);
        setEditForm({ ...user });
    };

    const saveEdit = async () => {
        try {
            await mockApi.updateUser(editingId, editForm);
            setEditingId(null);
            loadUsers();
        } catch (e) {
            alert(e);
        }
    };

    const cancelEdit = () => {
        setEditingId(null);
        setEditForm({});
    };

    return (
        <div className="container" style={{ maxWidth: '1400px' }}>
            <h1>Панель Администратора</h1>
            <table>
                <thead>
                <tr>
                    <th style={{ width: '30%' }}>ФИО (Фамилия Имя Отчество)</th>
                    <th>Email</th>
                    <th>Логин</th>
                    <th>Роль</th>
                    <th>Действия</th>
                </tr>
                </thead>
                <tbody>
                {users.map(u => (
                    <tr key={u.id}>
                        {/* Колонка ФИО */}
                        <td>
                            {editingId === u.id ? (
                                <div style={{ display: 'flex', flexDirection: 'column', gap: '5px' }}>
                                    <input
                                        placeholder="Фамилия"
                                        className="form-input"
                                        style={{ padding: '5px' }}
                                        value={editForm.lastName || ''}
                                        onChange={e => setEditForm({...editForm, lastName: e.target.value})}
                                    />
                                    <input
                                        placeholder="Имя"
                                        className="form-input"
                                        style={{ padding: '5px' }}
                                        value={editForm.firstName || ''}
                                        onChange={e => setEditForm({...editForm, firstName: e.target.value})}
                                    />
                                    <input
                                        placeholder="Отчество"
                                        className="form-input"
                                        style={{ padding: '5px' }}
                                        value={editForm.surName || ''}
                                        onChange={e => setEditForm({...editForm, surName: e.target.value})}
                                    />
                                </div>
                            ) : (
                                // Отображение: Фамилия Имя Отчество
                                `${u.lastName || ''} ${u.firstName || ''} ${u.surName || ''}`.trim()
                            )}
                        </td>

                        {/* Колонка Email */}
                        <td>
                            {editingId === u.id ? (
                                <input
                                    className="form-input"
                                    style={{ padding: '5px' }}
                                    value={editForm.email}
                                    onChange={e => setEditForm({...editForm, email: e.target.value})}
                                />
                            ) : u.email}
                        </td>

                        {/* Колонка Username */}
                        <td>
                            {editingId === u.id ? (
                                <input
                                    className="form-input"
                                    style={{ padding: '5px' }}
                                    value={editForm.username}
                                    onChange={e => setEditForm({...editForm, username: e.target.value})}
                                />
                            ) : u.username}
                        </td>

                        {/* Колонка Роль */}
                        <td>
                            {editingId === u.id ? (
                                <select
                                    className="form-input"
                                    style={{ padding: '5px' }}
                                    value={editForm.role}
                                    onChange={e => setEditForm({...editForm, role: e.target.value})}
                                >
                                    <option value="ROLE_USER">Пользователь</option>
                                    <option value="ROLE_SUPERUSER">Администратор</option>
                                </select>
                            ) : (
                                <span style={{
                                    padding: '4px 8px',
                                    borderRadius: '4px',
                                    background: u.role === 'ROLE_SUPERUSER' ? '#fee2e2' : '#e0e7ff',
                                    color: u.role === 'ROLE_SUPERUSER' ? '#991b1b' : '#3730a3',
                                    fontSize: '0.8rem',
                                    fontWeight: 'bold'
                                }}>
                    {u.role === 'ROLE_SUPERUSER' ? 'ADMIN' : 'USER'}
                  </span>
                            )}
                        </td>

                        {/* Действия */}
                        <td>
                            {editingId === u.id ? (
                                <div style={{ display: 'flex', gap: '5px', flexDirection: 'column' }}>
                                    <button className="btn" style={{ fontSize: '0.8rem', padding: '5px' }} onClick={saveEdit}>Сохранить</button>
                                    <button className="btn btn-danger" style={{ fontSize: '0.8rem', padding: '5px' }} onClick={cancelEdit}>Отмена</button>
                                </div>
                            ) : (
                                <div style={{ display: 'flex', gap: '5px' }}>
                                    <button className="btn" style={{ fontSize: '0.8rem', padding: '5px', width: 'auto' }} onClick={() => startEdit(u)}>Изм.</button>
                                    <button className="btn btn-danger" style={{ fontSize: '0.8rem', padding: '5px', width: 'auto' }} onClick={() => handleDelete(u.id)}>Удал.</button>
                                </div>
                            )}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}