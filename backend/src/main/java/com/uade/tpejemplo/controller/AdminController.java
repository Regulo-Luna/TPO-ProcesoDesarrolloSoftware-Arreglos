package com.uade.tpejemplo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.uade.tpejemplo.dto.request.PermisosRequest;
import com.uade.tpejemplo.dto.response.UsuarioResponse;
import com.uade.tpejemplo.exception.ResourceNotFoundException;
import com.uade.tpejemplo.model.Rol;
import com.uade.tpejemplo.model.Usuario;
import com.uade.tpejemplo.repository.UsuarioRepository;

import java.util.List;

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
        return usuarioRepository.findByRol(Rol.USER).stream()
            .map(UsuarioResponse::desde)
            .toList();
    }

    @PutMapping("/usuarios/{id}/permisos")
    public UsuarioResponse actualizarPermisos(@PathVariable Long id, @RequestBody PermisosRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

        usuario.setPuedeAnularCredito(request.isPuedeAnularCredito());
        usuario.setPuedeAnularCobranza(request.isPuedeAnularCobranza());

        usuarioRepository.save(usuario);

        return UsuarioResponse.desde(usuario);
    }
}
