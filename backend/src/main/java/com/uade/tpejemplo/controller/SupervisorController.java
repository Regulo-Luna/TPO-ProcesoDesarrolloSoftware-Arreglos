package com.uade.tpejemplo.controller;

import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.uade.tpejemplo.dto.request.PermisosRequest;
import com.uade.tpejemplo.dto.response.UsuarioResponse;
import com.uade.tpejemplo.model.Usuario;
import com.uade.tpejemplo.repository.UsuarioRepository;
import java.util.List;

@RestController
@RequestMapping("/api/supervisor")
@PreAuthorize("hasRole('SUPERVISOR')") // Asegúrate de tener el rol 'SUPERVISOR' configurado en tu seguridad
public class SupervisorController {

    private final UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    public List<UsuarioResponse> obtenerUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        
        return usuarios.stream()
            .map(usuario -> {
                // Si el rol es null, le ponemos "SIN_ROL" en vez de explotar con Error 500
                String nombreRol = (usuario.getRol() != null) ? usuario.getRol().name() : "SIN_ROL";
                
                return new UsuarioResponse(
                    usuario.getId(), 
                    usuario.getUsername(), 
                    nombreRol,
                    usuario.isPuedeAnularCredito(), 
                    usuario.isPuedeAnularCobranza()
                );
            })
            .collect(Collectors.toList());
    }
    public SupervisorController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Endpoint exclusivo del supervisor para asignar permisos de anulación
    @PutMapping("/usuarios/{id}/permisos-anulacion")
    public UsuarioResponse actualizarPermisosAnulacion(@PathVariable Long id, @RequestBody PermisosRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
        usuario.setPuedeAnularCredito(request.isPuedeAnularCredito());
        usuario.setPuedeAnularCobranza(request.isPuedeAnularCobranza());
        
        usuarioRepository.save(usuario);
        
        return new UsuarioResponse(
            usuario.getId(), 
            usuario.getUsername(), 
            usuario.getRol().name(),
            usuario.isPuedeAnularCredito(), 
            usuario.isPuedeAnularCobranza()
        );
    }

    // Endpoint preparado para el Dashboard de estadísticas
    @GetMapping("/dashboard")
    public Object obtenerEstadisticas() {
        // TODO: Aquí integraremos la lógica del dashboard que me pasarás después.
        // Por ahora devolvemos un mock o un mensaje simple.
        return "Datos del dashboard en construcción..."; 
    }
}