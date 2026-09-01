package com.uade.tpejemplo.model;

import java.math.BigDecimal;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "meta_cobranza")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MetaCobranza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String mes;

    @NotNull
    private BigDecimal montoObjetivo;

    private MetaCobranza(String mes, BigDecimal montoObjetivo) {
        this.mes = mes;
        this.montoObjetivo = montoObjetivo;
    }

    public static MetaCobranza nueva(String mes, BigDecimal montoObjetivo) {
        return new MetaCobranza(mes, montoObjetivo);
    }

    public void actualizar(String mes, BigDecimal montoObjetivo) {
        this.mes = mes;
        this.montoObjetivo = montoObjetivo;
    }
}
