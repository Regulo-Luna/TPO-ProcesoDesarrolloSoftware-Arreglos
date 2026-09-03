package com.uade.tpejemplo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.uade.tpejemplo.dto.request.RolRequest;
import com.uade.tpejemplo.dto.response.UsuarioResponse;
import com.uade.tpejemplo.model.Rol;
import com.uade.tpejemplo.model.Usuario;
import com.uade.tpejemplo.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UsuarioRepository usuarioRepository;

    public AdminController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/usuarios")
    public List<UsuarioResponse> listarUsuarios() {
        // Traemos todos los usuarios y excluímos a los administradores
        return usuarioRepository.findAll().stream()
            .filter(u -> u.getRol() != Rol.ADMIN)
            .map(u -> {
                String nombreRol = (u.getRol() != null) ? u.getRol().name() : "SIN_ROL";
                return new UsuarioResponse(
                    u.getId(), 
                    u.getUsername(), 
                    nombreRol, 
                    u.isPuedeAnularCredito(), 
                    u.isPuedeAnularCobranza()
                );
            })
            .collect(Collectors.toList());
    }

    @PutMapping("/usuarios/{id}/rol")
    public UsuarioResponse actualizarRol(@PathVariable Long id, @RequestBody RolRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validaciones estrictas de seguridad:
        // 1. No se puede modificar a un usuario que ya es ADMIN
        if (usuario.getRol() == Rol.ADMIN) {
            throw new RuntimeException("No se puede modificar a un Administrador.");
        }
        
        // 2. Solo permitimos asignar roles USER o SUPERVISOR
        if (request.getRol() == Rol.ADMIN) {
            throw new RuntimeException("No tienes permisos para otorgar el rol de Administrador.");
        }

        usuario.setRol(request.getRol());
        usuarioRepository.save(usuario);

        return new UsuarioResponse(
            usuario.getId(), 
            usuario.getUsername(), 
            usuario.getRol().name(), 
            usuario.isPuedeAnularCredito(), 
            usuario.isPuedeAnularCobranza()
        );
    }
}