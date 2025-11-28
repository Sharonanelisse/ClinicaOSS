package com.smarroquin.clinicaoss.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Seguro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    @NotBlank(message = "Agregar codigo de la aseguradora")
    private String codigoAseguradora;

    @Column(nullable = false, unique = true, length = 50)
    @NotBlank(message = "Agregar nombre de la aseguradora")
    private String nombreAseguradora;

    @Column
    @NotBlank(message = "Agregar porcentaje de descuento")
    private Double porcentajeDescuento;

    @Column
    private Date fechaInicio;

    @Column
    private BigDecimal deducible;

    @Column
    private Date fechaFinal;

    @NotNull(message = "El estado es obligatorio.")
    @Column(nullable = false)
    private Boolean estado = true;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoAseguradora() {
        return codigoAseguradora;
    }

    public void setCodigoAseguradora(String codigoAseguradora) {
        this.codigoAseguradora = codigoAseguradora;
    }

    public String getNombreAseguradora() {
        return nombreAseguradora;
    }

    public void setNombreAseguradora(String nombreAseguradora) {
        this.nombreAseguradora = nombreAseguradora;
    }

    public Double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public BigDecimal getDeducible() {
        return deducible;
    }

    public void setDeducible(BigDecimal deducible) {
        this.deducible = deducible;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Seguro{");
        sb.append("id=").append(id);
        sb.append(", codigoAseguradora='").append(codigoAseguradora).append('\'');
        sb.append(", nombreAseguradora='").append(nombreAseguradora).append('\'');
        sb.append(", porcentajeDescuento='").append(porcentajeDescuento).append('\'');
        sb.append(", fechaInicio='").append(fechaInicio).append('\'');
        sb.append(", deducible='").append(deducible).append('\'');
        sb.append(", fechaFinal='").append(fechaFinal).append('\'');
        sb.append(", estado='").append(estado).append('\'');
        sb.append('}');

        return sb.toString();
    }
}
