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
import org.primefaces.PrimeFaces;
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
    private CatalogService service;

    private ScheduleModel schedule;

    private ScheduleEvent<?> event;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        loadEvents();
    }


    public void loadEvents() {
        schedule = new DefaultScheduleModel();
        List<Cita> listaCitas = findAll();

        if (listaCitas != null && !listaCitas.isEmpty()) {
            for (Cita c : listaCitas) {
                // Validación básica de seguridad
                if (c.getFechaApertura() == null) continue;

                LocalDateTime start = c.getFechaApertura();

                LocalDateTime end = start.plusHours(1);

                // Lógica de colores CSS según el Enum
                String cssClass = "cita-pendiente"; // Default amarillo
                if (c.getEstado_cita() != null) {
                    switch (c.getEstado_cita()) {
                        case PENDIENTE:    cssClass = "cita-pendiente"; break;
                        case CONFIRMADA:   cssClass = "cita-confirmada"; break;
                        case ATENDIDA:     cssClass = "cita-atendida"; break;
                        case CANCELADA:    cssClass = "cita-cancelada"; break;
                        case REPROGRAMDA:  cssClass = "cita-reprogramada"; break;
                    }
                }

                String titulo = (c.getPaciente() != null ? c.getPaciente().getNombrePaciente() + " " + c.getPaciente().getApellidoPaciente() : "Sin Paciente")
                        + " - " + (c.getTratamiento() != null ? c.getTratamiento().getNombreTratamiento() : "Consulta");

                // Crear el evento gráfico
                DefaultScheduleEvent<?> ev = DefaultScheduleEvent.builder()
                        .title(titulo)
                        .startDate(start)
                        .endDate(end)
                        .description(c.getObservacionesCita())
                        .data(c)
                        .borderColor("transparent")
                        .styleClass(cssClass)
                        .build();

                schedule.addEvent(ev);
            }
        }
    }

    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        this.newEntity(); // Llama al método del padre para limpiar

        // Asignar la fecha donde se dio clic
        this.selected.setFechaApertura(selectEvent.getObject());

        // Valores por defecto
        this.selected.setEstado_cita(estado_cita.PENDIENTE);

        // Mostrar el diálogo
        PrimeFaces.current().executeScript("PF('wdialogo').show();");
    }

    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        this.event = selectEvent.getObject();

        // Recuperamos el objeto Cita real que guardamos en .data(c)
        this.selected = (Cita) event.getData();

        this.dialogVisible = true;
        // Mostrar el diálogo
        PrimeFaces.current().executeScript("PF('wdialogo').show();");
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
        service.guardarCita(entity);
    }

    @Override
    protected void remove(Cita entity) {
        service.eliminarCita(entity);
    }


    @Override
    public void save() {
        super.save();
        loadEvents();

        PrimeFaces.current().executeScript("PF('wdialogo').hide();");
    }

    @Override
    public void delete(Cita entity) {
        super.delete(entity);
        loadEvents();
        PrimeFaces.current().executeScript("PF('wdialogo').hide();");
    }


    @Override
    protected String getFacesClientId() {
        return "frmCitas:msgCitas";
    }

    @Override
    protected String successSaveMessage() {
        return "Cita agendada correctamente";
    }

    @Override
    protected String successDeleteMessage() {
        return "Cita eliminada/cancelada";
    }

    @Override
    protected Map<String, String> fieldLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("paciente", "Paciente");
        labels.put("user", "Usuario / Doctor");
        labels.put("tratamiento", "Tratamiento");
        labels.put("fechaApertura", "Fecha de la cita");
        labels.put("estado_cita", "Estado");
        labels.put("observacionesCita", "Observaciones");
        return labels;
    }

    public List<Paciente> getPacientes() {
        return service.pacientes();
    }

    public List<Usuario> getUsuarios() {
        return service.users();
    }

    public List<Tratamiento> getTratamientos() {
        return service.tratamientos();
    }

    public estado_cita[] getEstadosCita() {
        return estado_cita.values();
    }

    public ScheduleModel getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleModel schedule) {
        this.schedule = schedule;
    }

}