package com.smarroquin.clinicaoss.service;

import com.smarroquin.clinicaoss.models.Tratamiento;
import com.smarroquin.clinicaoss.repositories.TratamientoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class TratamientoService {

    @Inject
    private TratamientoRepository repository;

    public List<Tratamiento> findAll() {
        return repository.findAll();
    }

    public void guardar(Tratamiento tratamiento) {
        repository.guardar(tratamiento);
    }

    public void eliminar(Tratamiento tratamiento) {
        repository.eliminar(tratamiento);
    }
}
