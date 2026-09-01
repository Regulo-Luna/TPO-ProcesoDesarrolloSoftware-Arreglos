import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchMetas, addMeta, editMeta, removeMeta } from '../store/slices/metaCobranzaSlice';

const MetaCobranza = () => {
    const dispatch = useDispatch();
    const { lista: metas, loading, error } = useSelector((state) => state.metas);
    
    const { user } = useSelector((state) => state.auth);
    const isAdmin = user?.rol === 'ADMIN';
    const [formData, setFormData] = useState({ mes: '', montoObjetivo: '' });
    const [isEditing, setIsEditing] = useState(false);
    const [editingId, setEditingId] = useState(null);

    useEffect(() => {
        dispatch(fetchMetas());
    }, [dispatch]);

    const startEdit = (meta) => {
        setIsEditing(true);
        setEditingId(meta.id);
        setFormData({ mes: meta.mes, montoObjetivo: meta.montoObjetivo });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (isEditing) {
                await dispatch(editMeta({ id: editingId, data: formData })).unwrap();
            } else {
                await dispatch(addMeta(formData)).unwrap();
            }
            
            setFormData({ mes: '', montoObjetivo: '' });
            setIsEditing(false);
            setEditingId(null);
            
            dispatch(fetchMetas());
        } catch (err) {
            alert("Error al guardar/actualizar: " + (err || "Error desconocido"));
        }
    };

    const handleDelete = async (id) => {
        if(window.confirm("¿Seguro que deseas eliminar esta meta?")) {
            try {
                await dispatch(removeMeta(id)).unwrap();
                dispatch(fetchMetas());
            } catch (err) {
                alert("Error al eliminar: " + err);
            }
        }
    };

    return (
        <div style={styles.container}>
            <h2>Gestión de Metas Financieras</h2>
            
            {error && <p style={{ color: 'red' }}>Error: {error}</p>}
            
            {isAdmin && (
                <form onSubmit={handleSubmit} style={styles.form}>
                    <input 
                        placeholder="Mes (ej: Enero)" 
                        value={formData.mes} 
                        onChange={e => setFormData({...formData, mes: e.target.value})} 
                        required 
                        style={styles.input}
                        disabled={loading}
                    />
                    <input 
                        type="number" 
                        placeholder="Monto Objetivo" 
                        value={formData.montoObjetivo} 
                        onChange={e => setFormData({...formData, montoObjetivo: e.target.value})} 
                        required 
                        style={styles.input}
                        disabled={loading}
                    />
                    <button type="submit" style={isEditing ? styles.buttonUpdate : styles.buttonAdd} disabled={loading}>
                        {loading ? "Cargando..." : (isEditing ? "Actualizar Meta" : "Agregar Meta")}
                    </button>
                    {isEditing && (
                        <button type="button" onClick={() => { setIsEditing(false); setFormData({ mes: '', montoObjetivo: '' }); }} style={styles.buttonCancel} disabled={loading}>
                            Cancelar
                        </button>
                    )}
                </form>
            )}

            <table style={styles.table}>
                <thead>
                    <tr>
                        <th>Mes</th>
                        <th>Monto</th>
                        {/* 2. Ocultamos el título de la columna de acciones */}
                        {isAdmin && <th>Acciones</th>} 
                    </tr>
                </thead>
                <tbody>
                    {loading && metas?.length === 0 && (
                        <tr><td colSpan={isAdmin ? "3" : "2"}>Cargando metas...</td></tr>
                    )}
                    {metas?.map(m => (
                        <tr key={m.id}>
                            <td>{m.mes}</td>
                            <td>${Number(m.montoObjetivo).toLocaleString()}</td>
                            
                            {isAdmin && (
                                <td>
                                    <button onClick={() => startEdit(m)} style={styles.buttonEdit} disabled={loading}>Editar</button>
                                    <button onClick={() => handleDelete(m.id)} style={styles.buttonDelete} disabled={loading}>Eliminar</button>
                                </td>
                            )}
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

const styles = {
    container: { padding: '20px', maxWidth: '600px', margin: '0 auto' },
    form: { display: 'flex', gap: '10px', marginBottom: '20px' },
    input: { padding: '8px', flex: 1 },
    buttonAdd: { padding: '8px 16px', backgroundColor: '#28a745', color: 'white', border: 'none', cursor: 'pointer' },
    buttonUpdate: { padding: '8px 16px', backgroundColor: '#007bff', color: 'white', border: 'none', cursor: 'pointer' },
    buttonCancel: { padding: '8px 16px', backgroundColor: '#6c757d', color: 'white', border: 'none', cursor: 'pointer' },
    buttonEdit: { marginRight: '5px', backgroundColor: '#ffc107', border: 'none', cursor: 'pointer', padding: '5px' },
    buttonDelete: { backgroundColor: '#dc3545', color: 'white', border: 'none', cursor: 'pointer', padding: '5px' },
    table: { width: '100%', borderCollapse: 'collapse', marginTop: '10px' }
};

export default MetaCobranza;