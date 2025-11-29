package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.Descuento;
import com.smarroquin.clinicaoss.service.CatalogService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
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
        Descuento d = new Descuento();
        d.setEstado(true); // Activo por defecto
        return d;
    }

    @Override
    protected List<Descuento> findAll() {
        return service.descuentos();
    }

    @Override
    protected void persist(Descuento entity) {
        service.guardarDescuento(entity);
    }

    @Override
    public void delete(Descuento entity) {
        try {
            entity.setEstado(false);
            service.guardarDescuento(entity);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Descuento desactivado", null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void activar(Descuento entity) {
        entity.setEstado(true);
        service.guardarDescuento(entity);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Descuento reactivado", null));
    }

    @Override
    protected void remove(Descuento entity) {
        service.eliminarDescuento(entity);
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
