import React, { useState, useEffect } from 'react';
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
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Switch,
  FormControlLabel,
  IconButton,
  Box,
  Alert
} from '@mui/material';
import { Add, Edit, Delete } from '@mui/icons-material';
import { toast } from 'react-toastify';
import { coefficientsApi, rulesApi } from '../services/api';
import { Coefficient, Rule } from '../types';

const CoefficientsPage: React.FC = () => {
  const [coefficients, setCoefficients] = useState<Coefficient[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [editingCoeff, setEditingCoeff] = useState<Coefficient | null>(null);
  const [rulesDialogOpen, setRulesDialogOpen] = useState(false);
  const [selectedCoeff, setSelectedCoeff] = useState<Coefficient | null>(null);
  const [rules, setRules] = useState<Rule[]>([]);

  const [formData, setFormData] = useState({
    name: '',
    value: 0,
    description: '',
    isActive: true
  });

  const [ruleFormData, setRuleFormData] = useState({
    condition: '',
    multiplier: 1,
    description: ''
  });

  const [editingRule, setEditingRule] = useState<Rule | null>(null);

  useEffect(() => {
    loadCoefficients();
  }, []);

  const loadCoefficients = async () => {
    try {
      setLoading(true);
      const data = await coefficientsApi.getCoefficients(false);
      setCoefficients(data);
      setError(null);
    } catch (err) {
      setError('Ошибка загрузки коэффициентов');
      toast.error('Ошибка загрузки коэффициентов');
    } finally {
      setLoading(false);
    }
  };

  const loadRules = async (coeffId: number) => {
    try {
      const data = await rulesApi.getRulesByCoefficient(coeffId);
      setRules(data);
    } catch (err) {
      toast.error('Ошибка загрузки правил');
    }
  };

  const handleOpenDialog = (coeff?: Coefficient) => {
    if (coeff) {
      setEditingCoeff(coeff);
      setFormData({
        name: coeff.name,
        value: coeff.value,
        description: coeff.description || '',
        isActive: coeff.isActive
      });
    } else {
      setEditingCoeff(null);
      setFormData({
        name: '',
        value: 0,
        description: '',
        isActive: true
      });
    }
    setDialogOpen(true);
  };

  const handleCloseDialog = () => {
    setDialogOpen(false);
    setEditingCoeff(null);
  };

  const handleSubmit = async () => {
    try {
      if (editingCoeff) {
        await coefficientsApi.updateCoefficient(editingCoeff.id, formData);
        toast.success('Коэффициент обновлен');
      } else {
        await coefficientsApi.createCoefficient(formData);
        toast.success('Коэффициент создан');
      }
      handleCloseDialog();
      loadCoefficients();
    } catch (err) {
      toast.error('Ошибка сохранения коэффициента');
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Вы уверены, что хотите удалить этот коэффициент?')) {
      try {
        await coefficientsApi.deleteCoefficient(id);
        toast.success('Коэффициент удален');
        loadCoefficients();
      } catch (err) {
        toast.error('Ошибка удаления коэффициента');
      }
    }
  };

  const handleOpenRulesDialog = async (coeff: Coefficient) => {
    setSelectedCoeff(coeff);
    await loadRules(coeff.id);
    setRulesDialogOpen(true);
  };

  const handleCloseRulesDialog = () => {
    setRulesDialogOpen(false);
    setSelectedCoeff(null);
    setRules([]);
  };

  const handleAddRule = async () => {
    if (!selectedCoeff) return;

    try {
      if (editingRule) {
        await rulesApi.updateRule(editingRule.id, {
          ...ruleFormData,
          coefficientId: selectedCoeff.id
        });
        toast.success('Правило обновлено');
        setEditingRule(null);
      } else {
        await rulesApi.createRule({
          ...ruleFormData,
          coefficientId: selectedCoeff.id
        });
        toast.success('Правило добавлено');
      }
      setRuleFormData({ condition: '', multiplier: 1, description: '' });
      loadRules(selectedCoeff.id);
    } catch (err) {
      toast.error('Ошибка сохранения правила');
    }
  };

  const handleEditRule = (rule: Rule) => {
    setEditingRule(rule);
    setRuleFormData({
      condition: rule.condition,
      multiplier: rule.multiplier,
      description: rule.description || ''
    });
  };

  const handleDeleteRule = async (ruleId: number) => {
    if (window.confirm('Вы уверены, что хотите удалить это правило?')) {
      try {
        await rulesApi.deleteRule(ruleId);
        toast.success('Правило удалено');
        if (selectedCoeff) {
          loadRules(selectedCoeff.id);
        }
      } catch (err) {
        toast.error('Ошибка удаления правила');
      }
    }
  };

  const handleCancelEditRule = () => {
    setEditingRule(null);
    setRuleFormData({ condition: '', multiplier: 1, description: '' });
  };

  if (loading) {
    return (
      <Container maxWidth="lg" sx={{ mt: 4 }}>
        <Typography>Загрузка...</Typography>
      </Container>
    );
  }

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
        <Typography variant="h4" component="h1">
          Коэффициенты
        </Typography>
        <Button
          variant="contained"
          startIcon={<Add />}
          onClick={() => handleOpenDialog()}
        >
          Добавить коэффициент
        </Button>
      </Box>

      {error && (
        <Alert severity="error" sx={{ mb: 3 }}>
          {error}
        </Alert>
      )}

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Название</TableCell>
              <TableCell>Значение</TableCell>
              <TableCell>Описание</TableCell>
              <TableCell>Активен</TableCell>
              <TableCell>Действия</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {coefficients.map((coeff) => (
              <TableRow key={coeff.id}>
                <TableCell>{coeff.name}</TableCell>
                <TableCell>{coeff.value}</TableCell>
                <TableCell>{coeff.description}</TableCell>
                <TableCell>{coeff.isActive ? 'Да' : 'Нет'}</TableCell>
                <TableCell>
                  <IconButton onClick={() => handleOpenDialog(coeff)}>
                    <Edit />
                  </IconButton>
                  <IconButton onClick={() => handleOpenRulesDialog(coeff)}>
                    Правила
                  </IconButton>
                  <IconButton onClick={() => handleDelete(coeff.id)}>
                    <Delete />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      {/* Coefficient Dialog */}
      <Dialog open={dialogOpen} onClose={handleCloseDialog} maxWidth="sm" fullWidth>
        <DialogTitle>
          {editingCoeff ? 'Редактировать коэффициент' : 'Добавить коэффициент'}
        </DialogTitle>
        <DialogContent>
          <TextField
            fullWidth
            label="Название"
            value={formData.name}
            onChange={(e) => setFormData({ ...formData, name: e.target.value })}
            sx={{ mb: 2, mt: 1 }}
          />
          <TextField
            fullWidth
            label="Значение"
            type="number"
            value={formData.value}
            onChange={(e) => setFormData({ ...formData, value: parseFloat(e.target.value) || 0 })}
            sx={{ mb: 2 }}
          />
          <TextField
            fullWidth
            label="Описание"
            multiline
            rows={3}
            value={formData.description}
            onChange={(e) => setFormData({ ...formData, description: e.target.value })}
            sx={{ mb: 2 }}
          />
          <FormControlLabel
            control={
              <Switch
                checked={formData.isActive}
                onChange={(e) => setFormData({ ...formData, isActive: e.target.checked })}
              />
            }
            label="Активен"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog}>Отмена</Button>
          <Button onClick={handleSubmit} variant="contained">
            {editingCoeff ? 'Сохранить' : 'Создать'}
          </Button>
        </DialogActions>
      </Dialog>

      {/* Rules Dialog */}
      <Dialog open={rulesDialogOpen} onClose={handleCloseRulesDialog} maxWidth="md" fullWidth>
        <DialogTitle>
          Правила для коэффициента "{selectedCoeff?.name}"
        </DialogTitle>
        <DialogContent>
          <Box sx={{ mb: 3 }}>
            <Typography variant="h6" sx={{ mb: 2 }}>
              {editingRule ? 'Редактировать правило' : 'Добавить новое правило'}
            </Typography>
            <TextField
              fullWidth
              label="Условие"
              value={ruleFormData.condition}
              onChange={(e) => setRuleFormData({ ...ruleFormData, condition: e.target.value })}
              sx={{ mb: 2 }}
            />
            <TextField
              fullWidth
              label="Множитель"
              type="number"
              value={ruleFormData.multiplier}
              onChange={(e) => setRuleFormData({ ...ruleFormData, multiplier: parseFloat(e.target.value) || 1 })}
              sx={{ mb: 2 }}
            />
            <TextField
              fullWidth
              label="Описание"
              value={ruleFormData.description}
              onChange={(e) => setRuleFormData({ ...ruleFormData, description: e.target.value })}
              sx={{ mb: 2 }}
            />
            <Box sx={{ display: 'flex', gap: 1 }}>
              <Button onClick={handleAddRule} variant="contained">
                {editingRule ? 'Сохранить правило' : 'Добавить правило'}
              </Button>
              {editingRule && (
                <Button onClick={handleCancelEditRule} variant="outlined">
                  Отмена
                </Button>
              )}
            </Box>
          </Box>

          <Typography variant="h6" sx={{ mb: 2 }}>Существующие правила</Typography>
          {rules.length === 0 ? (
            <Typography color="text.secondary">Нет правил</Typography>
          ) : (
            rules.map((rule) => (
              <Box key={rule.id} sx={{ p: 2, border: '1px solid #ddd', borderRadius: 1, mb: 1, display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
                <Box>
                  <Typography><strong>Условие:</strong> {rule.condition}</Typography>
                  <Typography><strong>Множитель:</strong> {rule.multiplier}</Typography>
                  <Typography><strong>Описание:</strong> {rule.description}</Typography>
                </Box>
                <Box sx={{ display: 'flex', gap: 1 }}>
                  <IconButton size="small" onClick={() => handleEditRule(rule)} color="primary">
                    <Edit fontSize="small" />
                  </IconButton>
                  <IconButton size="small" onClick={() => handleDeleteRule(rule.id)} color="error">
                    <Delete fontSize="small" />
                  </IconButton>
                </Box>
              </Box>
            ))
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseRulesDialog}>Закрыть</Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
};

export default CoefficientsPage;
