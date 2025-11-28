package com.smarroquin.clinicaoss;

import com.smarroquin.clinicaoss.enums.*;
import com.smarroquin.clinicaoss.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class CargarDesdeJsonI {

    private static final String PERSISTENCE_UNIT = "clinicaossPU";
    private static final int TAMANO_LOTE = 50;

    private static final String USUARIOS_PATH = "data/Usuario.json";
    private static final String PACIENTES_PATH = "data/Paciente.json";
    private static final String ESPECIALIDADES_PATH = "data/Especialidad.json";
    private static final String DESCUENTOS_PATH = "data/Descuento.json";
    private static final String SEGUROS_PATH = "data/Seguro.json";

    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            cargarUsuarios(em);
            cargarPacientes(em);
            cargarEspecialidades(em);
            cargarDescuentos(em);
            cargarSeguros(em);

            em.getTransaction().commit();
            System.out.println("Carga independientes completada correctamente.");
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void cargarUsuarios(EntityManager em) throws Exception {
        List<UsuarioJson> lista = leerJson(USUARIOS_PATH, UsuarioJson.class);
        for (UsuarioJson json : lista) {
            Usuario u = new Usuario();
            u.setId(json.getUsuarioId());
            u.setNombreUsuario(json.getNombreUsuario());
            u.setApellidoUsuario(json.getApellidoUsuario());
            u.setRole_name(role_name.valueOf(json.getRole_name()));
            u.setEmail(json.getEmail());
            u.setTelefono(json.getTelefono());
            u.setPassword(json.getPassword());
            u.setStatus(json.getStatus() != null ? json.getStatus() : Boolean.TRUE);
            em.persist(u);
        }
        System.out.println("Usuarios insertados: " + lista.size());
    }

    private static void cargarPacientes(EntityManager em) throws Exception {
        List<PacienteJson> lista = leerJson(PACIENTES_PATH, PacienteJson.class);
        for (PacienteJson json : lista) {
            Paciente p = new Paciente();
            p.setId(json.getPacienteId());
            p.setNombrePaciente(json.getNombrePaciente());
            p.setApellidoPaciente(json.getApellidoPaciente());
            p.setDpi(json.getDpi());
            p.setFechaNacimiento(json.getFechaNacimiento());
            p.setEdad(json.getEdad());
            p.setTelefono(json.getTelefono());
            p.setEmail(json.getEmail());
            p.setAlergias(json.getAlergias());
            p.setCondicionesMedicas(json.getCondicionesMedicas());
            p.setObservaciones(json.getObservaciones());
            p.setFechaRegistro(json.getFechaRegistro() != null ? json.getFechaRegistro() : LocalDateTime.now());
            em.persist(p);
        }
        System.out.println("Pacientes insertados: " + lista.size());
    }

    private static void cargarEspecialidades(EntityManager em) throws Exception {
        List<EspecialidadJson> lista = leerJson(ESPECIALIDADES_PATH, EspecialidadJson.class);
        for (EspecialidadJson json : lista) {
            Especialidad e = new Especialidad();
            e.setId(json.getEspecialidadId());
            e.setNombreEspecialidad(json.getNombreEspecialidad());
            e.setDescripcion(json.getDescripcion());
            em.persist(e);
        }
        System.out.println("Especialidades insertadas: " + lista.size());
    }

    private static void cargarDescuentos(EntityManager em) throws Exception {
        List<DescuentoJson> lista = leerJson(DESCUENTOS_PATH, DescuentoJson.class);
        for (DescuentoJson json : lista) {
            Descuento d = new Descuento();
            d.setId(json.getDescuentoId());
            d.setNombrePromocion(json.getNombrePromocion());
            d.setDescripcionPromocion(json.getDescripcionPromocion());
            d.setDescuentoPromocion(json.getDescuentoPromocion());
            em.persist(d);
        }
        System.out.println("Descuentos insertados: " + lista.size());
    }

    private static void cargarSeguros(EntityManager em) throws Exception {
        List<SeguroJson> lista = leerJson(SEGUROS_PATH, SeguroJson.class);
        for (SeguroJson json : lista) {
            Seguro s = new Seguro();
            s.setId(json.getSeguroId());
            s.setCodigoAseguradora(json.getCodigoAseguradora());
            s.setNombreAseguradora(json.getNombreAseguradora());
            s.setPorcentajeDescuento(json.getPorcentajeDescuento());
            s.setFechaInicio(json.getFechaInicio());
            s.setDeducible(json.getDeducible());
            s.setFechaFinal(json.getFechaFinal());
            s.setEstado(json.getEstado());
            em.persist(s);
        }
        System.out.println("Seguros insertados: " + lista.size());
    }

    private static <T> List<T> leerJson(String path, Class<T> clazz) throws Exception {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.readValue(inputStream, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    // ---------- POJOs ----------
    public static class UsuarioJson {  private Long usuarioId;
        private String nombreUsuario;
        private String apellidoUsuario;
        private String role_name;
        private String email;
        private String telefono;
        private String password;
        private Boolean status;
        // getters y setters...

        public Long getUsuarioId() {
            return usuarioId;
        }

        public void setUsuarioId(Long id) {
            this.usuarioId = usuarioId;
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

        public void setApellidoUsuario(String apellidoUsuario) {
            this.apellidoUsuario = apellidoUsuario;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
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

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }
    }

    public static class PacienteJson {
        private Long pacienteId;
        private String nombrePaciente;
        private String apellidoPaciente;
        private String dpi;
        private LocalDate fechaNacimiento;
        private Integer edad;
        private String telefono;
        private String email;
        private String alergias;
        private String condicionesMedicas;
        private String observaciones;
        private LocalDateTime fechaRegistro;
        // getters y setters...


        public Long getPacienteId() {
            return pacienteId;
        }

        public void setPacienteId(Long pacienteId) {
            this.pacienteId = pacienteId;
        }

        public String getNombrePaciente() {
            return nombrePaciente;
        }

        public void setNombrePaciente(String nombrePaciente) {
            this.nombrePaciente = nombrePaciente;
        }

        public String getApellidoPaciente() {
            return apellidoPaciente;
        }

        public void setApellidoPaciente(String apellidoPaciente) {
            this.apellidoPaciente = apellidoPaciente;
        }

        public String getDpi() {
            return dpi;
        }

        public void setDpi(String dpi) {
            this.dpi = dpi;
        }

        public LocalDate getFechaNacimiento() {
            return fechaNacimiento;
        }

        public void setFechaNacimiento(LocalDate fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
        }

        public Integer getEdad() {
            return edad;
        }

        public void setEdad(Integer edad) {
            this.edad = edad;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAlergias() {
            return alergias;
        }

        public void setAlergias(String alergias) {
            this.alergias = alergias;
        }

        public String getCondicionesMedicas() {
            return condicionesMedicas;
        }

        public void setCondicionesMedicas(String condicionesMedicas) {
            this.condicionesMedicas = condicionesMedicas;
        }

        public String getObservaciones() {
            return observaciones;
        }

        public void setObservaciones(String observaciones) {
            this.observaciones = observaciones;
        }

        public LocalDateTime getFechaRegistro() {
            return fechaRegistro;
        }

        public void setFechaRegistro(LocalDateTime fechaRegistro) {
            this.fechaRegistro = fechaRegistro;
        }
    }

    public static class EspecialidadJson {private Long especialidadId;
        private String nombreEspecialidad;
        private String descripcion;
        // getters y setters...

        public Long getEspecialidadId() {
            return especialidadId;
        }

        public void setEspecialidadId(Long especialidadId) {
            this.especialidadId = especialidadId;
        }

        public String getNombreEspecialidad() {
            return nombreEspecialidad;
        }

        public void setNombreEspecialidad(String nombreEspecialidad) {
            this.nombreEspecialidad = nombreEspecialidad;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }
    }

    public static class DescuentoJson { private Long descuentoId;
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

    public static class SeguroJson { private Long seguroId;
        private String codigoAseguradora;
        private String nombreAseguradora;
        private Double porcentajeDescuento;
        private Date fechaInicio;
        private BigDecimal deducible;
        private Date fechaFinal;
        private Boolean estado;

        public Long getSeguroId() {
            return seguroId;
        }

        public void setSeguroId(Long seguroId) {
            this.seguroId = seguroId;
        }

        public String getCodigoAseguradora() {
            return codigoAseguradora;
        }

        public void setCodigoAseguradora(String codigoAseguradora) {
            this.codigoAseguradora = codigoAseguradora;
        }

        public String getNombreAseguradora() {
            return nombreAseguradora;
        }

        public void setNombreAseguradora(String nombreAseguradora) {
            this.nombreAseguradora = nombreAseguradora;
        }

        public Double getPorcentajeDescuento() {
            return porcentajeDescuento;
        }

        public void setPorcentajeDescuento(Double porcentajeDescuento) {
            this.porcentajeDescuento = porcentajeDescuento;
        }

        public Date getFechaInicio() {
            return fechaInicio;
        }

        public void setFechaInicio(Date fechaInicio) {
            this.fechaInicio = fechaInicio;
        }

        public BigDecimal getDeducible() {
            return deducible;
        }

        public void setDeducible(BigDecimal deducible) {
            this.deducible = deducible;
        }

        public Date getFechaFinal() {
            return fechaFinal;
        }

        public void setFechaFinal(Date fechaFinal) {
            this.fechaFinal = fechaFinal;
        }

        public Boolean getEstado() {
            return estado;
        }

        public void setEstado(Boolean estado) {
            this.estado = estado;
        }
    }
}
