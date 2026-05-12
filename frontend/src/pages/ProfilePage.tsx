import React, { useState } from 'react';
import {
  Container,
  Paper,
  Typography,
  TextField,
  Button,
  Box,
  Grid,
  Alert,
  Card,
  CardContent
} from '@mui/material';
import { toast } from 'react-toastify';
import { useAuth } from '../context/AuthContext';
import { usersApi } from '../services/api';

const ProfilePage: React.FC = () => {
  const { user, refreshUser } = useAuth();
  const [isEditing, setIsEditing] = useState(false);
  const [loading, setLoading] = useState(false);

  // Profile data
  const [formData, setFormData] = useState({
    firstName: user?.firstName || '',
    lastName: user?.lastName || '',
    email: user?.email || ''
  });

  // Password change data
  const [passForm, setPassForm] = useState({
    oldPassword: '',
    newPassword: '',
    confirmPassword: ''
  });
  const [showPassForm, setShowPassForm] = useState(false);
  const [passLoading, setPassLoading] = useState(false);

  const handleSaveProfile = async () => {
    if (!user) return;

    setLoading(true);
    try {
      const updated = await usersApi.updateUser(user.id, formData);
      refreshUser(updated);
      setIsEditing(false);
      toast.success('Профиль обновлен');
    } catch (err) {
      toast.error('Ошибка обновления профиля');
    } finally {
      setLoading(false);
    }
  };

  const handleChangePassword = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!user) return;

    if (passForm.newPassword !== passForm.confirmPassword) {
      toast.error('Пароли не совпадают');
      return;
    }

    setPassLoading(true);
    try {
      await usersApi.changePassword(user.id, passForm.oldPassword, passForm.newPassword);
      toast.success('Пароль успешно изменен');
      setPassForm({ oldPassword: '', newPassword: '', confirmPassword: '' });
      setShowPassForm(false);
    } catch (err) {
      toast.error('Ошибка изменения пароля');
    } finally {
      setPassLoading(false);
    }
  };

  if (!user) {
    return (
      <Container maxWidth="md" sx={{ mt: 4 }}>
        <Alert severity="error">Пользователь не найден</Alert>
      </Container>
    );
  }

  return (
    <Container maxWidth="md" sx={{ mt: 4, mb: 4 }}>
      <Typography variant="h4" component="h1" gutterBottom>
        Профиль пользователя
      </Typography>

      <Grid container spacing={3}>
        {/* Profile Information */}
        <Grid item xs={12} md={8}>
          <Paper sx={{ p: 3 }}>
            <Typography variant="h6" gutterBottom>
              Личная информация
            </Typography>

            <Box sx={{ mb: 3 }}>
              <Grid container spacing={2}>
                <Grid item xs={12} sm={6}>
                  <TextField
                    fullWidth
                    label="Имя"
                    value={isEditing ? formData.firstName : user.firstName}
                    onChange={(e) => setFormData({ ...formData, firstName: e.target.value })}
                    disabled={!isEditing}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    fullWidth
                    label="Фамилия"
                    value={isEditing ? formData.lastName : user.lastName}
                    onChange={(e) => setFormData({ ...formData, lastName: e.target.value })}
                    disabled={!isEditing}
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    fullWidth
                    label="Email"
                    type="email"
                    value={isEditing ? formData.email : user.email}
                    onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                    disabled={!isEditing}
                  />
                </Grid>
              </Grid>
            </Box>

            <Box sx={{ display: 'flex', gap: 2 }}>
              {isEditing ? (
                <>
                  <Button
                    variant="contained"
                    onClick={handleSaveProfile}
                    disabled={loading}
                  >
                    {loading ? 'Сохранение...' : 'Сохранить'}
                  </Button>
                  <Button
                    variant="outlined"
                    onClick={() => {
                      setFormData({
                        firstName: user.firstName,
                        lastName: user.lastName,
                        email: user.email
                      });
                      setIsEditing(false);
                    }}
                  >
                    Отмена
                  </Button>
                </>
              ) : (
                <Button variant="outlined" onClick={() => setIsEditing(true)}>
                  Редактировать
                </Button>
              )}
            </Box>
          </Paper>
        </Grid>

        {/* Account Status */}
        <Grid item xs={12} md={4}>
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Статус аккаунта
              </Typography>
              <Typography variant="body2" sx={{ mb: 1 }}>
                <strong>Роль:</strong> {user.role === 'ROLE_SUPERUSER' ? 'Суперпользователь' : 'Пользователь'}
              </Typography>
              <Typography variant="body2" sx={{ mb: 1 }}>
                <strong>Активирован:</strong> {user.isActivated ? 'Да' : 'Нет'}
              </Typography>
              <Typography variant="body2">
                <strong>Дата регистрации:</strong> {new Date(user.createdAt).toLocaleDateString()}
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        {/* Password Change */}
        <Grid item xs={12}>
          <Paper sx={{ p: 3 }}>
            <Typography variant="h6" gutterBottom>
              Смена пароля
            </Typography>

            {!showPassForm ? (
              <Button variant="outlined" onClick={() => setShowPassForm(true)}>
                Изменить пароль
              </Button>
            ) : (
              <Box component="form" onSubmit={handleChangePassword} sx={{ mt: 2 }}>
                <Grid container spacing={2}>
                  <Grid item xs={12} sm={4}>
                    <TextField
                      fullWidth
                      label="Текущий пароль"
                      type="password"
                      value={passForm.oldPassword}
                      onChange={(e) => setPassForm({ ...passForm, oldPassword: e.target.value })}
                      required
                    />
                  </Grid>
                  <Grid item xs={12} sm={4}>
                    <TextField
                      fullWidth
                      label="Новый пароль"
                      type="password"
                      value={passForm.newPassword}
                      onChange={(e) => setPassForm({ ...passForm, newPassword: e.target.value })}
                      required
                    />
                  </Grid>
                  <Grid item xs={12} sm={4}>
                    <TextField
                      fullWidth
                      label="Подтверждение пароля"
                      type="password"
                      value={passForm.confirmPassword}
                      onChange={(e) => setPassForm({ ...passForm, confirmPassword: e.target.value })}
                      required
                    />
                  </Grid>
                </Grid>
                <Box sx={{ mt: 2, display: 'flex', gap: 2 }}>
                  <Button
                    type="submit"
                    variant="contained"
                    disabled={passLoading}
                  >
                    {passLoading ? 'Сохранение...' : 'Сохранить пароль'}
                  </Button>
                  <Button
                    variant="outlined"
                    onClick={() => {
                      setPassForm({ oldPassword: '', newPassword: '', confirmPassword: '' });
                      setShowPassForm(false);
                    }}
                  >
                    Отмена
                  </Button>
                </Box>
              </Box>
            )}
          </Paper>
        </Grid>
      </Grid>
    </Container>
  );
};

export default ProfilePage;
