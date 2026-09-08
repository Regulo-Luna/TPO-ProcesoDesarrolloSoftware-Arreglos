package com.uade.tpejemplo.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "usuarios")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    private boolean puedeAnularCredito;
    private boolean puedeAnularCobranza;

    /**
     * Los permisos se cambian juntos y con nombre de negocio, en vez de
     * quedar expuestos como dos setters sueltos.
     */
    public void otorgarPermisos(boolean puedeAnularCredito, boolean puedeAnularCobranza) {
        this.puedeAnularCredito = puedeAnularCredito;
        this.puedeAnularCobranza = puedeAnularCobranza;
    }

    /**
     * El rol se cambia con nombre de negocio en vez de un setter suelto:
     * quien lo llama esta asignando un rol, no escribiendo un campo.
     */
    public void asignarRol(Rol rol) {
        this.rol = rol;
    }
}
