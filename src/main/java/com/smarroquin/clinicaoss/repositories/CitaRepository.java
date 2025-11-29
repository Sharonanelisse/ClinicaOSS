package com.smarroquin.clinicaoss.repositories;

import com.smarroquin.clinicaoss.models.Cita;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
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

    // Contar citas en un rango
    public Long contarCitas(LocalDateTime inicio, LocalDateTime fin) {
        return getEm().createQuery("SELECT COUNT(c) FROM Cita c WHERE c.fechaApertura BETWEEN :inicio AND :fin", Long.class)
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .getSingleResult();
    }

    // Contar citas canceladas
    public Long contarCanceladas(LocalDateTime inicio, LocalDateTime fin) {
        return getEm().createQuery("SELECT COUNT(c) FROM Cita c WHERE c.estado_cita = 'CANCELADA' AND c.fechaApertura BETWEEN :inicio AND :fin", Long.class)
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .getSingleResult();
    }

    // Contar odontólogos distintos que han tenido citas
    public Long contarOdontologosActivos(LocalDateTime inicio, LocalDateTime fin) {
        return getEm().createQuery("SELECT COUNT(DISTINCT c.user) FROM Cita c WHERE c.fechaApertura BETWEEN :inicio AND :fin", Long.class)
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .getSingleResult();
    }

    // Para GRÁFICA: Citas por Doctor
// Retorna lista de arreglos: [NombreDoctor, CantidadCitas]
    public List<Object[]> contarCitasPorDoctor(LocalDateTime inicio, LocalDateTime fin) {
        return getEm().createQuery("SELECT c.user.nombreUsuario, COUNT(c) FROM Cita c WHERE c.fechaApertura BETWEEN :inicio AND :fin GROUP BY c.user.nombreUsuario", Object[].class)
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .getResultList();
    }
}
