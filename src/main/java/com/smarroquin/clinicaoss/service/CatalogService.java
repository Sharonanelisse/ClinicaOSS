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
    private UsuarioRepostory userRepository;

    @Inject
    private PacienteRepository pacienteRepository;

    @Inject
    private JornadaLaboralRepository jornadalaboralRepository;

    @Inject
    private CitaRepository citaRepository;

    @Inject
    private TratamientoRepository tratamientoRepository;

    @Inject
    private EspecialidadesRepository especialidadesRepository;

    @Inject
    private RegistroClinicoRepository repositorioClinicoRepository;

    @Inject
    private FacturacionRepository facturacionRepository;

    @Inject
    private DescuentoRepository descuentoRepository;

    @Inject
    private SeguroRepository seguroRepository;


    //Users
    public List<Usuario> users() { return userRepository.findAll(); }
    public Usuario guardar(Usuario user) { return userRepository.guardar(user); }
    public void eliminar(Usuario user) { userRepository.eliminar(user); }
    public Usuario findUserById(Long id) { return userRepository.find(id); }

    //Pacientes
    public List<Paciente> pacientes() { return pacienteRepository.findAll(); }
    public Paciente guardar(Paciente paciente) { return pacienteRepository.guardar(paciente); }
    public void eliminar(Paciente paciente) { pacienteRepository.eliminar(paciente); }
    public Paciente findPacienteById(Long id) { return pacienteRepository.find(id); }

    //JornadaLaboral
    public List<JornadaLaboral> jornadasPorUsuario(Usuario userContext) { return jornadalaboralRepository.findAll(); }
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


    //Especialidad
    public List<Especialidad> especialidades() { return especialidadesRepository.findAll(); }
    public Especialidad guardar(Especialidad especialidad) { return especialidadesRepository.guardar(especialidad); }
    public void eliminar(Especialidad especialidad) { especialidadesRepository.eliminar(especialidad); }
    public Especialidad findEspecialidadById(Long id) { return especialidadesRepository.find(id); }


    //Registro Clinico
    public List<RegistroClinico> registrosClinicos() { return repositorioClinicoRepository.findAll(); }
    public RegistroClinico guardar(RegistroClinico registroClinico) { return repositorioClinicoRepository.guardar(registroClinico); }
    public void eliminar(RegistroClinico registroClinico) { repositorioClinicoRepository.eliminar(registroClinico); }
    public RegistroClinico findRegistroClinicoById(Long id) { return repositorioClinicoRepository.find(id); }

    //Facturacion
    public List<Facturacion> facturaciones() { return facturacionRepository.findAll(); }
    public Facturacion guardar(Facturacion facturacion) { return facturacionRepository.guardar(facturacion); }
    public void eliminar(Facturacion facturacion) { facturacionRepository.eliminar(facturacion); }
    public Facturacion findFacturacionById(Long id) { return facturacionRepository.find(id); }

    //Descuento
    public List<Descuento> descuentos() { return descuentoRepository.findAll(); }
    public Descuento guardar(Descuento descuento) { return descuentoRepository.guardar(descuento); }
    public void eliminar(Descuento descuento) { descuentoRepository.eliminar(descuento); }
    public Descuento findSescuentoById(Long id) { return descuentoRepository.find(id); }

    //Seguro
    public List<Seguro> seguros() { return seguroRepository.findAll(); }
    public Seguro guardar(Seguro seguro) { return seguroRepository.guardar(seguro); }
    public void eliminar(Seguro seguro) { seguroRepository.eliminar(seguro); }
    public Seguro findSeguroById(Long id) { return seguroRepository.find(id); }

}
