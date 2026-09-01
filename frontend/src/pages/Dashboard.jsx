import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchEstadisticas } from '../store/slices/dashboardSlice'; // Ajusta la ruta si es necesario
import MetaCobranza from './MetaCobranza';

const Dashboard = () => {
  const dispatch = useDispatch();
  
  // Extraemos la información del estado global de métricas
  const { data: estadisticas, loading, error } = useSelector((state) => state.dashboard);
  
  // NUEVO: Extraemos el usuario autenticado para saber su rol
  const { user } = useSelector((state) => state.auth);
  const isAdmin = user?.rol === 'ADMIN';

  useEffect(() => {
    // Disparamos la acción para ir a buscar los datos apenas carga la pantalla
    dispatch(fetchEstadisticas());
  }, [dispatch]);

  if (loading) return <div>Cargando métricas del sistema...</div>;
  if (error) return <div style={{ color: 'red' }}>Error: {error}</div>;

  return (
    <div style={styles.container}>
      <h2>Panel de Estadísticas {isAdmin && <span style={styles.adminBadge}>(Modo Admin)</span>}</h2>
      
      <div style={styles.tarjetasMetricas}>
        <div style={styles.tarjeta}>
           <h3>Total Clientes</h3>
           <p style={styles.valor}>{estadisticas?.cantidadClientes || 0}</p>
        </div>
        <div style={styles.tarjeta}>
           <h3>Créditos Activos</h3>
           <p style={styles.valor}>{estadisticas?.cantidadCreditos || 0}</p>
        </div>
        <div style={styles.tarjeta}>
           <h3>Monto Total Financiado</h3>
           <p style={styles.valor}>{estadisticas?.montoTotalFinanciado || 0}</p>
        </div>
        <div style={styles.tarjeta}>
           <h3>Monto Total Cobrado</h3>
           <p style={styles.valor}>{estadisticas?.montoTotalCobrado || 0}</p>
        </div>
      </div>
      
        {/* Opcional: Si quieres que SOLO el admin vea las metas, envuélvelo en isAdmin && */}
        {isAdmin && (
          <div style={{ marginTop: '30px', width: '100%' }}>
              <MetaCobranza />
          </div>
        )}
    </div>
  );
};

const styles = {
  container: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: '40px',
    backgroundColor: '#f4f7f6',
    minHeight: '100vh',
  },
  center: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    height: '100vh',
  },
  tarjetasMetricas: {
    display: 'flex',
    gap: '20px',
    justifyContent: 'center',
    flexWrap: 'wrap', // Agregado para que se acomoden bien en pantallas chicas
  },
  tarjeta: {
    backgroundColor: 'white',
    padding: '20px 40px',
    borderRadius: '12px',
    boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
    textAlign: 'center',
    minWidth: '150px',
  },
  valor: {
    fontSize: '2rem',
    fontWeight: 'bold',
    color: '#007bff',
    margin: '10px 0 0 0',
  },
  // NUEVO: Estilo para el pequeño aviso de "Modo Admin"
  adminBadge: {
    fontSize: '0.5em',
    color: 'white',
    backgroundColor: '#dc3545',
    padding: '4px 8px',
    borderRadius: '12px',
    verticalAlign: 'middle',
    marginLeft: '10px'
  }
};

export default Dashboard;