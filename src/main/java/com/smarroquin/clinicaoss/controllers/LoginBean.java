package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.Usuario;
import com.smarroquin.clinicaoss.service.CatalogService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class LoginBean implements Serializable {

    private String username; // puede ser email o nombreUsuario
    private String password;

    @Inject
    private CatalogService service;

    public String login() {
        List<Usuario> usuarios = service.users();

        Usuario u = usuarios.stream()
                .filter(x -> (x.getEmail().equals(username) ||
                        x.getNombreUsuario().equals(username)))
                .findFirst()
                .orElse(null);

        if (u == null) {
            addErrorMessage("Usuario no encontrado");
            return null;
        }

        if (!u.getPassword().equals(password)) {
            addErrorMessage("Contraseña incorrecta");
            return null;
        }

        if (!u.isStatus()) {
            addErrorMessage("Usuario desactivado");
            return null;
        }

        // Guardar en sesión
        FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap()
                .put("usuarioLogeado", u);

        return "/dashboard.xhtml?faces-redirect=true";
    }

    private void addErrorMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
    }

    // ===========================
    // GETTERS Y SETTERS
    // ===========================
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

