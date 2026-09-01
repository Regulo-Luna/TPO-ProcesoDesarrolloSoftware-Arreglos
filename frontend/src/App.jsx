import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import PrivateRoute from './components/PrivateRoute';
import Navbar from './components/Navbar';
import Login from './pages/Login';
import Register from './pages/Register';
import Clientes from './pages/Clientes';
import Creditos from './pages/Creditos';
import Cobranzas from './pages/Cobranzas';
import Dashboard from './pages/Dashboard.jsx';
import GestorPermisos from './pages/GestorPermisos'
import AdminRoute from './components/AdminRoute';

export default function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path="/login"     element={<Login />} />
        <Route path="/register"  element={<Register />} />
        <Route path="/clientes"  element={<PrivateRoute><Clientes /></PrivateRoute>} />
        <Route path="/creditos"  element={<PrivateRoute><Creditos /></PrivateRoute>} />
        <Route path="/cobranzas" element={<PrivateRoute><Cobranzas /></PrivateRoute>} />
        <Route path="/estadisticas" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
        <Route path="/admin/permisos" element={<AdminRoute><GestorPermisos /></AdminRoute>} />
        <Route path="*"          element={<Navigate to="/login" replace />} />
      </Routes>
    </BrowserRouter>
  );
}
