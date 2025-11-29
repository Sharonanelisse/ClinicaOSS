package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.enums.role_name;
import com.smarroquin.clinicaoss.models.Usuario;
import com.smarroquin.clinicaoss.service.CatalogService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
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


    public role_name[] getRoles() {
        return role_name.values();
    }

    public void toggleStatus(Usuario u) {
        u.setStatus(!u.getStatus());
        service.guardarUsuario(u);

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Estado actualizado",
                        "El usuario ahora está " + (u.getStatus() ? "Activo" : "Inactivo"))
        );
    }


    @Override
    protected void persist(Usuario entity) {
        service.guardarUsuario(entity);
    }

    @Override
    protected void remove(Usuario entity) {
        service.eliminarUsuario(entity);
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
