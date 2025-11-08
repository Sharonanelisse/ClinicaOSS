package com.smarroquin.clinicaoss.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "citas")
public class Cita implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paciente;

    private LocalDate fecha;

    private LocalTime hora; // 👈 Agregamos este campo

    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "tratamiento_id")
    private Tratamiento tratamiento;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPaciente() { return paciente; }
    public void setPaciente(String paciente) { this.paciente = paciente; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }  // 👈 Getter
    public void setHora(LocalTime hora) { this.hora = hora; } // 👈 Setter

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Tratamiento getTratamiento() { return tratamiento; }
    public void setTratamiento(Tratamiento tratamiento) { this.tratamiento = tratamiento; }
}
