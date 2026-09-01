import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { getMetas, createMeta, updateMeta, deleteMeta } from '../../api/metaCobranza';

export const fetchMetas = createAsyncThunk('metas/fetchAll', async (_, { rejectWithValue }) => {
    try {
        const response = await getMetas();
        return response || [];
    } catch (error) {
        return rejectWithValue(error.message);
    }
});

export const addMeta = createAsyncThunk('metas/add', async (data, { rejectWithValue }) => {
    try {
        await createMeta(data);
        return data; 
    } catch (error) {
        return rejectWithValue(error.message);
    }
});

export const editMeta = createAsyncThunk('metas/edit', async ({ id, data }, { rejectWithValue }) => {
    try {
        await updateMeta(id, data);
        return { id, data };
    } catch (error) {
        return rejectWithValue(error.message);
    }
});

export const removeMeta = createAsyncThunk('metas/remove', async (id, { rejectWithValue }) => {
    try {
        await deleteMeta(id);
        return id;
    } catch (error) {
        return rejectWithValue(error.message);
    }
});

const metaCobranzaSlice = createSlice({
    name: 'metas',
    initialState: {
        lista: [],
        loading: false,
        error: null,
    },
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(fetchMetas.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchMetas.fulfilled, (state, action) => {
                state.loading = false;
                state.lista = action.payload;
            })
            .addCase(fetchMetas.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            })
            .addCase(addMeta.pending, (state) => { state.loading = true; })
            .addCase(addMeta.fulfilled, (state) => { state.loading = false; })
            .addCase(addMeta.rejected, (state, action) => { state.loading = false; state.error = action.payload; })
            
            .addCase(editMeta.pending, (state) => { state.loading = true; })
            .addCase(editMeta.fulfilled, (state) => { state.loading = false; })
            .addCase(editMeta.rejected, (state, action) => { state.loading = false; state.error = action.payload; })
            
            .addCase(removeMeta.pending, (state) => { state.loading = true; })
            .addCase(removeMeta.fulfilled, (state) => { state.loading = false; })
            .addCase(removeMeta.rejected, (state, action) => { state.loading = false; state.error = action.payload; });
    },
});

export default metaCobranzaSlice.reducer;