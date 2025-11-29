package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.Paciente;
import com.smarroquin.clinicaoss.service.CatalogService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    protected List<Paciente> findAll() {return service.pacientes();}

    @Override
    protected void persist(Paciente entity) {service.guardarPaciente(entity);}

    @Override
    protected void remove(Paciente entity) {
        service.eliminarPaciente(entity);
    }

    public String formatFecha(LocalDate fecha) {
        return fecha != null ? fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
    }

    @Override
    public void delete(Paciente entity) {
        try {
            // En vez de borrar, lo desactivamos
            entity.setActivo(false);

            // Usamos el servicio de guardar (update)
            service.guardarPaciente(entity);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Paciente desactivado", "El registro ha pasar a estado inactivo."));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo cambiar el estado."));
        }
    }

    // Opcional: Un método para reactivarlo si te equivocaste
    public void activar(Paciente entity) {
        entity.setActivo(true);
        service.guardarPaciente(entity);
        addInfoMessage("Paciente reactivado exitosamente.");
    }

    @Override
    protected Map<String, String> fieldLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("nombrePaciente", "Nombre del paciente");
        labels.put("apellidoPaciente", "Apellido del paciente");
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
