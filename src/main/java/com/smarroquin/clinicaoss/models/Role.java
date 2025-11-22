package com.smarroquin.clinicaoss.models;

import com.smarroquin.clinicaoss.enums.role_name;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Seleccionar el role")
    @Enumerated(EnumType.STRING)
    private role_name role_name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public role_name getRole_name() {
        return role_name;
    }

    public void setRole_name(role_name role_name) {
        this.role_name = role_name;
    }
}
