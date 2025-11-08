package com.smarroquin.clinicaoss.service;

import com.smarroquin.clinicaoss.models.Cita;
import com.smarroquin.clinicaoss.repositories.CitaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class CitaService {

    @Inject
    private CitaRepository repository;

    public List<Cita> findAll() {
        return repository.findAll();
    }

    public void guardar(Cita cita) {
        repository.guardar(cita);
    }

    public void eliminar(Cita cita) {
        repository.eliminar(cita);
    }
}
