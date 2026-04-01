import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import ProfilePage from './pages/ProfilePage';
import AdminPage from './pages/AdminPage';
import ProtectedRoute from './components/ProtectedRoute';
import { AuthProvider } from './context/AuthContext';
import './styles/App.css';
import DataTablesPage from './pages/DataTablesPage';
import TableEditorPage from './pages/TableEditorPage';

function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <Navbar />
                <Routes>
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/register" element={<RegisterPage />} />
                    <Route path="/datatables" element={
                        <ProtectedRoute>
                            <DataTablesPage />
                        </ProtectedRoute>
                    } />

                    <Route path="/datatables/:id" element={
                        <ProtectedRoute>
                            <TableEditorPage />
                        </ProtectedRoute>
                    } />

                    {/* Защищенные маршруты */}
                    <Route path="/profile" element={
                        <ProtectedRoute>
                            <ProfilePage />
                        </ProtectedRoute>
                    } />

                    <Route path="/admin" element={
                        <ProtectedRoute roleRequired="ROLE_SUPERUSER">
                            <AdminPage />
                        </ProtectedRoute>
                    } />

                    <Route path="*" element={<Navigate to="/login" />} />
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
}

export default App;