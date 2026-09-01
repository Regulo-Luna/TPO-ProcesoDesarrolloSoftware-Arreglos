package com.uade.tpejemplo.dto.response;

import com.uade.tpejemplo.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
    
    private Long id;
    private String username;
    private String rol;
    private boolean puedeAnularCredito;
    private boolean puedeAnularCobranza;

}