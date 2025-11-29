package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.Facturacion;
import com.smarroquin.clinicaoss.models.Tratamiento;
import com.smarroquin.clinicaoss.models.Paciente;
import com.smarroquin.clinicaoss.models.Cita;
import com.smarroquin.clinicaoss.models.Descuento;
import com.smarroquin.clinicaoss.models.Seguro;
import com.smarroquin.clinicaoss.enums.estado_pago;
import com.smarroquin.clinicaoss.service.CatalogService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class FacturacionBean extends Bean<Facturacion> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private transient CatalogService service;

    @Override
    protected Facturacion createNew() {
        Facturacion f = new Facturacion();
        f.setFechaEmision(LocalDateTime.now()); // Fecha actual por defecto
        f.setEstado_pago(estado_pago.PENDIENTE); // Pendiente por defecto
        f.setSubtotal(BigDecimal.ZERO);
        f.setTotal(BigDecimal.ZERO);
        return f;
    }

    @Override
    protected List<Facturacion> findAll() {
        return service.facturaciones();
    }

    @Override
    protected void persist(Facturacion entity) {

        if (entity.getFechaEmision() == null) {
            entity.setFechaEmision(LocalDateTime.now());
        }
        if (entity.getSubtotal() == null) entity.setSubtotal(BigDecimal.ZERO);
        if (entity.getTotal() == null) entity.setTotal(BigDecimal.ZERO);
        if (entity.getEstado_pago() == null) {
            if (entity.getTotal().compareTo(BigDecimal.ZERO) > 0) {
                entity.setEstado_pago(estado_pago.PENDIENTE);
            }
            else {
                entity.setEstado_pago(estado_pago.PAGADO);
            }
        }
        service.guardarFacturacion(entity);
    }

    @Override
    protected void remove(Facturacion entity) {
        // En facturación, NUNCA se elimina físicamente, se ANULA.
        entity.setEstado_pago(estado_pago.ANULADO);
        service.guardarFacturacion(entity);
    }

    @Override
    protected Map<String, String> fieldLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("fechaEmision", "Fecha de emisión");
        labels.put("subtotal", "Subtotal");
        labels.put("total", "Total");
        labels.put("estado_pago", "Estado de pago");
        labels.put("tratamiento", "Tratamiento");
        labels.put("paciente", "Paciente");
        labels.put("cita", "Cita");
        labels.put("descuento", "Descuento");
        labels.put("seguro", "Seguro");
        return labels;
    }

    @Override
    protected String getFacesClientId() {
        return "frmFacturacion:msgFacturacion";
    }

    @Override
    protected String successSaveMessage() {
        return "Factura guardada";
    }

    @Override
    protected String successDeleteMessage() {
        return "Factura eliminada";
    }

    // Métodos auxiliares para combos
    public List<Tratamiento> getTratamientos() {
        return service.tratamientos();
    }

    public List<Paciente> getPacientes() {
        return service.pacientes();
    }

    public List<Cita> getCitas() {
        return service.citas();
    }

    public List<Descuento> getDescuentos() {
        return service.descuentos();
    }

    public List<Seguro> getSeguros() {
        return service.seguros();
    }

    public estado_pago[] getEstadosPago() {
        return estado_pago.values();
    }
}
