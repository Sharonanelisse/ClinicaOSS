package com.smarroquin.clinicaoss.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

@Entity
public class JornadaLaboral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Elegir un dia de la semana")
    private String diaSemana;

    @Column
    @NotBlank(message = "Poner hora de inicio")
    private LocalTime horaInicio;

    @Column
    @NotBlank(message = "Poner hora de salida")
    private LocalTime horaFin;
}
