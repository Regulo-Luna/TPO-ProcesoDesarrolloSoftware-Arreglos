package com.uade.tpejemplo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cobranzas")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cobranza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cuota", nullable = false)
    private Cuota cuota;

    @NotNull
    @Column(name = "importe", nullable = false, precision = 12, scale = 2)
    private BigDecimal importe;

    @NotNull
    @Column(name = "fecha_cobranza", nullable = false)
    private LocalDate fechaCobranza;

    @Column(name = "anulada", nullable = false)
    private boolean anulada = false;

    private Cobranza(Cuota cuota, BigDecimal importe) {
        this.cuota = cuota;
        this.importe = importe;
        this.fechaCobranza = LocalDate.now();
        this.anulada = false;
    }

    /**
     * Unica forma de registrar una cobranza. La fecha la pone la propia
     * cobranza (es el momento del cobro), no quien la registra.
     */
    public static Cobranza registrar(Cuota cuota, BigDecimal importe) {
        return new Cobranza(cuota, importe);
    }

    public void anular() {
        this.anulada = true;
    }
}
