package com.smarroquin.clinicaoss.repositories;

import com.smarroquin.clinicaoss.models.Facturacion;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    // Sumar ingresos totales
    public BigDecimal sumarIngresos(LocalDateTime inicio, LocalDateTime fin) {
        BigDecimal total = getEm().createQuery("SELECT SUM(f.total) FROM Facturacion f WHERE f.fechaEmision BETWEEN :inicio AND :fin", BigDecimal.class)
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .getSingleResult();
        return total != null ? total : BigDecimal.ZERO;
    }

    // Contar tratamientos (facturas generadas)
    public Long contarTratamientos(LocalDateTime inicio, LocalDateTime fin) {
        return getEm().createQuery("SELECT COUNT(f) FROM Facturacion f WHERE f.fechaEmision BETWEEN :inicio AND :fin", Long.class)
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .getSingleResult();
    }

    // Para GRÁFICA: Tratamientos más vendidos (Pie Chart)
    public List<Object[]> tratamientosPopulares(LocalDateTime inicio, LocalDateTime fin) {
        return getEm().createQuery("SELECT f.tratamiento.nombreTratamiento, COUNT(f) FROM Facturacion f WHERE f.fechaEmision BETWEEN :inicio AND :fin GROUP BY f.tratamiento.nombreTratamiento", Object[].class)
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .getResultList();
    }

    // Para GRÁFICA: Ingresos por día (Bar Chart)
// Traemos todas las facturas del mes y las agrupamos en Java para evitar lios con SQL de fechas
    public List<Facturacion> facturasDelMes(LocalDateTime inicio, LocalDateTime fin) {
        return getEm().createQuery("SELECT f FROM Facturacion f WHERE f.fechaEmision BETWEEN :inicio AND :fin ORDER BY f.fechaEmision", Facturacion.class)
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .getResultList();
    }
}
