package com.smarroquin.clinicaoss.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Agregar el nombre del tratamiento")
    private String nombreTratamiento;

    @Column
    private String descripcion;

    @Column(nullable = false)
    @DecimalMin(value = "0.5", message = "La duracion estimada debe ser al menos 0.5")
    private Double duracionEstimado;

    @Column
    private BigDecimal costo;

    @NotNull
    @ManyToOne(optional = false)
    private Especialidad especialidad;

    @Column(nullable = false)
    private Boolean activoTratamiento = true;

    @PrePersist
    protected void onCreate() {
        if (activoTratamiento == null) activoTratamiento = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tratamiento that = (Tratamiento) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // Getters y setters

    public Boolean getActivoTratamiento() { return activoTratamiento; }
    public void setActivoTratamiento(Boolean activoTratamiento) { this.activoTratamiento = activoTratamiento; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreTratamiento() {
        return nombreTratamiento;
    }

    public void setNombreTratamiento(String nombreTratamiento) {
        this.nombreTratamiento = nombreTratamiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getDuracionEstimado() {
        return duracionEstimado;
    }

    public void setDuracionEstimado(Double duracionEstimado) {
        this.duracionEstimado = duracionEstimado;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Tratamiento{");
        sb.append("id=").append(id);
        sb.append(", nombreTratamiento='").append(nombreTratamiento).append('\'');
        sb.append(", descripcion='").append(descripcion).append('\'');
        sb.append(", duracionEstimado='").append(duracionEstimado).append('\'');
        sb.append(", costo='").append(costo).append('\'');
        sb.append(", especialidad='").append(especialidad).append('\'');
        sb.append('}');

        return sb.toString();
    }

}
