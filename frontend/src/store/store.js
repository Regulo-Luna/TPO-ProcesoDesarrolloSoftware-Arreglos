import { configureStore } from '@reduxjs/toolkit';
import authReducer from './slices/authSlice';
import clientesReducer from './slices/clientesSlice';
import creditosReducer from './slices/creditosSlice';
import cobranzasReducer from './slices/cobranzasSlice';
import dashboardReducer from './slices/dashboardSlice';
import metasReducer from './slices/metaCobranzaSlice';
import permisosReducer from './slices/permisosSlice';

export const store = configureStore({
  reducer: {
    auth: authReducer,
    clientes: clientesReducer,
    creditos: creditosReducer,
    dashboard: dashboardReducer,
    metas: metasReducer,
    cobranzas: cobranzasReducer,
    permisos: permisosReducer
  },
});