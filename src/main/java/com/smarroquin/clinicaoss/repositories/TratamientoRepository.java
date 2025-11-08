package com.smarroquin.clinicaoss.repositories;

import com.smarroquin.clinicaoss.models.Tratamiento;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TratamientoRepository extends BaseRepository<Tratamiento, Long> {

    @Override
    protected Class<Tratamiento> entity() {
        return Tratamiento.class;
    }
}
