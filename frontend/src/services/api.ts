import axios, { AxiosInstance, AxiosResponse } from 'axios';
import {
  User,
  LoginRequest,
  RegisterRequest,
  AuthResponse,
  DataTable,
  Column,
  Row,
  Cell,
  Coefficient,
  Rule,
  CreateTableRequest,
  UpdateTableRequest,
  CreateColumnRequest,
  UpdateColumnRequest,
  CreateRowRequest,
  UpdateCellRequest,
  CreateCoefficientRequest,
  UpdateCoefficientRequest,
  CreateRuleRequest,
  PaginatedResponse
} from '../types';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

// Create axios instance with default config
const api: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor to add auth token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Response interceptor for error handling
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('currentUser');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export const authApi = {
  login: async (data: LoginRequest): Promise<User> => {
    const response: AxiosResponse<AuthResponse> = await api.post('/auth/login', data);
    const { token, user } = response.data;
    localStorage.setItem('token', token);
    localStorage.setItem('currentUser', JSON.stringify(user));
    return user;
  },

  register: async (data: RegisterRequest): Promise<void> => {
    await api.post('/auth/register', data);
  },

  getCurrentUser: async (): Promise<User | null> => {
    try {
      const response: AxiosResponse<User> = await api.get('/users/me');
      return response.data;
    } catch {
      return null;
    }
  },
};

export const usersApi = {
  getAllUsers: async (): Promise<User[]> => {
    const response: AxiosResponse<User[]> = await api.get('/users');
    return response.data;
  },

  updateUser: async (id: number, data: Partial<User>): Promise<User> => {
    const response: AxiosResponse<User> = await api.put(`/users/${id}`, data);
    return response.data;
  },

  changePassword: async (userId: number, oldPassword: string, newPassword: string): Promise<void> => {
    await api.put(`/users/${userId}/change-password`, null, {
      params: { oldPassword, newPassword }
    });
  },

  deleteUser: async (id: number): Promise<void> => {
    await api.delete(`/users/${id}`);
  },
};

export const tablesApi = {
  getDataTables: async (): Promise<DataTable[]> => {
    const response: AxiosResponse<DataTable[]> = await api.get('/datatables');
    return response.data;
  },

  getDataTableById: async (id: number): Promise<DataTable> => {
    const response: AxiosResponse<DataTable> = await api.get(`/datatables/${id}`);
    return response.data;
  },

  createDataTable: async (data: CreateTableRequest): Promise<DataTable> => {
    const response: AxiosResponse<DataTable> = await api.post('/datatables', data);
    return response.data;
  },

  updateDataTable: async (id: number, data: UpdateTableRequest): Promise<DataTable> => {
    const response: AxiosResponse<DataTable> = await api.put(`/datatables/${id}`, data);
    return response.data;
  },

  deleteDataTable: async (id: number): Promise<void> => {
    await api.delete(`/datatables/${id}`);
  },
};

export const columnsApi = {
  getColumns: async (tableId: number, page?: number): Promise<PaginatedResponse<Column>> => {
    const response: AxiosResponse<PaginatedResponse<Column>> = await api.get('/columns', {
      params: { tableId, page }
    });
    return response.data;
  },

  createColumn: async (data: CreateColumnRequest): Promise<Column> => {
    const response: AxiosResponse<Column> = await api.post('/columns', data);
    return response.data;
  },

  updateColumn: async (id: number, data: UpdateColumnRequest): Promise<Column> => {
    const response: AxiosResponse<Column> = await api.put(`/columns/${id}`, data);
    return response.data;
  },

  deleteColumn: async (id: number): Promise<void> => {
    await api.delete(`/columns/${id}`);
  },
};

export const rowsApi = {
  getRows: async (tableId: number): Promise<Row[]> => {
    const response: AxiosResponse<Row[]> = await api.get('/rows', {
      params: { tableId }
    });
    return response.data;
  },

  createRow: async (data: CreateRowRequest): Promise<Row> => {
    const response: AxiosResponse<Row> = await api.post('/rows', data);
    return response.data;
  },

  deleteRow: async (id: number): Promise<void> => {
    await api.delete(`/rows/${id}`);
  },
};

export const cellsApi = {
  getCells: async (tableId: number, page?: number): Promise<PaginatedResponse<Cell>> => {
    const response: AxiosResponse<PaginatedResponse<Cell>> = await api.get('/cells', {
      params: { tableId, page }
    });
    return response.data;
  },

  saveCell: async (data: UpdateCellRequest & { rowId: number; columnId: number }): Promise<Cell> => {
    const response: AxiosResponse<Cell> = await api.post('/cells', data);
    return response.data;
  },

  updateCell: async (id: number, data: UpdateCellRequest): Promise<Cell> => {
    const response: AxiosResponse<Cell> = await api.put(`/cells/${id}`, data);
    return response.data;
  },
};

export const coefficientsApi = {
  getCoefficients: async (active: boolean = true): Promise<Coefficient[]> => {
    const response: AxiosResponse<Coefficient[]> = await api.get('/coefficients');
    const coeffs = response.data;
    return active ? coeffs.filter(c => c.isActive) : coeffs;
  },

  createCoefficient: async (data: CreateCoefficientRequest): Promise<Coefficient> => {
    const response: AxiosResponse<Coefficient> = await api.post('/coefficients', data);
    return response.data;
  },

  updateCoefficient: async (id: number, data: UpdateCoefficientRequest): Promise<Coefficient> => {
    const response: AxiosResponse<Coefficient> = await api.put(`/coefficients/${id}`, data);
    return response.data;
  },

  deleteCoefficient: async (id: number): Promise<void> => {
    await api.delete(`/coefficients/${id}`);
  },
};

export const rulesApi = {
  getRulesByCoefficient: async (coeffId: number): Promise<Rule[]> => {
    const response: AxiosResponse<Rule[]> = await api.get('/rules', {
      params: { coefficientId: coeffId }
    });
    return response.data;
  },

  createRule: async (data: CreateRuleRequest): Promise<Rule> => {
    const response: AxiosResponse<Rule> = await api.post('/rules', data);
    return response.data;
  },
};