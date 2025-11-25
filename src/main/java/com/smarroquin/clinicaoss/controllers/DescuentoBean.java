package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.Descuento;
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
public class DescuentoBean extends Bean<Descuento> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private transient CatalogService service;

    @Override
    protected Descuento createNew() {
        return new Descuento();
    }

    @Override
    protected List<Descuento> findAll() {
        return service.descuentos();
    }

    @Override
    protected void persist(Descuento entity) {
        service.guardar(entity);
    }

    @Override
    protected void remove(Descuento entity) {
        service.eliminar(entity);
    }

    @Override
    protected Map<String, String> fieldLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("nombrePromocion", "Nombre de la promoción");
        labels.put("descripcionPromocion", "Descripción de la promoción");
        labels.put("descuentoPromocion", "Porcentaje de descuento");
        labels.put("estado", "Estado");
        return labels;
    }

    @Override
    protected String getFacesClientId() {
        return "frmDescuentos:msgDescuento";
    }

    @Override
    protected String successSaveMessage() {
        return "Descuento guardado";
    }

    @Override
    protected String successDeleteMessage() {
        return "Descuento eliminado";
    }
}
