package com.uade.tpejemplo.service;

import com.uade.tpejemplo.model.Usuario;
import com.uade.tpejemplo.repository.UsuarioRepository;
import com.uade.tpejemplo.dto.request.PermisosRequest;
import com.uade.tpejemplo.dto.response.UsuarioResponse;
import com.uade.tpejemplo.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioResponse actualizarPermisos(
            Long id,
            PermisosRequest request) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuario",
                                "id",
                                id
                        )
                );

        usuario.setPuedeAnularCredito(
                request.isPuedeAnularCredito()
        );

        usuario.setPuedeAnularCobranza(
                request.isPuedeAnularCobranza()
        );

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