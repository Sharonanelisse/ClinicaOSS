package com.smarroquin.clinicaoss.repositories;

import com.smarroquin.clinicaoss.models.Seguro;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SeguroRepository extends BaseRepository<Seguro, Long>{
    @Override
    protected Class<Seguro> entity() {
        return Seguro.class;
    }
}
