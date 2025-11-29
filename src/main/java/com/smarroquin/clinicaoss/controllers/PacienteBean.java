package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.Cita;
import com.smarroquin.clinicaoss.models.Facturacion;
import com.smarroquin.clinicaoss.models.Paciente;
import com.smarroquin.clinicaoss.service.CatalogService;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Named
@ViewScoped
public class PacienteBean extends Bean<Paciente> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private CatalogService service;


    private List<Cita> historialCitas;
    private List<Facturacion> historialFacturas;


    private String criterioBusqueda;
    private String textoBusqueda;
    private List<Paciente> listaPacientes;

    @Override
    protected Paciente createNew() {
        return new Paciente();
    }


    @Override
    public List<Paciente> getList() {

        if (listaPacientes != null) {
            return listaPacientes;
        }
        return findAll();
    }

    @Override
    protected List<Paciente> findAll() {
        return service.pacientes();
    }

    @Override
    protected void persist(Paciente entity) {
        service.guardarPaciente(entity);
    }

    @Override
    protected void remove(Paciente entity) {
        try {
            entity.setActivo(false);
            service.guardarPaciente(entity);
            addInfoMessage("Paciente desactivado correctamente");
        } catch (Exception e) {
            addErrorMessage("No se pudo desactivar al paciente");
        }
    }

    public void activar(Paciente entity) {
        entity.setActivo(true);
        service.guardarPaciente(entity);
        addInfoMessage("Paciente reactivado correctamente");
    }

    public void buscar() {
        if (textoBusqueda == null || textoBusqueda.trim().isEmpty()) {
            limpiarBusqueda();
            return;
        }

        List<Paciente> todos = findAll();
        String txt = textoBusqueda.toLowerCase();

        this.listaPacientes = todos.stream().filter(p -> {
            if ("dpi".equals(criterioBusqueda)) {
                return p.getDpi() != null && p.getDpi().contains(txt);
            } else if ("expediente".equals(criterioBusqueda)) {
                return p.getNumeroExpediente() != null && p.getNumeroExpediente().toLowerCase().contains(txt);
            } else {
                String nombreCompleto = (p.getNombrePaciente() + " " + p.getApellidoPaciente()).toLowerCase();
                return nombreCompleto.contains(txt);
            }
        }).collect(Collectors.toList());
    }

    public void limpiarBusqueda() {
        this.textoBusqueda = "";
        this.criterioBusqueda = "";
        this.listaPacientes = null;

    }


    public void cargarExpediente(Paciente p) {
        System.out.println("--> INTENTANDO ABRIR EXPEDIENTE DE: " + p.getNombrePaciente());

        try {
            // 1. Asignar el paciente seleccionado
            this.selected = p;

            // 2. Cargar listas (con seguridad anti-nulos)
            this.historialCitas = service.historialCitas(p.getId());
            if (this.historialCitas == null) this.historialCitas = new ArrayList<>();

            this.historialFacturas = service.historialFacturas(p.getId());
            if (this.historialFacturas == null) this.historialFacturas = new ArrayList<>();

            // 3. Forzar apertura visual
            PrimeFaces.current().executeScript("PF('dlgExpediente').show();");

        } catch (Exception e) {
            e.printStackTrace();
            addErrorMessage("Error al cargar datos: " + e.getMessage());
        }
    }

    public String formatFechaHora(LocalDateTime fecha) {
        return fecha != null ? fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "";
    }

    public String formatFecha(LocalDate fecha) {
        return fecha != null ? fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
    }


    public List<Cita> getHistorialCitas() { return historialCitas; }
    public List<Facturacion> getHistorialFacturas() { return historialFacturas; }

    public String getCriterioBusqueda() { return criterioBusqueda; }
    public void setCriterioBusqueda(String criterioBusqueda) { this.criterioBusqueda = criterioBusqueda; }

    public String getTextoBusqueda() { return textoBusqueda; }
    public void setTextoBusqueda(String textoBusqueda) { this.textoBusqueda = textoBusqueda; }

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
        labels.put("observaciones", "Observaciones sobre el paciente");
        labels.put("fechaRegistro", "Fecha de registro");
        labels.put("numeroExpediente", "Numero de expediente");
        labels.put("fechaUltimaActualizacion", "Fecha de ultima actualizacion");
        labels.put("activo", "Estado del paciente en el sistema");
        return labels;
    }

    @Override
    protected String getFacesClientId() {
        return "frmPrincipal:msgGlobal";
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