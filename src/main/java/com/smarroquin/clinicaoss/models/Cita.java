package com.smarroquin.clinicaoss.models;

import com.smarroquin.clinicaoss.enums.estado_cita;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

@Entity
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Column
    private Date fechaApertura;

    @Enumerated(EnumType.STRING)
    private estado_cita estado_cita;

    @Column
    private String observacionesCita;

    @NotNull
    @ManyToOne(optional = false)
    private Paciente paciente;

    @NotNull
    @ManyToOne(optional = false)
    private Usuario user;

    @NotNull
    @ManyToOne(optional = false)
    private Tratamiento tratamiento;

    @PrePersist
    protected void onCreate() {
        if (this.fechaApertura == null) {
            this.fechaApertura = new Date();
        }
        if (this.codigo == null || this.codigo.isBlank()) {
            this.codigo = generarCodigo();
        }
    }

    private String generarCodigo() {
        return "COSS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
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

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Cita{");
        sb.append("id=").append(id);
        sb.append(", codigo='").append(codigo).append('\'');
        sb.append(", fechaApertura='").append(fechaApertura).append('\'');
        sb.append(", estado_cita='").append(estado_cita).append('\'');
        sb.append(", observacionesCita='").append(observacionesCita).append('\'');
        sb.append(", paciente='").append(paciente).append('\'');
        sb.append(", user='").append(user).append('\'');
        sb.append(", tratamiento='").append(tratamiento).append('\'');
        sb.append('}');

        return sb.toString();
    }
}
