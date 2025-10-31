package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.Paciente;
import com.smarroquin.clinicaoss.service.CatalogService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class PacienteController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private CatalogService catalogService;

    private Paciente paciente = new Paciente();
    private String filtroDPI = "";

    // Listar todos los pacientes
    public List<Paciente> getPacientes() {
        return catalogService.pacientes();
    }

    // Guardar nuevo paciente
    public void guardarPaciente() {
        paciente.setFechaRegistro(LocalDateTime.now()); // fecha automática
        catalogService.guardar(paciente);
        paciente = new Paciente(); // limpiar formulario
    }

    // Eliminar paciente
    public void eliminarPaciente(Paciente p) {
        catalogService.eliminar(p);
    }

    // Buscar por DPI
    // Buscar por DPI
    public List<Paciente> buscarPorDPI() {
        return catalogService.pacientes().stream()
                .filter(p -> p.getDpi() != null && p.getDpi().contains(filtroDPI))
                .toList();
    }


    // Getters y Setters
    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getFiltroDPI() {
        return filtroDPI;
    }

    public void setFiltroDPI(String filtroDPI) {
        this.filtroDPI = filtroDPI;
    }
}
