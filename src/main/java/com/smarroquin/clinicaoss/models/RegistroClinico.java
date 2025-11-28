package com.smarroquin.clinicaoss.models;

import com.smarroquin.clinicaoss.enums.tipo_archivo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class RegistroClinico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String numeroRegistro;

    @NotNull(message = "Seleccionar un campo")
    @Enumerated(EnumType.STRING)
    private tipo_archivo tipo_archivo;

    @Pattern(regexp = "image/jpeg|image/png|application/pdf", message = "Tipo MIME no válido")
    @Column(length = 50)
    private String tipo_contenido;

    @NotNull(message = "Debe haber un URL asociado")
    @Column(length = 500)
    private String blob_url;

    @NotNull(message = "Debe haber un nombre asociado a la URL")
    @Column(length = 500)
    private String blobName;

    @NotNull(message = "Agregar fecha de Carga")
    private LocalDateTime fechaCarga;

    @NotNull(message = "Agregar nombre de paciente")
    @ManyToOne(optional = false)
    private Paciente paciente;

    @NotNull(message = "Agregar usuario donde role_name = Odontologo")
    @ManyToOne(optional = false)
    private User user;

    @NotNull(message = "Elegir fecha de cita")
    @ManyToOne(optional = false)
    private Cita cita;

    @PrePersist
    protected void onCreate() {
        if (this.fechaCarga == null) {
            this.fechaCarga = LocalDateTime.now();
        }
        if (this.numeroRegistro == null || this.numeroRegistro.isBlank()) {
            this.numeroRegistro = generarNumeroRegistro();
        }
    }

    public RegistroClinico() {
        this.numeroRegistro = generarNumeroRegistro();
    }

    private String generarNumeroRegistro() {
        return "REG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Getters y Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public tipo_archivo getTipo_archivo() {
        return tipo_archivo;
    }

    public void setTipo_archivo(tipo_archivo tipo_archivo) {
        this.tipo_archivo = tipo_archivo;
    }

    public String getTipo_contenido() {
        return tipo_contenido;
    }

    public void setTipo_contenido(String tipo_contenido) {
        this.tipo_contenido = tipo_contenido;
    }

    public String getBlob_url() {
        return blob_url;
    }

    public void setBlob_url(String blob_url) {
        this.blob_url = blob_url;
    }

    public String getBlobName() {
        return blobName;
    }

    public void setBlobName(String blobName) {
        this.blobName = blobName;
    }

    public LocalDateTime getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(LocalDateTime fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RegistroClinico{");
        sb.append("id=").append(id);
        sb.append(", numeroRegistro='").append(numeroRegistro).append('\'');
        sb.append(", tipo_archivo='").append(tipo_archivo).append('\'');
        sb.append(", tipo_contenido='").append(tipo_contenido).append('\'');
        sb.append(", blob_url='").append(blob_url).append('\'');
        sb.append(", blobName='").append(blobName).append('\'');
        sb.append(", fechaCarga='").append(fechaCarga).append('\'');
        sb.append(", paciente='").append(paciente).append('\'');
        sb.append(", user='").append(user).append('\'');
        sb.append(", cita='").append(cita).append('\'');
        sb.append('}');

        return sb.toString();
    }
}
