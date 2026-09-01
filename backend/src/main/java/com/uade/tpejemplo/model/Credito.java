package com.uade.tpejemplo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "creditos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dni_cliente", nullable = false)
    private Cliente cliente;

    @NotNull
    @Column(name = "deuda_original", nullable = false, precision = 12, scale = 2)
    private BigDecimal deudaOriginal;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(name = "importe_cuota", nullable = false, precision = 12, scale = 2)
    private BigDecimal importeCuota;

    @Min(1)
    @Column(name = "cantidad_cuotas", nullable = false)
    private Integer cantidadCuotas;

    @OneToMany(mappedBy = "credito", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cuota> cuotas;

    @Column(name = "anulado", nullable = false)
    private boolean anulado = false;

    /**
     * Genera el plan de cuotas del credito: una cuota por cada periodo,
     * numeradas desde 1 y con vencimiento mensual a partir de la fecha
     * de otorgamiento.
     *
     * Es una regla del credito, no del caso de uso que lo da de alta:
     * por eso vive en la entidad y no en el servicio.
     */
    public List<Cuota> generarPlanDeCuotas() {
        List<Cuota> plan = new ArrayList<>();
        for (int numeroCuota = 1; numeroCuota <= cantidadCuotas; numeroCuota++) {
            plan.add(new Cuota(
                new CuotaId(id, numeroCuota),
                this,
                fecha.plusMonths(numeroCuota)
            ));
        }
        return plan;
    }
}
