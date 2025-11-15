package com.smarroquin.clinicaoss.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 3, max = 60)
    @Column(length = 60, nullable = false)
    private String nombreCompleto;

    @NotBlank(message = "El DPI es obligatorio")
    @Column(length = 20, nullable = false, unique = true)
    private String dpi;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column
    private Integer edad;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\+?\\d{8,15}$", message = "Teléfono internacional 8-15 dígitos")
    private String telefono;

    @Email(message = "Debe ser un correo válido")
    @NotBlank(message = "Debes agregar un correo")
    @Column(length = 120)
    private String email;

    @Column(length = 255)
    private String alergias;

    @Column
    private String condicionesMedicas;

    @Column
    private String observaciones;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
        actualizarEdad();
    }

    @PreUpdate
    protected void preUpdate() {
        actualizarEdad();
    }

    private void actualizarEdad() {
        if (this.fechaNacimiento != null) {
            this.edad = Period.between(this.fechaNacimiento, LocalDate.now()).getYears();
        } else {
            this.edad = null;
        }
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getCondicionesMedicas() {
        return condicionesMedicas;
    }

    public void setCondicionesMedicas(String condicionesMedicas) {
        this.condicionesMedicas = condicionesMedicas;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
