package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.Cita;
import com.smarroquin.clinicaoss.models.Paciente;
import com.smarroquin.clinicaoss.models.Tratamiento;
import com.smarroquin.clinicaoss.models.User;
import com.smarroquin.clinicaoss.service.CitaService;
import com.smarroquin.clinicaoss.service.CatalogService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("citaController")
@ViewScoped
public class CitaController implements Serializable {

    private Cita cita = new Cita();
    private List<Cita> citas;
    private List<Paciente> pacientes;
    private List<User> odontologos;
    private List<Tratamiento> tratamientos;

    @Inject
    private CitaService citaService;

    @Inject
    private CatalogService catalogService;

    @PostConstruct
    public void init() {
        citas = citaService.findAll();
        pacientes = catalogService.pacientes();
        odontologos = catalogService.users().stream()
                .filter(u -> u.getRole() != null
                        && u.getRole().getNombre() != null
                        && u.getRole().getNombre().equalsIgnoreCase("odontologo"))
                .toList();
        tratamientos = catalogService.tratamientos();
    }

    public void guardar() {
        citaService.guardar(cita);
        citas = citaService.findAll();
        cita = new Cita();
    }

    public void eliminar(Cita c) {
        citaService.eliminar(c);
        citas.remove(c);
    }

    // Getters y setters
    public Cita getCita() { return cita; }
    public void setCita(Cita cita) { this.cita = cita; }

    public List<Cita> getCitas() { return citas; }
    public List<Paciente> getPacientes() { return pacientes; }
    public List<User> getOdontologos() { return odontologos; }
    public List<Tratamiento> getTratamientos() { return tratamientos; }
}
