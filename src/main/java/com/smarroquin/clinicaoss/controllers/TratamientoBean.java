package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.Tratamiento;
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
public class TratamientoBean extends Bean<Tratamiento> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private transient CatalogService service;

    @Override
    protected Tratamiento createNew() {
        return new Tratamiento();
    }

    @Override
    protected List<Tratamiento> findAll() {
        return service.tratamientos();
    }

    @Override
    protected void persist(Tratamiento entity) {
        service.guardar(entity);
    }

    @Override
    protected void remove(Tratamiento entity) {
        service.eliminar(entity);
    }

    @Override
    protected Map<String, String> fieldLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("nombreTratamiento", "Nombre del tratamiento");
        labels.put("descripcion", "descripcion del tratamiento");
        labels.put("duracionEstimado", "Duracion estimado del tratamiento");
        labels.put("costo", "Costo del tratamiento");
        labels.put("especialidad", "Especialidad del tratamiento");
        return labels;
    }

    @Override
    protected String getFacesClientId() {
        return "frmTratamiento:msgTratamiento";
    }

    @Override
    protected String successSaveMessage() {
        return "Tratamiento guardado";
    }

    @Override
    protected String successDeleteMessage() {
        return "Tratamiento eliminado";
    }

}
