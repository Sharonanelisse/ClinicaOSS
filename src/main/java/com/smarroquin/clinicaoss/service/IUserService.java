package com.smarroquin.clinicaoss.service;

import com.smarroquin.clinicaoss.models.Usuario;
import com.smarroquin.clinicaoss.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<Usuario> list(int page, int size);

    int totalPages(int size);

    Optional<Usuario> getById(int id);

    Optional<Usuario> getByUsername(String username);

    void save(Usuario user);      // create or update

    void delete(int id, int currentUserId); // valida no autoeliminarse

    boolean validateLogin(String username, String password);
}

