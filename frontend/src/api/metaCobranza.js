import { api } from './apiClient';

export const getMetas = () => api.get('/metas');
export const createMeta = (meta) => api.post('/metas', meta);
export const updateMeta = (id, meta) => api.put(`/metas/${id}`, meta);
export const deleteMeta = (id) => api.delete(`/metas/${id}`);