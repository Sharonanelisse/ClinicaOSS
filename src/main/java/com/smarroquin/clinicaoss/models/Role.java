package com.smarroquin.clinicaoss.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(
        name = "role",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_role_nombre", columnNames = "nombre")
        }
)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del rol es obligatorio.")
    @Size(min = 3, max = 20, message = "El nombre del rol debe tener entre 3 y 20 caracteres.")
    @Column(name = "nombre", length = 20, nullable = false)
    private String nombre;

    @PrePersist
    public void prePersist() {
        if (nombre != null) {
            nombre = nombre.trim().toUpperCase();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
