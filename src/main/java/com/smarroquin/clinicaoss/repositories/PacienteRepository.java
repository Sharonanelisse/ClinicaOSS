package com.smarroquin.clinicaoss.repositories;

import com.smarroquin.clinicaoss.models.Paciente;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;

@ApplicationScoped
public class PacienteRepository extends BaseRepository<Paciente, Long> {
    @Override
    protected Class<Paciente> entity() {
        return Paciente.class;
    }

    // Contar pacientes registrados en un rango de fechas
    public Long contarNuevos(LocalDateTime inicio, LocalDateTime fin) {
        return getEm().createQuery("SELECT COUNT(p) FROM Paciente p WHERE p.fechaRegistro BETWEEN :inicio AND :fin", Long.class)
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .getSingleResult();
    }
}
