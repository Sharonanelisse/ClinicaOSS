package com.smarroquin.clinicaoss.repositories;

import com.smarroquin.clinicaoss.models.Descuento;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DescuentoRepository extends BaseRepository<Descuento, Long>{
    @Override
    protected Class<Descuento> entity() {
        return Descuento.class;
    }
}
