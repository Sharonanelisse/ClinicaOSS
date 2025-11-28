package com.smarroquin.clinicaoss.repositories;

import com.smarroquin.clinicaoss.models.Especialidad;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EspecialidadesRepository extends BaseRepository<Especialidad, Long>{
    @Override
    protected Class<Especialidad> entity() {
        return Especialidad.class;
    }
}
