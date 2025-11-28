package com.smarroquin.clinicaoss.repositories;

import com.smarroquin.clinicaoss.models.JornadaLaboral;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JornadaLaboralRepository extends BaseRepository<JornadaLaboral, Long>{
    @Override
    protected Class<JornadaLaboral> entity() {
        return JornadaLaboral.class;
    }
}
