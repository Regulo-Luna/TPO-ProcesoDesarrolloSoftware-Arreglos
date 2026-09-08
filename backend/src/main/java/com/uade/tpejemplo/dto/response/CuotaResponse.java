package com.uade.tpejemplo.dto.response;

import com.uade.tpejemplo.model.Cuota;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CuotaResponse {

    private Long idCredito;
    private Integer idCuota;
    private LocalDate fechaVencimiento;
    private boolean pagada;

    public static CuotaResponse desde(Cuota cuota) {
        return new CuotaResponse(
            cuota.getCredito().getId(),
            cuota.getNumero(),
            cuota.getFechaVencimiento(),
            cuota.estaPagada()
        );
    }
}
