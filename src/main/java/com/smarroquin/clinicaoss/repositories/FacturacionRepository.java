package com.smarroquin.clinicaoss.repositories;

import com.smarroquin.clinicaoss.models.Facturacion;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FacturacionRepository extends BaseRepository<Facturacion, Long>{
    @Override
    protected Class<Facturacion> entity() {
        return Facturacion.class;
    }
}
