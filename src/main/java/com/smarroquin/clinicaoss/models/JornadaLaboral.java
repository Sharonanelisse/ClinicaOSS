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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_jornadalaboral")
    )
    private User user;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Paciente{");
        sb.append("id=").append(id);
        sb.append(", diaSemana='").append(diaSemana).append('\'');
        sb.append(", horaInicio='").append(horaInicio).append('\'');
        sb.append(", horaFin='").append(horaFin).append('\'');
        sb.append(", user='").append(user).append('\'');
        sb.append('}');

        return sb.toString();
    }

}
