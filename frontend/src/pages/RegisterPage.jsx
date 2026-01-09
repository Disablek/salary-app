import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

export default function RegisterPage() {
    const [formData, setFormData] = useState({
        username: '', email: '', firstName: '', lastName: '', surName: '', password: ''
    });
    const { register } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        await register(formData);
        navigate('/profile');
    };

    const handleChange = (e) => setFormData({...formData, [e.target.name]: e.target.value});

    return (
        <div className="card" style={{ maxWidth: '500px' }}>
            <h2 style={{ textAlign: 'center' }}>Регистрация</h2>
            <form onSubmit={handleSubmit}>
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px' }}>
                    <div className="form-group">
                        <label>Фамилия</label>
                        <input name="lastName" className="form-input" onChange={handleChange} required />
                    </div>
                    <div className="form-group">
                        <label>Имя</label>
                        <input name="firstName" className="form-input" onChange={handleChange} required />
                    </div>
                </div>
                <div className="form-group">
                    <label>Отчество</label>
                    <input name="surName" className="form-input" onChange={handleChange} />
                </div>

                <div className="form-group">
                    <label>Логин (Username)</label>
                    <input name="username" className="form-input" onChange={handleChange} required />
                </div>
                <div className="form-group">
                    <label>Email</label>
                    <input name="email" type="email" className="form-input" onChange={handleChange} required />
                </div>
                <div className="form-group">
                    <label>Пароль</label>
                    <input name="password" type="password" className="form-input" onChange={handleChange} required />
                </div>
                <button className="btn" type="submit">Зарегистрироваться</button>
            </form>
        </div>
    );
}