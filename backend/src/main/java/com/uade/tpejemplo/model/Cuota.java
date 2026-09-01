package com.uade.tpejemplo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "cuotas")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cuota {

    @EmbeddedId
    private CuotaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idCredito")
    @JoinColumn(name = "id_credito")
    private Credito credito;

    @NotNull
    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    /**
     * Visible solo dentro del paquete model: una cuota no se crea suelta,
     * la crea el credito al generar su plan.
     */
    Cuota(CuotaId id, Credito credito, LocalDate fechaVencimiento) {
        this.id = id;
        this.credito = credito;
        this.fechaVencimiento = fechaVencimiento;
    }
}
