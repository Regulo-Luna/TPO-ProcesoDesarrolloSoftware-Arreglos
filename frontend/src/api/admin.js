import { api } from './apiClient';

export const getUsuarios = () => api.get('/admin/usuarios');
export const updatePermisos = (id, permisos) => api.put(`/admin/usuarios/${id}/permisos`, permisos);