package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.enums.role_name;
import com.smarroquin.clinicaoss.models.Usuario;
import com.smarroquin.clinicaoss.service.CatalogService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
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

    @PostConstruct
    public void initBean() {
        System.out.println("Usuarios encontrados en init: " + service.users().size());
    }


    @Override
    protected Usuario createNew() {
        return new Usuario();
    }

    @Override
    protected List<Usuario> findAll() {
        List<Usuario> usuarios = service.users();
        System.out.println("Usuarios encontrados: " + usuarios.size());
        return usuarios;
    }

    public String login() {
        try {
            List<Usuario> usuarios = service.users();
            String loginInput = selected.getEmail(); // aquí reutilizamos el campo "email" como entrada genérica
            String passwordInput = selected.getPassword();

            Usuario encontrado = usuarios.stream()
                    .filter(u ->
                            (u.getEmail() != null && u.getEmail().equalsIgnoreCase(loginInput))
                                    ||
                                    (u.getNombreUsuario() != null && u.getNombreUsuario().equalsIgnoreCase(loginInput))
                    )
                    .findFirst()
                    .orElse(null);

            if (encontrado == null) {
                addErrorMessage("Usuario o correo no encontrado");
                return null;
            }

            if (!encontrado.getPassword().equals(passwordInput)) {
                addErrorMessage("Contraseña incorrecta");
                return null;
            }

            if (!encontrado.isStatus()) {
                addErrorMessage("Usuario inactivo");
                return null;
            }

            // Guardar usuario autenticado en sesión
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap()
                    .put("usuarioLogueado", encontrado);

            return "/views/home.xhtml?faces-redirect=true";

        } catch (Exception e) {
            addErrorMessage("Error inesperado: " + e.getMessage());
            return null;
        }
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
