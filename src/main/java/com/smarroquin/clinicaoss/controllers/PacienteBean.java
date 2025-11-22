package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.Paciente;
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
public class PacienteBean extends Bean<Paciente> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private transient CatalogService service;

    @Override
    protected Paciente createNew() {
        return new Paciente();
    }

    @Override
    protected List<Paciente> findAll() {
        return service.pacientes();
    }

    @Override
    protected void persist(Paciente entity) {
        service.guardar(entity);
    }

    @Override
    protected void remove(Paciente entity) {
        service.eliminar(entity);
    }

    @Override
    protected Map<String, String> fieldLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("nombreCompleto", "Nombre completo");
        labels.put("dpi", "Numero de DPI");
        labels.put("fechaNacimiento", "Fecha de nacimiento");
        labels.put("edad", "Edad del paciente");
        labels.put("telefono", "Telefono del paciente");
        labels.put("email", "Email del paciente");
        labels.put("alergias", "Alergias del paciente");
        labels.put("condicionesMedicas", "Condiciones medicas del paciente");
        labels.put("observaciones", "Observaciones");
        labels.put("fechaRegistro", "Fecha de registro");
        return labels;
    }

    @Override
    protected String getFacesClientId() {
        return "frmPacientes:msgPacientes";
    }

    @Override
    protected String successSaveMessage() {
        return "Paciente guardado";
    }

    @Override
    protected String successDeleteMessage() {
        return "Paciente eliminado";
    }

}
