package com.smarroquin.clinicaoss.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class RegistroClinico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String numeroRegistro;

    @NotBlank
    private String tipo; // imagen/pdf/radiografía

    private String ruta; // ruta del archivo

    @NotNull
    private LocalDateTime fechaCarga;

    @ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "odontologo_id")
    private User odontologo;

    @PrePersist
    protected void onCreate() {
        if (this.fechaCarga == null) {
            this.fechaCarga = LocalDateTime.now();
        }
        if (this.numeroRegistro == null || this.numeroRegistro.isBlank()) {
            this.numeroRegistro = generarNumeroRegistro();
        }
    }

    public RegistroClinico() {
        this.numeroRegistro = generarNumeroRegistro();
    }

    private String generarNumeroRegistro() {
        return "REG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroRegistro() { return numeroRegistro; }
    public void setNumeroRegistro(String numeroRegistro) { this.numeroRegistro = numeroRegistro; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getRuta() { return ruta; }
    public void setRuta(String ruta) { this.ruta = ruta; }

    public LocalDateTime getFechaCarga() { return fechaCarga; }
    public void setFechaCarga(LocalDateTime fechaCarga) { this.fechaCarga = fechaCarga; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public User getOdontologo() { return odontologo; }
    public void setOdontologo(User odontologo) { this.odontologo = odontologo; }

    @Override
    public String toString() {
        return "RegistroClinico{" +
                "id=" + id +
                ", numeroRegistro='" + numeroRegistro + '\'' +
                ", tipo='" + tipo + '\'' +
                ", ruta='" + ruta + '\'' +
                ", fechaCarga=" + fechaCarga +
                ", paciente=" + (paciente != null ? paciente.getNombreCompleto() : "null") +
                ", odontologo=" + (odontologo != null ? odontologo.getNombreCompleto() : "null") +
                '}';
    }
}
