package com.smarroquin.clinicaoss.dto;

import java.math.BigDecimal;
import java.util.Date;

public class SeguroDTO {
    private Long seguroId;
    private String codigoAseguradora;
    private String nombreAseguradora;
    private Double porcentajeDescuento;
    private Date fechaInicio;
    private BigDecimal deducible;
    private Date fechaFinal;
    private Boolean estado;

    public Long getSeguroId() {
        return seguroId;
    }

    public void setSeguroId(Long seguroId) {
        this.seguroId = seguroId;
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
}
