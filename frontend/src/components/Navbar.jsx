import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Navbar() {
    const { user, logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <nav className="navbar">
            <div style={{ fontWeight: 'bold', fontSize: '1.2rem' }}>SalaryApp</div>
            <div className="nav-links">
                {!user ? (
                    <>
                        <Link to="/login">Вход</Link>
                        <Link to="/register">Регистрация</Link>
                    </>
                ) : (
                    <>
                        <Link to="/profile">Профиль</Link>
                        <Link to="/datatables">Таблицы</Link>
                        {user.roleName === 'ROLE_SUPERUSER' && <Link to="/admin">Админка</Link>}
                        <button onClick={handleLogout} className="nav-btn" style={{ marginLeft: '20px' }}>
                            Выход
                        </button>
                    </>
                )}
            </div>

        </nav>
    );
}