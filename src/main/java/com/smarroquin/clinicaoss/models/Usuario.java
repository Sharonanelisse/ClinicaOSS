package com.smarroquin.clinicaoss.models;

import com.smarroquin.clinicaoss.enums.role_name;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "usuario", schema = "public")

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre completo es obligatorio.")
    @Size(min = 3, max = 60, message = "El nombre completo debe tener entre 3 y 60 caracteres.")
    @Column(length = 60, nullable = false)
    private String nombreUsuario;

    @NotBlank(message = "El nombre completo es obligatorio.")
    @Size(min = 3, max = 60, message = "El nombre completo debe tener entre 3 y 60 caracteres.")
    @Column(length = 60, nullable = false)
    private String apellidoUsuario;

    @NotNull(message = "Seleccionar el role")
    @Enumerated(EnumType.STRING)
    private role_name role_name;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El correo electrónico no tiene un formato válido.")
    @Column(length = 120, nullable = false)
    private String email;

    @NotBlank(message = "El teléfono es obligatorio.")
    @Pattern(regexp = "\\d{8}", message = "El teléfono debe tener exactamente 8 dígitos.")
    @Column(length = 8, nullable = false)
    private String telefono;

    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,20}$",
            message = "La contraseña debe incluir al menos: una letra mayúscula, una minúscula, un número y un carácter especial."
    )
    @Column(length = 60, nullable = false)
    private String password;


    @NotNull(message = "El estado es obligatorio.")
    @Column(nullable = false)
    private Boolean status = true;

    @PrePersist
    protected void prePersist() {
        if (status == null) status = true;
        if (nombreUsuario != null) nombreUsuario = nombreUsuario.trim();
        if (apellidoUsuario != null) apellidoUsuario = apellidoUsuario.trim();
        if (email != null) email = email.trim().toLowerCase();
        if (telefono != null) telefono = telefono.trim();
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public role_name getRole_name() {
        return role_name;
    }

    public void setRole_name(role_name role_name) {
        this.role_name = role_name;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Usuario{");
        sb.append("id=").append(id);
        sb.append(", nombreUsuario='").append(nombreUsuario).append('\'');
        sb.append(", apellidoUsuario='").append(apellidoUsuario).append('\'');
        sb.append(", role_name='").append(role_name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", telefono='").append(telefono).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append('}');

        return sb.toString();
    }
}
