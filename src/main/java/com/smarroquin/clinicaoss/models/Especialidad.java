package com.smarroquin.clinicaoss.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Agregar el nombre de la Especialidad")
    private String nombreEspecialidad;

    @Column
    private String descripcion;

    // Getters y setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Especialidad{");
        sb.append("id=").append(id);
        sb.append(", nombreEspecialidad='").append(nombreEspecialidad).append('\'');
        sb.append(", descripcion='").append(descripcion).append('\'');
        sb.append('}');

        return sb.toString();
    }
}
