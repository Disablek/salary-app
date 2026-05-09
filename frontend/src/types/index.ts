// User and Authentication Types
export interface User {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  role: UserRole;
  isActivated: boolean;
  createdAt: string;
  updatedAt: string;
}

export enum UserRole {
  USER = 'ROLE_USER',
  SUPERUSER = 'ROLE_SUPERUSER'
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
}

export interface AuthResponse {
  token: string;
  user: User;
}

// Data Table Types
export interface DataTable {
  id: number;
  name: string;
  description?: string;
  createdBy: User;
  createdAt: string;
  updatedAt: string;
  columns: Column[];
  rows: Row[];
}

export interface Column {
  id: number;
  tableId: number;
  name: string;
  type: ColumnType;
  isRequired: boolean;
  order: number;
  createdAt: string;
}

export enum ColumnType {
  TEXT = 'TEXT',
  NUMBER = 'NUMBER',
  DATE = 'DATE',
  BOOLEAN = 'BOOLEAN'
}

export interface Row {
  id: number;
  tableId: number;
  order: number;
  createdAt: string;
  cells: Cell[];
}

export interface Cell {
  id: number;
  rowId: number;
  columnId: number;
  value: string | number | boolean | null;
  createdAt: string;
  updatedAt: string;
}

// Coefficient Types
export interface Coefficient {
  id: number;
  name: string;
  value: number;
  description?: string;
  isActive: boolean;
  createdAt: string;
  updatedAt: string;
  rules: Rule[];
}

export interface Rule {
  id: number;
  coefficientId: number;
  condition: string;
  multiplier: number;
  description?: string;
  createdAt: string;
}

// API Response Types
export interface ApiResponse<T> {
  data: T;
  message?: string;
  success: boolean;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
}

// Form Types
export interface CreateTableRequest {
  name: string;
  description?: string;
}

export interface UpdateTableRequest {
  name: string;
  description?: string;
}

export interface CreateColumnRequest {
  tableId: number;
  name: string;
  type: ColumnType;
  isRequired: boolean;
}

export interface UpdateColumnRequest {
  name: string;
  type: ColumnType;
  isRequired: boolean;
}

export interface CreateRowRequest {
  tableId: number;
}

export interface UpdateCellRequest {
  value: string | number | boolean | null;
}

export interface CreateCoefficientRequest {
  name: string;
  value: number;
  description?: string;
}

export interface UpdateCoefficientRequest {
  name: string;
  value: number;
  description?: string;
  isActive: boolean;
}

export interface CreateRuleRequest {
  coefficientId: number;
  condition: string;
  multiplier: number;
  description?: string;
}

// UI State Types
export interface LoadingState {
  isLoading: boolean;
  error: string | null;
}

export interface TableEditorState {
  table: DataTable | null;
  selectedCell: Cell | null;
  isEditing: boolean;
}