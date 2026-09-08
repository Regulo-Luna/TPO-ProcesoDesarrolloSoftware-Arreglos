package com.uade.tpejemplo.dto.response;

import com.uade.tpejemplo.model.Credito;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
        return CreditoResponse.builder()
            .id(credito.getId())
            .dniCliente(credito.getCliente().getDni())
            .nombreCliente(credito.getCliente().getNombre())
            .deudaOriginal(credito.getDeudaOriginal())
            .fecha(credito.getFecha())
            .importeCuota(credito.getImporteCuota())
            .cantidadCuotas(credito.getCantidadCuotas())
            .cuotas(cuotas)
            .anulado(credito.isAnulado())
            .build();
    }
}
