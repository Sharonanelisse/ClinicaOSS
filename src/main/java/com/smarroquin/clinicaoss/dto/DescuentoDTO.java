package com.smarroquin.clinicaoss.dto;

public class DescuentoDTO {
    private Long descuentoId;
    private String nombrePromocion;
    private String descripcionPromocion;
    private Double descuentoPromocion;
    private Boolean estado;

    public Long getDescuentoId() {
        return descuentoId;
    }

    public void setDescuentoId(Long descuentoId) {
        this.descuentoId = descuentoId;
    }

    public String getNombrePromocion() {
        return nombrePromocion;
    }

    public void setNombrePromocion(String nombrePromocion) {
        this.nombrePromocion = nombrePromocion;
    }

    public String getDescripcionPromocion() {
        return descripcionPromocion;
    }

    public void setDescripcionPromocion(String descripcionPromocion) {
        this.descripcionPromocion = descripcionPromocion;
    }

    public Double getDescuentoPromocion() {
        return descuentoPromocion;
    }

    public void setDescuentoPromocion(Double descuentoPromocion) {
        this.descuentoPromocion = descuentoPromocion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
