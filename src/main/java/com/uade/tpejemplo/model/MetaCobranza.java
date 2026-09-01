package com.uade.tpejemplo.model;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "meta_cobranza")
@NoArgsConstructor
@AllArgsConstructor
public class MetaCobranza {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String mes;
    @NotNull
    private BigDecimal montoObjetivo;
}