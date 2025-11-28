package com.smarroquin.clinicaoss.dto;

import com.smarroquin.clinicaoss.enums.estado_pago;
import com.smarroquin.clinicaoss.models.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FacturacionDTO {
    private Long facturacionId;
    private LocalDateTime fechaEmision;
    private BigDecimal subtotal;
    private BigDecimal total;
    private estado_pago estado_pago;
    private Tratamiento tratamiento;
    private Long pacienteId;
    private Long citaId;
    private Long descuentoId;
    private Long seguroId;

    public Long getFacturacionId() {
        return facturacionId;
    }

    public void setFacturacionId(Long facturacionId) {
        this.facturacionId = facturacionId;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public estado_pago getEstado_pago() {
        return estado_pago;
    }

    public void setEstado_pago(estado_pago estado_pago) {
        this.estado_pago = estado_pago;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }

    public Long getDescuentoId() {
        return descuentoId;
    }

    public void setDescuentoId(Long descuentoId) {
        this.descuentoId = descuentoId;
    }

    public Long getSeguroId() {
        return seguroId;
    }

    public void setSeguroId(Long seguroId) {
        this.seguroId = seguroId;
    }
}
