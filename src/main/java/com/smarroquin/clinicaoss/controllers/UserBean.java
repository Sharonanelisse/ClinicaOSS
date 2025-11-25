package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.User;
import com.smarroquin.clinicaoss.service.CatalogService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class UserBean extends Bean<User> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private transient CatalogService service;

    @Override
    protected User createNew() {
        return new User();
    }

    @Override
    protected List<User> findAll() {
        return service.users();
    }

    @Override
    protected void persist(User entity) {
        service.guardar(entity);
    }

    @Override
    protected void remove(User entity) {
        service.eliminar(entity);
    }

    @Override
    protected Map<String, String> fieldLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("nombreCompleto", "Nombre completo");
        labels.put("email", "Correo electrónico");
        labels.put("telefono", "Teléfono");
        labels.put("password", "Contraseña");
        labels.put("status", "Estado");
        labels.put("role", "Rol");
        return labels;
    }

    @Override
    protected String getFacesClientId() {
        return "frmUsers:msgUser";
    }

    @Override
    protected String successSaveMessage() {
        return "Usuario guardado";
    }

    @Override
    protected String successDeleteMessage() {
        return "Usuario eliminado";
    }

    public List<Role> getRoles() {
        return service.roles();
    }
}
