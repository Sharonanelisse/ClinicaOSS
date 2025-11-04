package com.smarroquin.clinicaoss.service;

import com.smarroquin.clinicaoss.models.RegistroClinico;
import com.smarroquin.clinicaoss.repositories.RegistroClinicoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class RegistroClinicoService {

    @Inject
    private RegistroClinicoRepository repo;

    public List<RegistroClinico> registros() {
        return repo.findAll();
    }

    public RegistroClinico guardar(RegistroClinico r) {
        return repo.guardar(r);
    }

    public void eliminar(RegistroClinico r) {
        repo.eliminar(r);
    }

    public RegistroClinico findById(Long id) {
        return repo.find(id);
    }
}

