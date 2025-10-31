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
    UserRepostory userRepository;
    @Inject
    RoleRepository roleRepository;

    @Inject
    PacienteRepository pacienteRepository;

    public List<User> users(){ return userRepository.findAll(); }
    public User guardar(User user){ return userRepository.guardar(user); }
    public void eliminar(User user){ userRepository.eliminar(user); }
    public User findUserById(Long id) { return userRepository.find(id); }


    public List<Role> roles() { return roleRepository.findAll(); }
    public Role guardar(Role role) { return roleRepository.guardar(role); }
    public void eliminar(Role role) { roleRepository.eliminar(role); }

    // --- Pacientes ---
    public List<Paciente> pacientes() { return pacienteRepository.findAll(); }
    public Paciente guardar(Paciente paciente) { return pacienteRepository.guardar(paciente); }
    public void eliminar(Paciente paciente) { pacienteRepository.eliminar(paciente); }
    public Paciente findPacienteById(Long id) { return pacienteRepository.find(id); }

 }



