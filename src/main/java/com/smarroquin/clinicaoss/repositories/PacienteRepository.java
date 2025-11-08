package com.smarroquin.clinicaoss.repositories;

import com.smarroquin.clinicaoss.models.Paciente;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PacienteRepository extends BaseRepository<Paciente, Long> {
    @Override
    protected Class<Paciente> entity() {
        return Paciente.class;
    }
}
