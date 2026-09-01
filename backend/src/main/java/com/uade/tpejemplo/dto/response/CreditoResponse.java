package com.uade.tpejemplo.dto.response;

import com.uade.tpejemplo.model.Credito;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class CreditoResponse {

    private Long id;
    private String dniCliente;
    private String nombreCliente;
    private BigDecimal deudaOriginal;
    private LocalDate fecha;
    private BigDecimal importeCuota;
    private Integer cantidadCuotas;
    private List<CuotaResponse> cuotas;
    private boolean anulado;

    public static CreditoResponse desde(Credito credito, List<CuotaResponse> cuotas) {
        return new CreditoResponse(
            credito.getId(),
            credito.getCliente().getDni(),
            credito.getCliente().getNombre(),
            credito.getDeudaOriginal(),
            credito.getFecha(),
            credito.getImporteCuota(),
            credito.getCantidadCuotas(),
            cuotas,
            credito.isAnulado()
        );
    }
}
