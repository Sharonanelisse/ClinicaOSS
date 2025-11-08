package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.Paciente;
import com.smarroquin.clinicaoss.models.RegistroClinico;
import com.smarroquin.clinicaoss.models.User;
import com.smarroquin.clinicaoss.service.CatalogService;
import com.smarroquin.clinicaoss.service.RegistroClinicoService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class RegistroClinicoController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private RegistroClinicoService registroService;

    @Inject
    private CatalogService catalogService;

    private RegistroClinico registro = new RegistroClinico();
    private Long pacienteId;
    private Long odontologoId;

    // Listar registros clínicos
    public List<RegistroClinico> getRegistros() {
        return registroService.registros();
    }

    // Guardar registro clínico
    public void guardarRegistro() {
        Paciente paciente = catalogService.findPacienteById(pacienteId);
        User odontologo = catalogService.findUserById(odontologoId);

        registro.setPaciente(paciente);
        registro.setOdontologo(odontologo); // asegúrate de tener este atributo en tu modelo
        registro.setFechaCarga(LocalDateTime.now());

        registroService.guardar(registro);
        registro = new RegistroClinico(); // limpiar formulario
    }

    // Eliminar registro clínico
    public void eliminarRegistro(RegistroClinico r) {
        registroService.eliminar(r);
    }

    // Listado de pacientes para el select
    public List<Paciente> getPacientes() {
        return catalogService.pacientes();
    }

    // Listado de odontólogos (usuarios con rol "odontologo")
    public List<User> getOdontologos() {
        return catalogService.users().stream()
                .filter(u -> u.getRole() != null && "odontologo".equalsIgnoreCase(u.getRole().getNombre()))
                .toList();
    }

    // Getters y Setters
    public RegistroClinico getRegistro() {
        return registro;
    }

    public void setRegistro(RegistroClinico registro) {
        this.registro = registro;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getOdontologoId() {
        return odontologoId;
    }

    public void setOdontologoId(Long odontologoId) {
        this.odontologoId = odontologoId;
    }
}
