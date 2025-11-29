package com.smarroquin.clinicaoss.models;

import com.smarroquin.clinicaoss.enums.dia_semana;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalTime;

@Entity
public class JornadaLaboral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Seleccionar el dia de la semana")
    @Enumerated(EnumType.STRING)
    private dia_semana dia_semana;

    @Column
    @NotBlank(message = "Poner hora de inicio")
    private LocalTime horaInicio;

    @Column
    @NotBlank(message = "Poner hora de salida")
    private LocalTime horaFin;

    @NotNull
    @ManyToOne(optional = false)
    private Usuario user;

    // Getters y Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JornadaLaboral{");
        sb.append("id=").append(id);
        sb.append(", dia_semana='").append(dia_semana).append('\'');
        sb.append(", horaInicio='").append(horaInicio).append('\'');
        sb.append(", horaFin='").append(horaFin).append('\'');
        sb.append(", user='").append(user).append('\'');
        sb.append('}');

        return sb.toString();
    }

}
