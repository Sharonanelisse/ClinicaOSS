package com.smarroquin.clinicaoss.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
public class JornadaLaboral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String diaSemana;
    @Column
    private String mesSemana;
}
