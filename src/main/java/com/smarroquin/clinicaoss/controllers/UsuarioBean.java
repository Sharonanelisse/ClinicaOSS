package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.JornadaLaboral;
import com.smarroquin.clinicaoss.models.Usuario;
import com.smarroquin.clinicaoss.enums.role_name;
import com.smarroquin.clinicaoss.enums.dia_semana;
import com.smarroquin.clinicaoss.service.CatalogService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class UsuarioBean extends Bean<Usuario> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private CatalogService service;

    // --- VARIABLES PARA JORNADA LABORAL ---
    private List<JornadaLaboral> jornadasUsuario;

    // CORRECCIÓN 1: Inicializar aquí para que NUNCA sea null al cargar la página
    private JornadaLaboral nuevaJornada = new JornadaLaboral();

    // ==========================================================
    // MÉTODOS DEL PADRE (BEAN<T>)
    // ==========================================================

    @Override
    protected Usuario createNew() {
        Usuario u = new Usuario();
        u.setStatus(true);

        // CORRECCIÓN 2: Reiniciar el objeto auxiliar al crear nuevo usuario
        this.nuevaJornada = new JornadaLaboral();
        this.jornadasUsuario = new ArrayList<>(); // Reiniciar lista también

        return u;
    }

    @Override
    protected List<Usuario> findAll() {
        return service.usuario();
    }

    @Override
    protected void persist(Usuario entity) {
        service.guardarUsuario(entity);
    }

    @Override
    protected void remove(Usuario entity) {
        entity.setStatus(false);
        service.guardarUsuario(entity);
    }

    @Override
    public void delete(Usuario entity) {
        try {
            remove(entity);
            addInfoMessage("Usuario desactivado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            addErrorMessage("Error al desactivar usuario");
        }
    }

    public void activar(Usuario entity) {
        try {
            entity.setStatus(true);
            service.guardarUsuario(entity);
            addInfoMessage("Usuario reactivado correctamente");
        } catch (Exception e) {
            addErrorMessage("Error al reactivar usuario");
        }
    }

    // ==========================================================
    // LÓGICA DE JORNADA LABORAL
    // ==========================================================

    public void cargarUsuario(Usuario u) {
        this.selected = u;

        // Cargar horarios. Si viene null del servicio, ponemos lista vacía.
        List<JornadaLaboral> encontradas = service.jornadasPorUsuario(u);
        this.jornadasUsuario = (encontradas != null) ? encontradas : new ArrayList<>();

        // Preparar nueva jornada
        this.nuevaJornada = new JornadaLaboral();
        this.nuevaJornada.setUser(u);

        PrimeFaces.current().executeScript("PF('wdialogo').show();");
    }

    public void agregarJornada() {
        if (selected == null || selected.getId() == null) {
            addErrorMessage("Error: Guarde el usuario antes de asignar horarios.");
            return;
        }

        try {
            nuevaJornada.setUser(selected);
            service.guardarJornada(nuevaJornada);

            // Refrescar lista
            this.jornadasUsuario = service.jornadasPorUsuario(selected);

            // Limpiar form
            this.nuevaJornada = new JornadaLaboral();
            this.nuevaJornada.setUser(selected); // Importante volver a setear el usuario

            addInfoMessage("Horario agregado exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            addErrorMessage("Error al guardar horario: " + e.getMessage());
        }
    }

    public void eliminarJornada(JornadaLaboral j) {
        try {
            service.eliminarJornada(j);
            this.jornadasUsuario.remove(j);
            addInfoMessage("Horario eliminado");
        } catch (Exception e) {
            addErrorMessage("No se pudo eliminar el horario");
        }
    }

    // ==========================================================
    // GETTERS
    // ==========================================================

    public role_name[] getRoles() {
        return role_name.values();
    }

    public dia_semana[] getDiasSemana() {
        return dia_semana.values();
    }

    public List<JornadaLaboral> getJornadasUsuario() {
        return jornadasUsuario;
    }

    public JornadaLaboral getNuevaJornada() {
        return nuevaJornada;
    }

    public void setNuevaJornada(JornadaLaboral nuevaJornada) {
        this.nuevaJornada = nuevaJornada;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

    // ==========================================================
    // CONFIGURACIÓN VISUAL
    // ==========================================================

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
        return "frmPrincipal:msgGlobal";
    }

    @Override
    protected String successSaveMessage() {
        return "Usuario guardado correctamente";
    }

    @Override
    protected String successDeleteMessage() {
        return "Usuario desactivado";
    }
}