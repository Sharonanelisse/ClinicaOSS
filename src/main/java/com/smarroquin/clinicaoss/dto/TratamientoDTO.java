package com.smarroquin.clinicaoss.dto;

import com.smarroquin.clinicaoss.models.Especialidad;

import java.math.BigDecimal;

public class TratamientoDTO {
    private Long tratamientoId;
    private String nombreTratamiento;
    private String descripcion;
    private Double duracionEstimado;
    private BigDecimal costo;
    private Long especialidadId;
    private Boolean activoTratamiento;

    public Boolean getActivoTratamiento() { return activoTratamiento; }
    public void setActivoTratamiento(Boolean activoTratamiento) { this.activoTratamiento = activoTratamiento; }

    public Long getTratamientoId() {
        return tratamientoId;
    }

    public void setTratamientoId(Long tratamientoId) {
        this.tratamientoId = tratamientoId;
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

    public Long getEspecialidadId() {
        return especialidadId;
    }

    public void setEspecialidadId(Long especialidadId) {
        this.especialidadId = especialidadId;
    }
}
