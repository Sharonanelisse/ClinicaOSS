package com.smarroquin.clinicaoss.dto;

import com.smarroquin.clinicaoss.enums.estado_cita;

import java.time.LocalDateTime;

public class CitaDTO {
    private Long citaId;
    private String codigo;
    private LocalDateTime fechaApertura;
    private estado_cita estado_cita;
    private String observacionesCita;
    private Long pacienteId;
    private Long userId;
    private Long tratamientoId;

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDateTime fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public estado_cita getEstado_cita() {
        return estado_cita;
    }

    public void setEstado_cita(estado_cita estado_cita) {
        this.estado_cita = estado_cita;
    }

    public String getObservacionesCita() {
        return observacionesCita;
    }

    public void setObservacionesCita(String observacionesCita) {
        this.observacionesCita = observacionesCita;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTratamientoId() {
        return tratamientoId;
    }

    public void setTratamientoId(Long tratamientoId) {
        this.tratamientoId = tratamientoId;
    }
}
