package com.smarroquin.clinicaoss.models;

import com.smarroquin.clinicaoss.enums.estado_pago;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Facturacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Agregar fecha de Emision")
    private LocalDateTime fechaEmision;

    @Column
    private BigDecimal subtotal;

    @Column
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private estado_pago estado_pago;

    @NotNull
    @ManyToOne(optional = false)
    private Tratamiento tratamiento;

    @NotNull
    @ManyToOne(optional = false)
    private Paciente paciente;

    @NotNull
    @ManyToOne(optional = false)
    private Cita cita;

    @NotNull
    @ManyToOne(optional = false)
    private Descuento descuento;

    @NotNull
    @ManyToOne(optional = false)
    private Seguro seguro;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public Descuento getDescuento() {
        return descuento;
    }

    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
    }

    public Seguro getSeguro() {
        return seguro;
    }

    public void setSeguro(Seguro seguro) {
        this.seguro = seguro;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Facturacion{");
        sb.append("id=").append(id);
        sb.append(", fechaEmision='").append(fechaEmision).append('\'');
        sb.append(", subtotal='").append(subtotal).append('\'');
        sb.append(", total='").append(total).append('\'');
        sb.append(", estado_pago='").append(estado_pago).append('\'');
        sb.append(", tratamiento='").append(tratamiento).append('\'');
        sb.append(", paciente='").append(paciente).append('\'');
        sb.append(", cita='").append(cita).append('\'');
        sb.append(", descuento='").append(descuento).append('\'');
        sb.append(", seguro='").append(seguro).append('\'');
        sb.append('}');

        return sb.toString();
    }

}
