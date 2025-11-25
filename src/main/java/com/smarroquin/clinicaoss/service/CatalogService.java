package com.smarroquin.clinicaoss.service;


import com.smarroquin.clinicaoss.models.*;
import com.smarroquin.clinicaoss.repositories.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ApplicationScoped
public class CatalogService implements Serializable {
    private static final long serialVersionUID = 1L;

    public CatalogService() { }

    @Inject
    private UserRepostory userRepository;

    @Inject
    private PacienteRepository pacienteRepository;

    @Inject
    private JornadaLaboralRepository jornadalaboralRepository;

    @Inject
    private CitaRepository citaRepository;

    @Inject
    private TratamientoRepository tratamientoRepository;

    @Inject
    FileRepository fileRepository;


    //Users
    public List<User> users() { return userRepository.findAll(); }
    public User guardar(User user) { return userRepository.guardar(user); }
    public void eliminar(User user) { userRepository.eliminar(user); }
    public User findUserById(Long id) { return userRepository.find(id); }

    //Files
    public File saveFile(File f) { return fileRepository.guardar(f); }
    public List<File> files() { return fileRepository.findAll(); }
    public void deleteFile(File f) { fileRepository.eliminar(f); }

    //Pacientes
    public List<Paciente> pacientes() { return pacienteRepository.findAll(); }
    public Paciente guardar(Paciente paciente) { return pacienteRepository.guardar(paciente); }
    public void eliminar(Paciente paciente) { pacienteRepository.eliminar(paciente); }
    public Paciente findPacienteById(Long id) { return pacienteRepository.find(id); }

    //JornadaLaboral
    public List<JornadaLaboral> jornadasPorUsuario(User userContext) { return jornadalaboralRepository.findAll(); }
    public JornadaLaboral guardar(JornadaLaboral jornadaLaboral) { return jornadalaboralRepository.guardar(jornadaLaboral); }
    public void eliminar(JornadaLaboral jornadaLaboral) { jornadalaboralRepository.eliminar(jornadaLaboral); }

    //Citas
    public List<Cita> citas() { return citaRepository.findAll(); }
    public Cita guardar(Cita cita) { return citaRepository.guardar(cita); }
    public void eliminar(Cita cita) { citaRepository.eliminar(cita); }
    public Cita findCitaById(Long id) { return citaRepository.find(id); }


    //Tratamientos
    public List<Tratamiento> tratamientos() { return tratamientoRepository.findAll(); }
    public Tratamiento guardar(Tratamiento tratamiento) { return tratamientoRepository.guardar(tratamiento); }
    public void eliminar(Tratamiento tratamiento) { tratamientoRepository.eliminar(tratamiento); }
    public Tratamiento findTratamientoById(Long id) { return tratamientoRepository.find(id); }

}
