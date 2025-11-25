package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.Seguro;
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
public class SeguroBean extends Bean<Seguro> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private transient CatalogService service;

    @Override
    protected Seguro createNew() {
        return new Seguro();
    }

    @Override
    protected List<Seguro> findAll() {
        return service.seguros();
    }

    @Override
    protected void persist(Seguro entity) {
        service.guardar(entity);
    }

    @Override
    protected void remove(Seguro entity) {
        service.eliminar(entity);
    }

    @Override
    protected Map<String, String> fieldLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("codigoAseguradora", "Código de la aseguradora");
        labels.put("nombreAseguradora", "Nombre de la aseguradora");
        labels.put("porcentajeDescuento", "Porcentaje de descuento");
        labels.put("fechaInicio", "Fecha de inicio");
        labels.put("deducible", "Deducible");
        labels.put("fechaFinal", "Fecha final");
        labels.put("estado", "Estado");
        return labels;
    }

    @Override
    protected String getFacesClientId() {
        return "frmSeguros:msgSeguro";
    }

    @Override
    protected String successSaveMessage() {
        return "Seguro guardado";
    }

    @Override
    protected String successDeleteMessage() {
        return "Seguro eliminado";
    }
}
