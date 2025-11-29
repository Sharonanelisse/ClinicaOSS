package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.Especialidad;
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
public class EspecialidadBean extends Bean<Especialidad> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private transient CatalogService service;

    @Override
    protected Especialidad createNew() {
        return new Especialidad();
    }

    @Override
    protected List<Especialidad> findAll() {
        return service.especialidades();
    }

    @Override
    protected void persist(Especialidad entity) {
        service.guardarEspecialidad(entity);
    }

    @Override
    public void delete(Especialidad entity) {
        try {
            entity.setActivoEspecialidad(false);
            service.guardarEspecialidad(entity); // Guardar cambio de estado

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Especialidad desactivada", "No aparecerá en nuevos registros."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo desactivar."));
        }
    }

    // Método para reactivar
    public void activar(Especialidad entity) {
        entity.setActivoEspecialidad(true);
        service.guardarEspecialidad(entity);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Especialidad reactivada", null));
    }

    @Override
    protected void remove(Especialidad entity) {
        service.eliminarEspecialidad(entity);
    }

    @Override
    protected Map<String, String> fieldLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("nombreEspecialidad", "Nombre de la especialidad");
        labels.put("descripcion", "Descripción de la especialidad");
        return labels;
    }

    @Override
    protected String getFacesClientId() {
        return "frmEspecialidades:msgEspecialidad";
    }

    @Override
    protected String successSaveMessage() {
        return "Especialidad guardada";
    }

    @Override
    protected String successDeleteMessage() {
        return "Especialidad eliminada";
    }
}

