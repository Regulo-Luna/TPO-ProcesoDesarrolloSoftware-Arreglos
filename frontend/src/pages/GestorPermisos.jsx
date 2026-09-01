import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchUsuariosAdmin, togglePermiso } from '../store/slices/permisosSlice';

  const GestorPermisos = () => {
  const dispatch = useDispatch();
  const { lista: usuarios, loading, error } = useSelector((state) => state.permisos);

  useEffect(() => {
    dispatch(fetchUsuariosAdmin());
  }, [dispatch]);

  const handleCheckboxChange = (usuario, campoPermiso) => {
    const nuevosPermisos = {
      puedeAnularCredito: usuario.puedeAnularCredito,
      puedeAnularCobranza: usuario.puedeAnularCobranza,
      [campoPermiso]: !usuario[campoPermiso]
    };
    
    dispatch(togglePermiso({ id: usuario.id, permisos: nuevosPermisos }));
  };

  return (
    <div style={styles.page}>
      <h2 style={styles.title}>Gestor de Permisos (Panel de Administrador)</h2>
      
      {error && <div style={styles.error}>Error: {error}</div>}
      
      <div style={styles.card}>
        {loading && usuarios.length === 0 ? (
          <p style={styles.loading}>Cargando lista de usuarios...</p>
        ) : usuarios.length === 0 ? (
          <p style={styles.empty}>No hay usuarios con rol USER registrados en el sistema.</p>
        ) : (
          <table style={styles.table}>
            <thead>
              <tr>
                <th style={styles.th}>Usuario</th>
                <th style={styles.thCenter}>¿Puede Anular Crédito?</th>
                <th style={styles.thCenter}>¿Puede Anular Cobranza?</th>
              </tr>
            </thead>
            <tbody>
              {usuarios.map(u => (
                <tr key={u.id} style={styles.tr}>
                  <td style={styles.td}>{u.username}</td>
                  <td style={styles.tdCenter}>
                    <input 
                      type="checkbox" 
                      checked={u.puedeAnularCredito}
                      onChange={() => handleCheckboxChange(u, 'puedeAnularCredito')}
                      disabled={loading} // Evita que hagan doble clic mientras se guarda
                      style={styles.checkbox}
                    />
                  </td>
                  <td style={styles.tdCenter}>
                    <input 
                      type="checkbox" 
                      checked={u.puedeAnularCobranza}
                      onChange={() => handleCheckboxChange(u, 'puedeAnularCobranza')}
                      disabled={loading}
                      style={styles.checkbox}
                    />
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
};

const styles = {
  page: { padding: '20px', fontFamily: 'Arial, sans-serif', maxWidth: '800px', margin: '0 auto' },
  title: { color: '#333', borderBottom: '2px solid #eee', paddingBottom: '10px' },
  card: { 
    background: '#fff', 
    padding: '20px', 
    borderRadius: '8px', 
    boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
    marginTop: '20px'
  },
  table: { width: '100%', borderCollapse: 'collapse' },
  th: { padding: '12px', textAlign: 'left', borderBottom: '2px solid #ddd', color: '#555' },
  thCenter: { padding: '12px', textAlign: 'center', borderBottom: '2px solid #ddd', color: '#555' },
  tr: { borderBottom: '1px solid #eee' },
  td: { padding: '12px', color: '#333' },
  tdCenter: { padding: '12px', textAlign: 'center' },
  checkbox: { transform: 'scale(1.5)', cursor: 'pointer' },
  error: { color: 'white', backgroundColor: '#e53935', padding: '10px', borderRadius: '4px', marginBottom: '10px' },
  empty: { color: '#777', fontStyle: 'italic', textAlign: 'center' },
  loading: { color: '#0056b3', textAlign: 'center', fontWeight: 'bold' }
};

export default GestorPermisos;