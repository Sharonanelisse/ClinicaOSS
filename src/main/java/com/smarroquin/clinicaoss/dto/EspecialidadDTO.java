package com.smarroquin.clinicaoss.dto;

public class EspecialidadDTO {
    private Long especialidadId;
    private String nombreEspecialidad;
    private String descripcion;

    public Long getEspecialidadId() {
        return especialidadId;
    }

    public void getEspecialidadId(Long especialidadId) {
        this.especialidadId = especialidadId;
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
}
