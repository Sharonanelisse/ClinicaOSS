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
        return new Facturacion();
    }

    @Override
    protected List<Facturacion> findAll() {
        return service.facturaciones();
    }

    @Override
    protected void persist(Facturacion entity) {
        service.guardar(entity);
    }

    @Override
    protected void remove(Facturacion entity) {
        service.eliminar(entity);
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
