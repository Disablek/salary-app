import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import Navbar from './components/Navbar';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import ProfilePage from './pages/ProfilePage';
import AdminPage from './pages/AdminPage';
import ProtectedRoute from './components/ProtectedRoute';
import { AuthProvider } from './context/AuthContext';
import { UserRole } from './types';
import DataTablesPage from './pages/DataTablesPage';
import TableEditorPage from './pages/TableEditorPage';
import CoefficientsPage from './pages/CoefficientsPage';
import DashboardPage from './pages/DashboardPage';
import UserManagementPage from './pages/UserManagementPage';

import './styles/App.css';

const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2',
    },
    secondary: {
      main: '#dc004e',
    },
  },
  typography: {
    fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
  },
});

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <AuthProvider>
        <BrowserRouter>
          <Navbar />
          <Routes>
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/dashboard" element={
              <ProtectedRoute>
                <DashboardPage />
              </ProtectedRoute>
            } />
            <Route path="/tables" element={
              <ProtectedRoute requireActivation>
                <DataTablesPage />
              </ProtectedRoute>
            } />
            <Route path="/tables/new" element={
              <ProtectedRoute requireActivation>
                <TableEditorPage />
              </ProtectedRoute>
            } />
            <Route path="/tables/:id" element={
              <ProtectedRoute requireActivation>
                <TableEditorPage />
              </ProtectedRoute>
            } />
            <Route path="/coefficients" element={
              <ProtectedRoute>
                <CoefficientsPage />
              </ProtectedRoute>
            } />
            <Route path="/profile" element={
              <ProtectedRoute>
                <ProfilePage />
              </ProtectedRoute>
            } />
            <Route path="/admin/users" element={
              <ProtectedRoute roleRequired={UserRole.SUPERUSER}>
                <UserManagementPage />
              </ProtectedRoute>
            } />
            <Route path="/admin/users/:id" element={
              <ProtectedRoute roleRequired={UserRole.SUPERUSER}>
                <UserManagementPage />
              </ProtectedRoute>
            } />
            <Route path="/admin" element={
              <ProtectedRoute roleRequired={UserRole.SUPERUSER}>
                <AdminPage />
              </ProtectedRoute>
            } />
            <Route path="*" element={<Navigate to="/dashboard" />} />
          </Routes>
        </BrowserRouter>
        <ToastContainer
          position="top-right"
          autoClose={5000}
          hideProgressBar={false}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
        />
      </AuthProvider>
    </ThemeProvider>
  );
}

export default App;