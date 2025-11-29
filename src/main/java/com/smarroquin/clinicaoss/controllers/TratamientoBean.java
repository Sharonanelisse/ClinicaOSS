package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.Especialidad;
import com.smarroquin.clinicaoss.models.Tratamiento;
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
        service.guardarTratamiento(entity);
    }

    @Override
    public void delete(Tratamiento entity) {
        try {
            entity.setActivoTratamiento(false);
            service.guardarTratamiento(entity); // Guardamos el cambio de estado

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Tratamiento desactivado", "Ya no podrá seleccionarse para nuevas citas."));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo desactivar."));
        }
    }

    // Método para reactivar si fue un error
    public void activar(Tratamiento entity) {
        entity.setActivoTratamiento(true);
        service.guardarTratamiento(entity);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Tratamiento reactivado", null));
    }

    // --- LISTA PARA EL SELECTONEMENU ---
    public List<Especialidad> getEspecialidades() {
        return service.especialidades();
    }

    @Override
    protected void remove(Tratamiento entity) {
        service.eliminarTratamiento(entity);
    }

    @Override
    protected Map<String, String> fieldLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("nombreTratamiento", "Nombre del tratamiento");
        labels.put("descripcion", "Descripción del tratamiento");
        labels.put("duracionEstimado", "Duración estimada");
        labels.put("costo", "Costo");
        labels.put("especialidad", "Especialidad");
        return labels;
    }

    @Override
    protected String getFacesClientId() {
        return "frmTratamientos:msgTratamiento";
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
