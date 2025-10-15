package com.smarroquin.clinicaoss.controllers;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.io.Serializable;
import java.util.*;

/*
* El Controller<T> sirve como base estandarizada para todos los controladores JSF de una aplicación.
* Su propósito es reducir código repetido en las operaciones CRUD y centralizar:
* La creación, edición, guardado y eliminación de entidades.
* La validación automática con Jakarta Bean Validation (@NotNull, @Email, etc.).
* El manejo de mensajes en la interfaz (errores y confirmaciones).
* El control de diálogos modales (por ejemplo, en PrimeFaces).
* Cada controlador específico (como PacienteController, DoctorController, etc.) hereda de esta clase y solo implementa la lógica propia de su entidad: cómo crear, listar, guardar y eliminar.
@Named
@ViewScoped
public class PacienteController extends Controller<Paciente> {
    @Inject
    private PacienteService service;
    @Override
    protected Paciente createNew() {
        return new Paciente();
    }
    @Override
    protected List<Paciente> findAll() {
        return service.listarPacientes();
    }
    @Override
    protected void persist(Paciente entity) {
        service.guardar(entity);
    }
    @Override
    protected void remove(Paciente entity) {
        service.eliminar(entity);
    }
}
*
*
* */

@ViewScoped
public abstract class Controller<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    protected Validator validator;

    protected T selected;
    protected boolean dialogVisible;

    @PostConstruct
    public void init() {
        selected = createNew();
        dialogVisible = false;
    }

    public List<T> getList() {
        return findAll();
    }

    public void newEntity() {
        clearFacesMessages();
        selected = createNew();
        dialogVisible = true;
    }

    public void edit(T entity) {
        clearFacesMessages();
        this.selected = entity;
        dialogVisible = true;
    }

    public void save() {
        Set<ConstraintViolation<T>> violations = validator.validate(selected);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<T> v : violations) {
                String field = v.getPropertyPath().toString();
                String message = v.getMessage();
                String label = getFieldLabel(field);
                FacesContext.getCurrentInstance().addMessage(
                        getFacesClientId(),
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, label + ": " + message, null)
                );
            }
            FacesContext.getCurrentInstance().validationFailed();
            return;
        }

        persist(selected);
        dialogVisible = false;

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, successSaveMessage(), "Operación exitosa")
        );

        selected = createNew();
    }

    public void delete(T entity) {
        remove(entity);
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, successDeleteMessage(), null)
        );
    }

    protected void clearFacesMessages() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        if (ctx == null) return;
        for (Iterator<FacesMessage> it = ctx.getMessages(); it.hasNext();) {
            it.next();
            it.remove();
        }
    }

    protected abstract T createNew();

    protected abstract List<T> findAll();

    protected abstract void persist(T entity);

    protected abstract void remove(T entity);

    protected Map<String, String> fieldLabels() {
        return Collections.emptyMap();
    }

    protected String getFieldLabel(String fieldName) {
        return fieldLabels().getOrDefault(fieldName, fieldName);
    }

    protected String getFacesClientId() {
        return null;
    }

    protected String successSaveMessage() {
        return "Registro guardado";
    }

    protected String successDeleteMessage() {
        return "Registro eliminado";
    }

    public T getSelected() {
        return selected;
    }

    public void setSelected(T selected) {
        this.selected = selected;
    }

    public boolean isDialogVisible() {
        return dialogVisible;
    }

    public void setDialogVisible(boolean dialogVisible) {
        this.dialogVisible = dialogVisible;
    }
}