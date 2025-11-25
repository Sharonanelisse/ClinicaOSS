package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.JornadaLaboral;
import com.smarroquin.clinicaoss.models.User;
import com.smarroquin.clinicaoss.enums.dia_semana;
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
public class JornadaLaboralBean extends Bean<JornadaLaboral> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private transient CatalogService service;

    private User userContext;

    @Override
    protected JornadaLaboral createNew() {
        JornadaLaboral j = new JornadaLaboral();
        j.setUser(userContext);
        return j;
    }

    @Override
    protected List<JornadaLaboral> findAll() {
        return service.jornadasPorUsuario(userContext);
    }

    @Override
    protected void persist(JornadaLaboral entity) {
        service.guardar(entity);
    }

    @Override
    protected void remove(JornadaLaboral entity) {
        service.eliminar(entity);
    }

    @Override
    protected Map<String, String> fieldLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("dia_semana", "Día de la semana");
        labels.put("horaInicio", "Hora de inicio");
        labels.put("horaFin", "Hora de salida");
        return labels;
    }

    @Override
    protected String getFacesClientId() {
        return "frmJornadas:msgJornadas";
    }

    @Override
    protected String successSaveMessage() {
        return "Jornada laboral guardada";
    }

    @Override
    protected String successDeleteMessage() {
        return "Jornada laboral eliminada";
    }

    // Setter para establecer el usuario en contexto desde UserBean
    public void setUserContext(User user) {
        this.userContext = user;
    }

    public User getUserContext() {
        return userContext;
    }

    public dia_semana[] getDiasSemana() {
        return dia_semana.values();
    }
}

