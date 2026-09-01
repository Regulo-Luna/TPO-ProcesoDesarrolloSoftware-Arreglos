package com.uade.tpejemplo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "clientes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cliente {

    @Id
    @Column(name = "dni", length = 15)
    private String dni;

    @NotBlank
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /** Sin getter: la coleccion es interna y exponerla entregaria la lista mutable. */
    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Credito> creditos;

    private Cliente(String dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    }

    public static Cliente nuevo(String dni, String nombre) {
        return new Cliente(dni, nombre);
    }
}
