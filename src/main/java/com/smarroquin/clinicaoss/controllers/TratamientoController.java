package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.Tratamiento;
import com.smarroquin.clinicaoss.service.CatalogService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("tratamientoController")
@ViewScoped
public class TratamientoController implements Serializable {

    private static final long serialVersionUID = 1L;

    private Tratamiento tratamiento;
    private List<Tratamiento> tratamientos;

    @Inject
    private CatalogService catalogService;

    @PostConstruct
    public void init() {
        try {
            tratamiento = new Tratamiento();
            tratamientos = catalogService.tratamientos(); // 🔹 Carga todos los tratamientos
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(" Error al inicializar TratamientoController: " + e.getMessage());
        }
    }

    public void guardar() {
        try {
            catalogService.guardar(tratamiento);
            tratamientos = catalogService.tratamientos();
            tratamiento = new Tratamiento(); // limpia el formulario
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al guardar tratamiento: " + e.getMessage());
        }
    }

    public void eliminar(Tratamiento t) {
        try {
            catalogService.eliminar(t);
            tratamientos.remove(t);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al eliminar tratamiento: " + e.getMessage());
        }
    }

    // Getters y setters
    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }
}
