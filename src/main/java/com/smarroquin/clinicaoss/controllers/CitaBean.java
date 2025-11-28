package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.Cita;
import com.smarroquin.clinicaoss.models.Paciente;
import com.smarroquin.clinicaoss.models.Usuario;
import com.smarroquin.clinicaoss.models.Tratamiento;
import com.smarroquin.clinicaoss.enums.estado_cita;
import com.smarroquin.clinicaoss.service.CatalogService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class CitaBean extends Bean<Cita> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private transient CatalogService service;

    private ScheduleModel schedule;
    private ScheduleEvent<?> selectedEvent;
    private Cita cita;
    private boolean dialogVisible;
    private boolean detailVisible;

    @PostConstruct
    public void init() {
        this.cita = new Cita();  // ← AQUÍ
        loadEvents();
    }

    @Override
    protected Cita createNew() {
        return new Cita();
    }

    @Override
    protected List<Cita> findAll() {
        return service.citas();
    }

    @Override
    protected void persist(Cita entity) {
        service.guardar(entity);
    }

    @Override
    protected void remove(Cita entity) {
        service.eliminar(entity);
    }

    @Override
    protected Map<String, String> fieldLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("codigo", "Código de cita");
        labels.put("fechaApertura", "Fecha de apertura");
        labels.put("estado_cita", "Estado de la cita");
        labels.put("observacionesCita", "Observaciones");
        labels.put("paciente", "Paciente");
        labels.put("user", "Usuario");
        labels.put("tratamiento", "Tratamiento");
        return labels;
    }

    @Override
    protected String getFacesClientId() {
        return "frmCitas:msgCitas";
    }

    @Override
    protected String successSaveMessage() {
        return "Cita guardada";
    }

    @Override
    protected String successDeleteMessage() {
        return "Cita eliminada";
    }


    public void loadEvents() {
        schedule = new DefaultScheduleModel();

        for (Cita c : service.citas()) {

            LocalDateTime start = c.getFechaApertura();
            LocalDateTime end = c.getFechaApertura().plusHours(1);

            DefaultScheduleEvent<?> ev = DefaultScheduleEvent.builder()
                    .id(String.valueOf(c.getCodigo()))
                    .title(c.getPaciente().getNombrePaciente() + " - " + c.getTratamiento().getNombreTratamiento())
                    .startDate(start)
                    .endDate(end)
                    .data(c)
                    .editable(true)
                    .build();

            schedule.addEvent(ev);
        }
    }

    public void newEvent() {
        cita = new Cita();
        dialogVisible = true;
    }

    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        selectedEvent = selectEvent.getObject();
        cita = (Cita) selectedEvent.getData();
        detailVisible = true;
    }

    public void save() {
        service.guardar(cita);
        loadEvents();
        dialogVisible = false;
        detailVisible = false;
    }

    public void delete() {
        service.eliminar(cita);
        loadEvents();
        detailVisible = false;
    }

    /* ================================
                CONVERT Date
       ================================ */

    private LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /* ================================
              GETTERS / SETTERS
       ================================ */

    public ScheduleModel getSchedule() {
        return schedule;
    }

    public boolean isDialogVisible() {
        return dialogVisible;
    }

    public boolean isDetailVisible() {
        return detailVisible;
    }

    public Cita getCita() {
        return cita;
    }

    public Cita getSelected() {
        return cita;
    }

    public List<Paciente> getPacientes() { return service.pacientes(); }
    public List<Usuario> getUsuarios() { return service.users(); }
    public List<Tratamiento> getTratamientos() { return service.tratamientos(); }
    public estado_cita[] getEstadosCita() { return estado_cita.values(); }
}

