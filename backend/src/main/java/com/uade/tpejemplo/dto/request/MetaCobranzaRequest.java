package com.uade.tpejemplo.dto.request;

import com.uade.tpejemplo.model.MetaCobranza;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaCobranzaRequest {

    private Long id;

    @NotBlank(message = "El mes es obligatorio")
    private String mes;

    @NotNull(message = "El monto objetivo es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    private BigDecimal montoObjetivo;

    public static MetaCobranzaRequest desde(MetaCobranza meta) {
        return new MetaCobranzaRequest(meta.getId(), meta.getMes(), meta.getMontoObjetivo());
    }
}
