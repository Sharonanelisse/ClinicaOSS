package com.smarroquin.clinicaoss.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Descuento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    @NotBlank(message = "Agregar nombre de la promocion")
    private String nombrePromocion;

    @Column(nullable = false, unique = true, length = 255)
    @NotBlank(message = "Agregar detalles de la promocion")
    private String descripcionPromocion;

    @Column
    @NotBlank(message = "Agregar porcentaje de descuento")
    private Double descuentoPromocion;

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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Descuento{");
        sb.append("id=").append(id);
        sb.append(", nombrePromocion='").append(nombrePromocion).append('\'');
        sb.append(", descripcionPromocion='").append(descripcionPromocion).append('\'');
        sb.append(", descuentoPromocion='").append(descuentoPromocion).append('\'');
        sb.append(", estado='").append(estado).append('\'');
        sb.append('}');

        return sb.toString();
    }
}
