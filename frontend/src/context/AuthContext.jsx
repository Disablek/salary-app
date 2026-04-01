import React, { createContext, useState, useEffect, useContext } from 'react';
import { mockApi } from '../services/mockApi';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        // При загрузке страницы проверяем, есть ли сохраненная сессия
        const initAuth = async () => {
            const storedUser = await mockApi.getCurrentUser();
            if (storedUser) {
                setUser(storedUser);
            }
            setLoading(false);
        };
        initAuth();
    }, []);

    const login = async (email, password) => {
        try {
            const response = await mockApi.login(email, password);
            // Бэкенд может вернуть { user, token } или просто { ...userData, token }
            const userData = response.user || response;
            localStorage.setItem('currentUser', JSON.stringify(userData));
            setUser(userData);
            return true;
        } catch (error) {
            alert(error);
            return false;
        }
    };

    const register = async (data) => {
        await mockApi.register(data);
        // Автологин после регистрации
        return login(data.email, data.password);
    };

    const logout = () => {
        localStorage.removeItem('currentUser');
        setUser(null);
    };

    // Метод для обновления данных в стейте без перезагрузки
    const refreshUser = (updatedData) => {
        setUser(prev => ({...prev, ...updatedData}));
    }

    return (
        <AuthContext.Provider value={{ user, login, register, logout, loading, refreshUser }}>
            {!loading && children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);