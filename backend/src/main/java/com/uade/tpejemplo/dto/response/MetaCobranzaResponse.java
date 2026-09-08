package com.uade.tpejemplo.dto.response;

import com.uade.tpejemplo.model.MetaCobranza;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MetaCobranzaResponse {

    private Long id;
    private String mes;
    private BigDecimal montoObjetivo;

    public static MetaCobranzaResponse desde(MetaCobranza meta) {
        return new MetaCobranzaResponse(meta.getId(), meta.getMes(), meta.getMontoObjetivo());
    }
}
