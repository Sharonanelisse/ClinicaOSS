package com.smarroquin.clinicaoss.repositories;

import com.smarroquin.clinicaoss.models.Usuario;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepostory extends BaseRepository<Usuario, Long> {
    @Override
    protected Class<Usuario> entity(){return Usuario.class;}
}
