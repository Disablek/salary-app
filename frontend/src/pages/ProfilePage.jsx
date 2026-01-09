import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { mockApi } from '../services/mockApi';

export default function ProfilePage() {
    const { user, refreshUser } = useAuth();
    const [isEditing, setIsEditing] = useState(false);

    // Данные профиля
    const [formData, setFormData] = useState({ ...user });

    // Данные для смены пароля
    const [passForm, setPassForm] = useState({ oldPassword: '', newPassword: '' });
    const [showPassForm, setShowPassForm] = useState(false);

    const handleSaveProfile = async () => {
        try {
            const updated = await mockApi.updateUser(user.id, formData);
            refreshUser(updated);
            setIsEditing(false);
            alert('Профиль обновлен!');
        } catch (e) {
            alert(e);
        }
    };

    const handleChangePassword = async (e) => {
        e.preventDefault();
        try {
            await mockApi.changePassword(user.id, passForm.oldPassword, passForm.newPassword);
            alert('Пароль успешно изменен');
            setPassForm({ oldPassword: '', newPassword: '' });
            setShowPassForm(false);
        } catch (err) {
            alert('Ошибка: ' + err);
        }
    };

    // Словарь для красивого отображения полей
    const fieldLabels = {
        username: 'Логин (Username)',
        email: 'Email',
        lastName: 'Фамилия',
        firstName: 'Имя',
        surName: 'Отчество'
    };

    // Порядок полей
    const fieldsOrder = ['lastName', 'firstName', 'surName', 'email', 'username'];

    return (
        <div className="container">
            <h1>Личный кабинет</h1>

            <div className="card" style={{ margin: '0 auto 20px auto' }}>
                <h2 style={{ marginTop: 0 }}>Данные пользователя</h2>

                {/* Роль показываем, ID скрываем */}
                <div className="form-group">
                    <label>Роль: <span style={{ color: 'var(--primary)', fontWeight: 'bold' }}>
            {user.role === 'ROLE_SUPERUSER' ? 'Администратор' : 'Пользователь'}
          </span></label>
                </div>

                {fieldsOrder.map(field => (
                    <div key={field} className="form-group">
                        <label>{fieldLabels[field]}</label>
                        <input
                            className="form-input"
                            value={formData[field] || ''}
                            disabled={!isEditing}
                            onChange={(e) => setFormData({...formData, [field]: e.target.value})}
                        />
                    </div>
                ))}

                {!isEditing ? (
                    <button className="btn" onClick={() => setIsEditing(true)}>Редактировать профиль</button>
                ) : (
                    <div style={{ display: 'flex', gap: '10px' }}>
                        <button className="btn" onClick={handleSaveProfile}>Сохранить</button>
                        <button className="btn btn-danger" onClick={() => {
                            setIsEditing(false);
                            setFormData({...user}); // Сброс изменений
                        }}>Отмена</button>
                    </div>
                )}
            </div>

            {/* Блок смены пароля */}
            <div className="card" style={{ margin: '0 auto' }}>
                <div
                    onClick={() => setShowPassForm(!showPassForm)}
                    style={{ cursor: 'pointer', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}
                >
                    <h3 style={{ margin: 0 }}>Безопасность</h3>
                    <span style={{ color: 'var(--primary)' }}>{showPassForm ? 'Свернуть' : 'Сменить пароль'}</span>
                </div>

                {showPassForm && (
                    <form onSubmit={handleChangePassword} style={{ marginTop: '20px' }}>
                        <div className="form-group">
                            <label>Старый пароль</label>
                            <input
                                type="password"
                                className="form-input"
                                required
                                value={passForm.oldPassword}
                                onChange={e => setPassForm({...passForm, oldPassword: e.target.value})}
                            />
                        </div>
                        <div className="form-group">
                            <label>Новый пароль</label>
                            <input
                                type="password"
                                className="form-input"
                                required
                                value={passForm.newPassword}
                                onChange={e => setPassForm({...passForm, newPassword: e.target.value})}
                            />
                        </div>
                        <button type="submit" className="btn">Обновить пароль</button>
                    </form>
                )}
            </div>
        </div>
    );
}