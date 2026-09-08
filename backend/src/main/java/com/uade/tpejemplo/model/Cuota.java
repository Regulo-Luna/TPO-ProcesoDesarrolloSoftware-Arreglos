package com.uade.tpejemplo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    /** Sin getter: la cuota contesta estaPagada(), no entrega la lista. */
    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "cuota", fetch = FetchType.LAZY)
    private List<Cobranza> cobranzas = new ArrayList<>();

    /**
     * Visible solo dentro del paquete model: una cuota no se crea suelta,
     * la crea el credito al generar su plan.
     */
    Cuota(CuotaId id, Credito credito, LocalDate fechaVencimiento) {
        this.id = id;
        this.credito = credito;
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * Una cuota esta pagada cuando tiene una cobranza vigente. Las
     * anuladas no cuentan: anular una cobranza es deshacer el pago.
     */
    public boolean estaPagada() {
        return cobranzas.stream().anyMatch(cobranza -> !cobranza.isAnulada());
    }
}
