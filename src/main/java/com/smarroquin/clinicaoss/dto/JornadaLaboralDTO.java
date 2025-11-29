package com.smarroquin.clinicaoss.dto;

import com.smarroquin.clinicaoss.enums.dia_semana;
import com.smarroquin.clinicaoss.models.Usuario;

import java.time.LocalTime;

public class JornadaLaboralDTO {
    private Long jornadaLaboralId;
    private dia_semana dia_semana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Long userId;

    public Long getJornadaLaboralId() {
        return jornadaLaboralId;
    }

    public void setJornadaLaboralId(Long jornadaLaboralId) {
        this.jornadaLaboralId = jornadaLaboralId;
    }

    public dia_semana getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(dia_semana dia_semana) {
        this.dia_semana = dia_semana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
