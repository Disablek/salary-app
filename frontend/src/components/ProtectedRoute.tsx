import React from 'react';
import { Navigate } from 'react-router-dom';
import { Box, Typography, Alert } from '@mui/material';
import { useAuth } from '../context/AuthContext';
import { UserRole } from '../types';

interface ProtectedRouteProps {
  children: React.ReactNode;
  roleRequired?: UserRole;
  requireActivation?: boolean;
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({
  children,
  roleRequired,
  requireActivation = false
}) => {
  const { user } = useAuth();

  if (!user) {
    return <Navigate to="/login" replace />;
  }

  if (roleRequired && user.role !== roleRequired) {
    return (
      <Box sx={{ p: 3 }}>
        <Alert severity="error">
          <Typography variant="h6">Доступ запрещен</Typography>
          <Typography>У вас нет необходимых прав для просмотра этой страницы.</Typography>
        </Alert>
      </Box>
    );
  }

  if (requireActivation && !user.isActivated) {
    return (
      <Box sx={{ p: 3 }}>
        <Alert severity="warning">
          <Typography variant="h6">Аккаунт не активирован</Typography>
          <Typography>
            Ваш аккаунт еще не активирован администратором. Обратитесь к администратору для активации.
          </Typography>
        </Alert>
      </Box>
    );
  }

  return <>{children}</>;
};

export default ProtectedRoute;