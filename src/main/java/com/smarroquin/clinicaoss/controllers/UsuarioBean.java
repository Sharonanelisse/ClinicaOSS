package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.enums.role_name;
import com.smarroquin.clinicaoss.models.Usuario;
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
public class UsuarioBean extends Bean<Usuario> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private transient CatalogService service;

    @Override
    protected Usuario createNew() {
        return new Usuario();
    }

    @Override
    protected List<Usuario> findAll() {
        return service.users();
    }

    public role_name[] getRoles() {
        return role_name.values();
    }

    @Override
    protected void persist(Usuario entity) {
        service.guardar(entity);
    }

    @Override
    protected void remove(Usuario entity) {
        service.eliminar(entity);
    }

    @Override
    protected Map<String, String> fieldLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("nombreUsuario", "Nombre del usuario");
        labels.put("apellidoUsuario", "Apellido del usuario");
        labels.put("role_name", "Rol asignado");
        labels.put("email", "Correo electrónico");
        labels.put("password", "Contraseña");
        labels.put("telefono", "Teléfono");
        labels.put("status", "Estado");
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

}
