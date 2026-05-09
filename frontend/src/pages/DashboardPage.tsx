import React from 'react';
import {
  Container,
  Typography,
  Grid,
  Card,
  CardContent,
  Button,
  Box
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { UserRole } from '../types';

const DashboardPage: React.FC = () => {
  const { user } = useAuth();
  const navigate = useNavigate();

  const isSuperUser = user?.role === UserRole.SUPERUSER;
  const isActivated = user?.isActivated;

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Typography variant="h4" component="h1" gutterBottom>
        Добро пожаловать, {user?.firstName} {user?.lastName}!
      </Typography>

      {!isActivated && (
        <Card sx={{ mb: 3, bgcolor: 'warning.light' }}>
          <CardContent>
            <Typography variant="h6" color="warning.contrastText">
              Аккаунт не активирован
            </Typography>
            <Typography color="warning.contrastText">
              Ваш аккаунт еще не активирован администратором. Доступ к таблицам ограничен.
            </Typography>
          </CardContent>
        </Card>
      )}

      <Grid container spacing={3}>
        {/* Tables Section */}
        {isActivated && (
          <Grid item xs={12} md={6}>
            <Card>
              <CardContent>
                <Typography variant="h5" component="h2" gutterBottom>
                  Таблицы педагогических работников
                </Typography>
                <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
                  Создавайте и управляйте таблицами с данными о педагогических работниках лицея БНТУ.
                </Typography>
                <Box sx={{ display: 'flex', gap: 1 }}>
                  <Button
                    variant="contained"
                    onClick={() => navigate('/tables')}
                  >
                    Просмотреть таблицы
                  </Button>
                  <Button
                    variant="outlined"
                    onClick={() => navigate('/tables/new')}
                  >
                    Создать новую
                  </Button>
                </Box>
              </CardContent>
            </Card>
          </Grid>
        )}

        {/* Coefficients Section */}
        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h5" component="h2" gutterBottom>
                Коэффициенты
              </Typography>
              <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
                Управляйте коэффициентами для автоматического расчета выплат.
              </Typography>
              <Button
                variant="contained"
                onClick={() => navigate('/coefficients')}
              >
                Управление коэффициентами
              </Button>
            </CardContent>
          </Card>
        </Grid>

        {/* Admin Section */}
        {isSuperUser && (
          <Grid item xs={12}>
            <Card>
              <CardContent>
                <Typography variant="h5" component="h2" gutterBottom>
                  Администрирование
                </Typography>
                <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
                  Управление пользователями и настройками системы.
                </Typography>
                <Box sx={{ display: 'flex', gap: 1 }}>
                  <Button
                    variant="contained"
                    onClick={() => navigate('/admin/users')}
                  >
                    Управление пользователями
                  </Button>
                  <Button
                    variant="outlined"
                    onClick={() => navigate('/admin')}
                  >
                    Панель администратора
                  </Button>
                </Box>
              </CardContent>
            </Card>
          </Grid>
        )}

        {/* Profile Section */}
        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h5" component="h2" gutterBottom>
                Профиль
              </Typography>
              <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
                Просмотр и редактирование личных данных.
              </Typography>
              <Button
                variant="outlined"
                onClick={() => navigate('/profile')}
              >
                Мой профиль
              </Button>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </Container>
  );
};

export default DashboardPage;