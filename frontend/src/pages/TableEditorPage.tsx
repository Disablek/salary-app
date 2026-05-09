import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
  Container,
  Typography,
  Button,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  TextField,
  Box,
  IconButton,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
  Alert
} from '@mui/material';
import { Add, Delete, Edit, Save } from '@mui/icons-material';
import { toast } from 'react-toastify';
import { tablesApi, columnsApi, rowsApi, cellsApi } from '../services/api';
import { DataTable, Column, Row, ColumnType } from '../types';

const TableEditorPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [table, setTable] = useState<DataTable | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [isNewTable, setIsNewTable] = useState(false);
  const [tableName, setTableName] = useState('');
  const [editingCell, setEditingCell] = useState<{ rowId: number; columnId: number; value: string } | null>(null);

  // Column dialog
  const [columnDialogOpen, setColumnDialogOpen] = useState(false);
  const [editingColumn, setEditingColumn] = useState<Column | null>(null);
  const [columnForm, setColumnForm] = useState({
    name: '',
    type: ColumnType.TEXT,
    isRequired: false
  });

  useEffect(() => {
    if (id === 'new') {
      setIsNewTable(true);
      setTable({
        id: 0,
        name: '',
        createdBy: {} as any,
        createdAt: '',
        updatedAt: '',
        columns: [],
        rows: []
      });
      setLoading(false);
    } else if (id) {
      loadTable(parseInt(id));
    }
  }, [id]);

  const loadTable = async (tableId: number) => {
    try {
      setLoading(true);
      const tableData = await tablesApi.getDataTableById(tableId);
      setTable(tableData);
      setTableName(tableData.name);
      setError(null);
    } catch (err) {
      setError('Ошибка загрузки таблицы');
      toast.error('Ошибка загрузки таблицы');
    } finally {
      setLoading(false);
    }
  };

  const handleSaveTable = async () => {
    if (!table) return;

    try {
      if (isNewTable) {
        const newTable = await tablesApi.createDataTable({ name: tableName });
        navigate(`/tables/${newTable.id}`);
        toast.success('Таблица создана');
      } else {
        await tablesApi.updateDataTable(table.id, { name: tableName });
        toast.success('Таблица обновлена');
      }
    } catch (err) {
      toast.error('Ошибка сохранения таблицы');
    }
  };

  const handleAddColumn = () => {
    setEditingColumn(null);
    setColumnForm({ name: '', type: ColumnType.TEXT, isRequired: false });
    setColumnDialogOpen(true);
  };

  const handleEditColumn = (column: Column) => {
    setEditingColumn(column);
    setColumnForm({
      name: column.name,
      type: column.type,
      isRequired: column.isRequired
    });
    setColumnDialogOpen(true);
  };

  const handleSaveColumn = async () => {
    if (!table) return;

    try {
      if (editingColumn) {
        await columnsApi.updateColumn(editingColumn.id, columnForm);
        toast.success('Колонка обновлена');
      } else {
        await columnsApi.createColumn({ ...columnForm, tableId: table.id });
        toast.success('Колонка добавлена');
      }
      setColumnDialogOpen(false);
      loadTable(table.id);
    } catch (err) {
      toast.error('Ошибка сохранения колонки');
    }
  };

  const handleDeleteColumn = async (columnId: number) => {
    if (window.confirm('Удалить колонку?')) {
      try {
        await columnsApi.deleteColumn(columnId);
        toast.success('Колонка удалена');
        if (table) loadTable(table.id);
      } catch (err) {
        toast.error('Ошибка удаления колонки');
      }
    }
  };

  const handleAddRow = async () => {
    if (!table) return;

    try {
      await rowsApi.createRow({ tableId: table.id });
      toast.success('Строка добавлена');
      loadTable(table.id);
    } catch (err) {
      toast.error('Ошибка добавления строки');
    }
  };

  const handleDeleteRow = async (rowId: number) => {
    if (window.confirm('Удалить строку?')) {
      try {
        await rowsApi.deleteRow(rowId);
        toast.success('Строка удалена');
        if (table) loadTable(table.id);
      } catch (err) {
        toast.error('Ошибка удаления строки');
      }
    }
  };

  const handleCellEdit = (rowId: number, columnId: number, currentValue: any) => {
    setEditingCell({ rowId, columnId, value: currentValue?.toString() || '' });
  };

  const handleCellSave = async () => {
    if (!editingCell) return;

    try {
      await cellsApi.saveCell({
        rowId: editingCell.rowId,
        columnId: editingCell.columnId,
        value: editingCell.value
      });
      setEditingCell(null);
      if (table) loadTable(table.id);
      toast.success('Ячейка обновлена');
    } catch (err) {
      toast.error('Ошибка сохранения ячейки');
    }
  };

  const getCellValue = (row: Row, columnId: number) => {
    const cell = row.cells.find(c => c.columnId === columnId);
    return cell?.value || '';
  };

  if (loading) {
    return (
      <Container maxWidth="lg" sx={{ mt: 4 }}>
        <Typography>Загрузка...</Typography>
      </Container>
    );
  }

  if (!table) {
    return (
      <Container maxWidth="lg" sx={{ mt: 4 }}>
        <Alert severity="error">Таблица не найдена</Alert>
      </Container>
    );
  }

  return (
    <Container maxWidth="xl" sx={{ mt: 4, mb: 4 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
        <TextField
          label="Название таблицы"
          value={tableName}
          onChange={(e) => setTableName(e.target.value)}
          sx={{ minWidth: 300 }}
        />
        <Box>
          <Button variant="contained" onClick={handleSaveTable} sx={{ mr: 1 }}>
            <Save sx={{ mr: 1 }} />
            Сохранить таблицу
          </Button>
          <Button variant="outlined" onClick={() => navigate('/tables')}>
            Назад к списку
          </Button>
        </Box>
      </Box>

      {error && (
        <Alert severity="error" sx={{ mb: 3 }}>
          {error}
        </Alert>
      )}

      <Box sx={{ mb: 2 }}>
        <Button variant="contained" onClick={handleAddColumn} sx={{ mr: 1 }}>
          <Add sx={{ mr: 1 }} />
          Добавить колонку
        </Button>
        <Button variant="contained" onClick={handleAddRow}>
          <Add sx={{ mr: 1 }} />
          Добавить строку
        </Button>
      </Box>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              {table.columns.map((column) => (
                <TableCell key={column.id}>
                  <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                    {column.name}
                    <IconButton size="small" onClick={() => handleEditColumn(column)}>
                      <Edit fontSize="small" />
                    </IconButton>
                    <IconButton size="small" onClick={() => handleDeleteColumn(column.id)}>
                      <Delete fontSize="small" />
                    </IconButton>
                  </Box>
                </TableCell>
              ))}
              <TableCell>Действия</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {table.rows.map((row) => (
              <TableRow key={row.id}>
                {table.columns.map((column) => (
                  <TableCell key={column.id}>
                    {editingCell?.rowId === row.id && editingCell?.columnId === column.id ? (
                      <TextField
                        size="small"
                        value={editingCell.value}
                        onChange={(e) => setEditingCell({ ...editingCell, value: e.target.value })}
                        onBlur={handleCellSave}
                        onKeyPress={(e) => e.key === 'Enter' && handleCellSave()}
                        autoFocus
                      />
                    ) : (
                      <Box
                        onClick={() => handleCellEdit(row.id, column.id, getCellValue(row, column.id))}
                        sx={{ cursor: 'pointer', minHeight: 24, display: 'flex', alignItems: 'center' }}
                      >
                        {getCellValue(row, column.id) || <em style={{ color: '#999' }}>пусто</em>}
                      </Box>
                    )}
                  </TableCell>
                ))}
                <TableCell>
                  <IconButton onClick={() => handleDeleteRow(row.id)}>
                    <Delete />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      {/* Column Dialog */}
      <Dialog open={columnDialogOpen} onClose={() => setColumnDialogOpen(false)} maxWidth="sm" fullWidth>
        <DialogTitle>
          {editingColumn ? 'Редактировать колонку' : 'Добавить колонку'}
        </DialogTitle>
        <DialogContent>
          <TextField
            fullWidth
            label="Название колонки"
            value={columnForm.name}
            onChange={(e) => setColumnForm({ ...columnForm, name: e.target.value })}
            sx={{ mb: 2, mt: 1 }}
          />
          <FormControl fullWidth sx={{ mb: 2 }}>
            <InputLabel>Тип данных</InputLabel>
            <Select
              value={columnForm.type}
              label="Тип данных"
              onChange={(e) => setColumnForm({ ...columnForm, type: e.target.value as ColumnType })}
            >
              <MenuItem value={ColumnType.TEXT}>Текст</MenuItem>
              <MenuItem value={ColumnType.NUMBER}>Число</MenuItem>
              <MenuItem value={ColumnType.DATE}>Дата</MenuItem>
              <MenuItem value={ColumnType.BOOLEAN}>Да/Нет</MenuItem>
            </Select>
          </FormControl>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setColumnDialogOpen(false)}>Отмена</Button>
          <Button onClick={handleSaveColumn} variant="contained">
            {editingColumn ? 'Сохранить' : 'Добавить'}
          </Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
};

export default TableEditorPage;