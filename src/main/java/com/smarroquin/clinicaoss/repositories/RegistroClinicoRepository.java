package com.smarroquin.clinicaoss.repositories;

import com.smarroquin.clinicaoss.models.RegistroClinico;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RegistroClinicoRepository extends BaseRepository<RegistroClinico, Long> {
    @Override
    protected Class<RegistroClinico> entity() {
        return RegistroClinico.class;
    }
}
