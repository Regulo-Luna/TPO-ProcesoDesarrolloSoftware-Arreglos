package com.uade.tpejemplo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.uade.tpejemplo.service.AdminService;
import com.uade.tpejemplo.dto.request.PermisosRequest;
import com.uade.tpejemplo.dto.response.UsuarioResponse;
import com.uade.tpejemplo.model.Rol;
import com.uade.tpejemplo.model.Usuario;
import com.uade.tpejemplo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PutMapping("/usuarios/{id}/permisos")
    @PreAuthorize("hasRole('ADMIN')")
    public UsuarioResponse actualizarPermisos(
            @PathVariable Long id,
            @RequestBody PermisosRequest request) {

        return adminService.actualizarPermisos(id, request);
    }
}

