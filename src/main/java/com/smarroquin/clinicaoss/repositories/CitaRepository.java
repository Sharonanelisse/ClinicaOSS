package com.smarroquin.clinicaoss.repositories;

import com.smarroquin.clinicaoss.models.Cita;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CitaRepository extends BaseRepository<Cita, Long> {

    @Override
    protected Class<Cita> entity() {
        return Cita.class;
    }

    public List<Cita> findByPacienteId(Long pacienteId) {
        return getEm().createQuery("SELECT c FROM Cita c WHERE c.paciente.id = :pid ORDER BY c.fechaApertura DESC", Cita.class)
                .setParameter("pid", pacienteId)
                .getResultList();
    }
}
