package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.Cita;
import com.smarroquin.clinicaoss.models.Paciente;
import com.smarroquin.clinicaoss.models.Usuario;
import com.smarroquin.clinicaoss.models.Tratamiento;
import com.smarroquin.clinicaoss.enums.estado_cita;
import com.smarroquin.clinicaoss.service.CatalogService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class CitaBean extends Bean<Cita> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private transient CatalogService service;

    @Override
    protected Cita createNew() {
        return new Cita();
    }

    @Override
    protected List<Cita> findAll() {
        return service.citas();
    }

    @Override
    protected void persist(Cita entity) {
        service.guardar(entity);
    }

    @Override
    protected void remove(Cita entity) {
        service.eliminar(entity);
    }

    @Override
    protected Map<String, String> fieldLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("codigo", "Código de cita");
        labels.put("fechaApertura", "Fecha de apertura");
        labels.put("estado_cita", "Estado de la cita");
        labels.put("observacionesCita", "Observaciones");
        labels.put("paciente", "Paciente");
        labels.put("user", "Usuario");
        labels.put("tratamiento", "Tratamiento");
        return labels;
    }

    @Override
    protected String getFacesClientId() {
        return "frmCitas:msgCitas";
    }

    @Override
    protected String successSaveMessage() {
        return "Cita guardada";
    }

    @Override
    protected String successDeleteMessage() {
        return "Cita eliminada";
    }

    // Métodos auxiliares para combos en la vista
    public List<Paciente> getPacientes() {
        return service.pacientes();
    }

    public List<Usuario> getUsuarios() {
        return service.users();
    }

    public List<Tratamiento> getTratamientos() {
        return service.tratamientos();
    }

    public estado_cita[] getEstadosCita() {
        return estado_cita.values();
    }
}

