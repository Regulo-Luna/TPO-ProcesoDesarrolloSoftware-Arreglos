package com.uade.tpejemplo.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponseDTO {
    private Long cantidadClientes;
    private Long cantidadCreditos;
    private Double montoTotalFinanciado;
    private Double montoTotalCobrado;
}