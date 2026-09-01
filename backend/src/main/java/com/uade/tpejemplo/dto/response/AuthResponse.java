package com.uade.tpejemplo.dto.response;

import com.uade.tpejemplo.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String username;
    private String rol;
    private boolean puedeAnularCredito;
    private boolean puedeAnularCobranza;

    public static AuthResponse desde(String token, Usuario usuario) {
        return new AuthResponse(
            token,
            usuario.getUsername(),
            usuario.getRol().name(),
            usuario.isPuedeAnularCredito(),
            usuario.isPuedeAnularCobranza()
        );
    }
}
