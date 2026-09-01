import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { getUsuarios, updatePermisos } from '../../api/admin';

export const fetchUsuariosAdmin = createAsyncThunk('permisos/fetchAll', async (_, { rejectWithValue }) => {
  try {
    return await getUsuarios();
  } catch (err) {
    return rejectWithValue(err.message);
  }
});

export const togglePermiso = createAsyncThunk('permisos/toggle', async ({ id, permisos }, { rejectWithValue }) => {
  try {
    return await updatePermisos(id, permisos);
  } catch (err) {
    return rejectWithValue(err.message);
  }
});

const permisosSlice = createSlice({
  name: 'permisos',
  initialState: {
    lista: [],
    loading: false,
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchUsuariosAdmin.pending, (state) => { state.loading = true; state.error = null; })
      .addCase(fetchUsuariosAdmin.fulfilled, (state, action) => { state.loading = false; state.lista = action.payload; })
      .addCase(fetchUsuariosAdmin.rejected, (state, action) => { state.loading = false; state.error = action.payload; })
      
      .addCase(togglePermiso.fulfilled, (state, action) => {
        const usuarioActualizado = action.payload;
        const index = state.lista.findIndex(u => u.id === usuarioActualizado.id);
        if (index !== -1) {
          state.lista[index] = usuarioActualizado;
        }
      });
  },
});

export default permisosSlice.reducer;