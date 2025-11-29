package com.smarroquin.clinicaoss.repositories;

import com.smarroquin.clinicaoss.models.Facturacion;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class FacturacionRepository extends BaseRepository<Facturacion, Long>{
    @Override
    protected Class<Facturacion> entity() {
        return Facturacion.class;
    }

    public List<Facturacion> findByPacienteId(Long pacienteId) {
        return getEm().createQuery("SELECT f FROM Facturacion f WHERE f.paciente.id = :pid ORDER BY f.fechaEmision DESC", Facturacion.class)
                .setParameter("pid", pacienteId)
                .getResultList();
    }
}
