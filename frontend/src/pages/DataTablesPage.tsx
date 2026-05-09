import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Container,
  Typography,
  Button,
  Card,
  CardContent,
  CardActions,
  Grid,
  TextField,
  Box,
  Alert,
  IconButton
} from '@mui/material';
import { Add, Edit, Delete } from '@mui/icons-material';
import { toast } from 'react-toastify';
import { tablesApi } from '../services/api';
import { DataTable } from '../types';

const DataTablesPage: React.FC = () => {
  const navigate = useNavigate();
  const [tables, setTables] = useState<DataTable[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [newTableName, setNewTableName] = useState('');
  const [creating, setCreating] = useState(false);

  useEffect(() => {
    loadTables();
  }, []);

  const loadTables = async () => {
    try {
      setLoading(true);
      const data = await tablesApi.getDataTables();
      setTables(data);
      setError(null);
    } catch (err) {
      setError('Ошибка загрузки таблиц');
      toast.error('Ошибка загрузки таблиц');
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = async () => {
    if (!newTableName.trim()) {
      toast.error('Введите название таблицы');
      return;
    }

    setCreating(true);
    try {
      await tablesApi.createDataTable({
        name: newTableName.trim(),
        description: 'Новая таблица'
      });
      setNewTableName('');
      loadTables();
      toast.success('Таблица создана');
    } catch (err) {
      toast.error('Ошибка создания таблицы');
    } finally {
      setCreating(false);
    }
  };

  const handleDelete = async (tableId: number) => {
    if (window.confirm('Вы уверены, что хотите удалить эту таблицу?')) {
      try {
        await tablesApi.deleteDataTable(tableId);
        loadTables();
        toast.success('Таблица удалена');
      } catch (err) {
        toast.error('Ошибка удаления таблицы');
      }
    }
  };

  if (loading) {
    return (
      <Container maxWidth="lg" sx={{ mt: 4 }}>
        <Typography>Загрузка таблиц...</Typography>
      </Container>
    );
  }

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Typography variant="h4" component="h1" gutterBottom>
        Таблицы педагогических работников
      </Typography>

      {error && (
        <Alert severity="error" sx={{ mb: 3 }}>
          {error}
        </Alert>
      )}

      {/* Create new table */}
      <Card sx={{ mb: 4 }}>
        <CardContent>
          <Typography variant="h6" gutterBottom>
            Создать новую таблицу
          </Typography>
          <Box sx={{ display: 'flex', gap: 2, alignItems: 'center' }}>
            <TextField
              fullWidth
              label="Название таблицы"
              value={newTableName}
              onChange={(e) => setNewTableName(e.target.value)}
              onKeyPress={(e) => e.key === 'Enter' && handleCreate()}
            />
            <Button
              variant="contained"
              onClick={handleCreate}
              disabled={creating || !newTableName.trim()}
              startIcon={<Add />}
            >
              {creating ? 'Создание...' : 'Создать'}
            </Button>
          </Box>
        </CardContent>
      </Card>

      {/* Tables grid */}
      <Grid container spacing={3}>
        {tables.map((table) => (
          <Grid item xs={12} sm={6} md={4} key={table.id}>
            <Card>
              <CardContent>
                <Typography variant="h6" component="h2" gutterBottom>
                  {table.name}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Создано: {new Date(table.createdAt).toLocaleDateString()}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Колонок: {table.columns.length}, Строк: {table.rows.length}
                </Typography>
              </CardContent>
              <CardActions>
                <Button
                  size="small"
                  onClick={() => navigate(`/tables/${table.id}`)}
                  startIcon={<Edit />}
                >
                  Открыть
                </Button>
                <IconButton
                  size="small"
                  onClick={() => handleDelete(table.id)}
                  color="error"
                >
                  <Delete />
                </IconButton>
              </CardActions>
            </Card>
          </Grid>
        ))}
      </Grid>

      {tables.length === 0 && !loading && (
        <Box sx={{ textAlign: 'center', mt: 4 }}>
          <Typography variant="h6" color="text.secondary">
            Нет созданных таблиц
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Создайте первую таблицу для начала работы
          </Typography>
        </Box>
      )}
    </Container>
  );
};

export default DataTablesPage;