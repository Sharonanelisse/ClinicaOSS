package com.smarroquin.clinicaoss.repositories;

import com.smarroquin.clinicaoss.models.Cita;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CitaRepository extends BaseRepository<Cita, Long> {

    @Override
    protected Class<Cita> entity() {
        return Cita.class;
    }
}
