package com.uade.tpejemplo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cuotas", uniqueConstraints = @UniqueConstraint(columnNames = {"id_credito", "numero"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cuota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_credito", nullable = false)
    private Credito credito;

    @Min(1)
    @Column(name = "numero", nullable = false)
    private Integer numero;

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
    Cuota(Credito credito, Integer numero, LocalDate fechaVencimiento) {
        this.credito = credito;
        this.numero = numero;
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
