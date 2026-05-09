import React from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Container,
  Typography,
  Grid,
  Card,
  CardContent,
  CardActions,
  Button,
  Box
} from '@mui/material';
import { People, Settings } from '@mui/icons-material';

const AdminPage: React.FC = () => {
  const navigate = useNavigate();

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Typography variant="h4" component="h1" gutterBottom>
        Панель администратора
      </Typography>

      <Grid container spacing={3}>
        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                <People sx={{ mr: 2, fontSize: 40 }} />
                <Typography variant="h5" component="h2">
                  Управление пользователями
                </Typography>
              </Box>
              <Typography variant="body2" color="text.secondary">
                Просмотр, редактирование и управление учетными записями пользователей.
                Активация аккаунтов, изменение ролей и другая административная работа.
              </Typography>
            </CardContent>
            <CardActions>
              <Button
                size="large"
                variant="contained"
                onClick={() => navigate('/admin/users')}
              >
                Перейти к управлению
              </Button>
            </CardActions>
          </Card>
        </Grid>

        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                <Settings sx={{ mr: 2, fontSize: 40 }} />
                <Typography variant="h5" component="h2">
                  Системные настройки
                </Typography>
              </Box>
              <Typography variant="body2" color="text.secondary">
                Конфигурация системы, управление коэффициентами,
                настройка параметров расчетов и другие системные функции.
              </Typography>
            </CardContent>
            <CardActions>
              <Button
                size="large"
                variant="outlined"
                onClick={() => navigate('/coefficients')}
              >
                Коэффициенты
              </Button>
            </CardActions>
          </Card>
        </Grid>
      </Grid>
    </Container>
  );
};

export default AdminPage;