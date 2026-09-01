package com.uade.tpejemplo.dto.response;

import com.uade.tpejemplo.model.Cobranza;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CobranzaResponse {

    private Long id;
    private Long idCredito;
    private Integer idCuota;
    private BigDecimal importe;
    private LocalDate fechaCobranza;
    private boolean anulada;

    public static CobranzaResponse desde(Cobranza cobranza) {
        return new CobranzaResponse(
            cobranza.getId(),
            cobranza.getCuota().getId().getIdCredito(),
            cobranza.getCuota().getId().getIdCuota(),
            cobranza.getImporte(),
            cobranza.getFechaCobranza(),
            cobranza.isAnulada()
        );
    }
}
