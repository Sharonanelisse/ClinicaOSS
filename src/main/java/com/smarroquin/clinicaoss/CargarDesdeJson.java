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
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class CargarDesdeJson {
    private static final String PERSISTENCE_UNIT = "clinicaossPU";
    private static final int TAMANO_LOTE = 50;

    private static final String USUARIOS_PATH = "data/Usuario.json";
    private static final String PACIENTES_PATH = "data/Paciente.json";
    private static final String ESPECIALIDADES_PATH = "data/Especialidad.json";
    private static final String DESCUENTOS_PATH = "data/Descuento.json";
    private static final String SEGUROS_PATH = "data/Seguro.json";

    private static final String TRATAMIENTOS_PATH = "data/Tratamiento.json";
    private static final String CITAS_PATH = "data/Cita.json";
    private static final String REGISTROS_PATH = "data/RegistroClinico.json";
    private static final String FACTURACIONES_PATH = "data/Facturacion.json";
    private static final String JORNADAS_PATH = "data/JornadaLaboral.json";

    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();

        try {

            //Cargar archivos Independientes
            em.getTransaction().begin();
            cargarUsuarios(em);
            cargarPacientes(em);
            cargarEspecialidades(em);
            cargarDescuentos(em);
            cargarSeguros(em);

            em.getTransaction().commit();
            System.out.println("Carga independientes completada correctamente.");

            // Fase 1: cargar tratamientos
            em.getTransaction().begin();
            cargarTratamientos(em);
            em.getTransaction().commit();
            System.out.println("Tratamientos cargados correctamente.");

            // Fase 2: cargar el resto
            em.getTransaction().begin();
            cargarCitas(em);
            cargarRegistros(em);
            cargarFacturaciones(em);
            cargarJornadas(em);
            em.getTransaction().commit();
            System.out.println("Dependientes cargados correctamente.");


        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void cargarUsuarios(EntityManager em) throws Exception {
        List<CargarDesdeJson.UsuarioJson> lista = leerJson(USUARIOS_PATH, CargarDesdeJson.UsuarioJson.class);
        for (CargarDesdeJson.UsuarioJson json : lista) {
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
        List<CargarDesdeJson.PacienteJson> lista = leerJson(PACIENTES_PATH, CargarDesdeJson.PacienteJson.class);
        for (CargarDesdeJson.PacienteJson json : lista) {
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
            p.setActivo(json.getActivoPaciente());
            p.setFechaRegistro(json.getFechaRegistro() != null ? json.getFechaRegistro() : LocalDateTime.now());
            p.setNumeroExpediente(json.getNumeroExpediente());
            p.setFechaUltimaActualizacion(json.getFechaUltimaActualizacion());
            em.persist(p);
        }
        System.out.println("Pacientes insertados: " + lista.size());
    }

    private static void cargarEspecialidades(EntityManager em) throws Exception {
        List<CargarDesdeJson.EspecialidadJson> lista = leerJson(ESPECIALIDADES_PATH, CargarDesdeJson.EspecialidadJson.class);
        for (CargarDesdeJson.EspecialidadJson json : lista) {
            Especialidad e = new Especialidad();
            e.setId(json.getEspecialidadId());
            e.setNombreEspecialidad(json.getNombreEspecialidad());
            e.setDescripcion(json.getDescripcion());
            e.setActivoEspecialidad(json.getActivoEspecialidad());
            em.persist(e);
        }
        System.out.println("Especialidades insertadas: " + lista.size());
    }

    private static void cargarDescuentos(EntityManager em) throws Exception {
        List<CargarDesdeJson.DescuentoJson> lista = leerJson(DESCUENTOS_PATH, CargarDesdeJson.DescuentoJson.class);
        for (CargarDesdeJson.DescuentoJson json : lista) {
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
        List<CargarDesdeJson.SeguroJson> lista = leerJson(SEGUROS_PATH, CargarDesdeJson.SeguroJson.class);
        for (CargarDesdeJson.SeguroJson json : lista) {
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

    private static void cargarTratamientos(EntityManager em) throws Exception {
        List<CargarDesdeJson.TratamientoJson> lista = leerJson(TRATAMIENTOS_PATH, CargarDesdeJson.TratamientoJson.class);
        for (CargarDesdeJson.TratamientoJson json : lista) {
            Tratamiento t = new Tratamiento();
            t.setId(json.getTratamientoId());
            t.setNombreTratamiento(json.getNombreTratamiento());
            t.setDescripcion(json.getDescripcion());
            t.setDuracionEstimado(json.getDuracionEstimado());
            t.setCosto(json.getCosto());

            Especialidad especialidad = em.find(Especialidad.class, json.getEspecialidadId());
            if (especialidad == null)
                throw new RuntimeException("Especialidad no encontrada con id: " + json.getEspecialidadId());
            t.setEspecialidad(especialidad);

            t.setActivoTratamiento(json.getActivoTratamiento());

            em.persist(t);
        }
        System.out.println("Tratamientos insertados: " + lista.size());
    }

    private static void cargarCitas(EntityManager em) throws Exception {
        List<CargarDesdeJson.CitaJson> lista = leerJson(CITAS_PATH, CargarDesdeJson.CitaJson.class);
        for (CargarDesdeJson.CitaJson json : lista) {
            Cita c = new Cita();
            c.setId(json.getCitaId());
            c.setCodigo(json.getCodigo());
            c.setFechaApertura(json.getFechaApertura());
            c.setEstado_cita(json.getEstado_cita());
            c.setObservacionesCita(json.getObservacionesCita());
            c.setPaciente(em.find(Paciente.class, json.getPacienteId()));
            c.setUser(em.find(Usuario.class, json.getUserId()));
            c.setTratamiento(em.find(Tratamiento.class, json.getTratamientoId()));
            em.persist(c);
        }
        System.out.println("Citas insertadas: " + lista.size());
    }

    private static void cargarRegistros(EntityManager em) throws Exception {
        List<CargarDesdeJson.RegistroClinicoJson> lista = leerJson(REGISTROS_PATH, CargarDesdeJson.RegistroClinicoJson.class);
        for (CargarDesdeJson.RegistroClinicoJson json : lista) {
            RegistroClinico r = new RegistroClinico();
            r.setId(json.getRegistroId());
            r.setNumeroRegistro(json.getNumeroRegistro());
            r.setTipo_archivo(json.getTipo_archivo());
            r.setTipo_contenido(json.getTipo_contenido());
            r.setBlob_url(json.getBlob_url());
            r.setBlobName(json.getBlob_url());
            r.setFechaCarga(json.getFechaCarga());
            r.setPaciente(em.find(Paciente.class, json.getPacienteId()));
            r.setUser(em.find(Usuario.class, json.getUserId()));
            r.setCita(em.find(Cita.class, json.getCitaId()));
            em.persist(r);
        }
        System.out.println("Registros insertados: " + lista.size());
    }

    private static void cargarFacturaciones(EntityManager em) throws Exception {
        List<CargarDesdeJson.FacturacionJson> lista = leerJson(FACTURACIONES_PATH, CargarDesdeJson.FacturacionJson.class);
        for (CargarDesdeJson.FacturacionJson json : lista) {
            Facturacion f = new Facturacion();
            f.setId(json.getFacturacionId());
            f.setFechaEmision(json.getFechaEmision());
            f.setSubtotal(json.getSubtotal());
            f.setTotal(json.getTotal());
            f.setEstado_pago(json.getEstado_pago());
            f.setTratamiento(em.find(Tratamiento.class, json.getTratamientoId()));
            f.setPaciente(em.find(Paciente.class, json.getPacienteId()));
            f.setCita(em.find(Cita.class, json.getCitaId()));
            f.setDescuento(em.find(Descuento.class, json.getDescuentoId()));
            f.setSeguro(em.find(Seguro.class, json.getSeguroId()));
            em.persist(f);
        }
        System.out.println("Facturaciones insertadas: " + lista.size());
    }

    private static void cargarJornadas(EntityManager em) throws Exception {
        List<CargarDesdeJson.JornadaLaboralJson> lista = leerJson(JORNADAS_PATH, CargarDesdeJson.JornadaLaboralJson.class);
        for (CargarDesdeJson.JornadaLaboralJson json : lista) {
            JornadaLaboral j = new JornadaLaboral();
            j.setId(json.getJornadaLaboralId());
            j.setDia_semana(json.getDia_semana());
            j.setHoraInicio(json.getHoraInicio());
            j.setHoraFin(json.getHoraFin());
            j.setUser(em.find(Usuario.class, json.getUserId()));
            em.persist(j);
        }
        System.out.println("Jornadas insertadas: " + lista.size());
    }

    private static <T> List<T> leerJson(String path, Class<T> clazz) throws Exception {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new IllegalStateException("No se encontró el archivo: " + path);
        }
        ObjectMapper mapper = crearMapper();
        return mapper.readValue(
                inputStream,
                mapper.getTypeFactory().constructCollectionType(List.class, clazz)
        );
    }

    private static ObjectMapper crearMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // LocalDate / LocalDateTime
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
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
        private String numeroExpediente;
        private LocalDateTime fechaUltimaActualizacion;
        private Boolean activoPaciente;

        // getters y setters...

        public String getNumeroExpediente() { return numeroExpediente; }
        public void setNumeroExpediente(String numeroExpediente) { this.numeroExpediente = numeroExpediente; }

        public LocalDateTime getFechaUltimaActualizacion() { return fechaUltimaActualizacion; }
        public void setFechaUltimaActualizacion(LocalDateTime fechaUltimaActualizacion) { this.fechaUltimaActualizacion = fechaUltimaActualizacion; }


        public Boolean getActivoPaciente() {
            return activoPaciente;
        }

        public void setActivoPaciente(Boolean activoPaciente) {
            this.activoPaciente = activoPaciente;
        }

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
        private Boolean activoEspecialidad;
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
        public Boolean getActivoEspecialidad() {
            return activoEspecialidad;
        }

        public void setActivoEspecialidad(Boolean activoEspecialidad) {
            this.activoEspecialidad = activoEspecialidad;
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

    public static class TratamientoJson {
        private Long tratamientoId;
        private String nombreTratamiento;
        private String descripcion;
        private Double duracionEstimado;
        private BigDecimal costo;
        private Long especialidadId;
        private Boolean activoTratamiento;
        // getters y setters...

        public Boolean getActivoTratamiento() { return activoTratamiento; }
        public void setActivoTratamiento(Boolean activoTratamiento) { this.activoTratamiento = activoTratamiento; }

        public Long getTratamientoId() {
            return tratamientoId;
        }

        public void setTratamientoId(Long id) {
            this.tratamientoId = tratamientoId;
        }

        public String getNombreTratamiento() {
            return nombreTratamiento;
        }

        public void setNombreTratamiento(String nombreTratamiento) {
            this.nombreTratamiento = nombreTratamiento;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public Double getDuracionEstimado() {
            return duracionEstimado;
        }

        public void setDuracionEstimado(Double duracionEstimado) {
            this.duracionEstimado = duracionEstimado;
        }

        public BigDecimal getCosto() {
            return costo;
        }

        public void setCosto(BigDecimal costo) {
            this.costo = costo;
        }

        public Long getEspecialidadId() {
            return especialidadId;
        }

        public void setEspecialidadId(Long especialidadId) {
            this.especialidadId = especialidadId;
        }
    }

    public static class CitaJson {
        private Long citaId;
        private String codigo;
        private LocalDateTime fechaApertura;
        private estado_cita estado_cita;
        private String observacionesCita;
        private Long pacienteId;
        private Long userId;
        private Long tratamientoId;

        // getters y setters...


        public Long getCitaId() {
            return citaId;
        }

        public void setCitaId(Long id) {
            this.citaId = citaId;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public LocalDateTime getFechaApertura() {
            return fechaApertura;
        }

        public void setFechaApertura(LocalDateTime fechaApertura) {
            this.fechaApertura = fechaApertura;
        }

        public estado_cita getEstado_cita() {
            return estado_cita;
        }

        public void setEstado_cita(estado_cita estado_cita) {
            this.estado_cita = estado_cita;
        }

        public String getObservacionesCita() {
            return observacionesCita;
        }

        public void setObservacionesCita(String observacionesCita) {
            this.observacionesCita = observacionesCita;
        }

        public Long getPacienteId() {
            return pacienteId;
        }

        public void setPacienteId(Long pacienteId) {
            this.pacienteId = pacienteId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Long getTratamientoId() {
            return tratamientoId;
        }

        public void setTratamientoId(Long tratamientoId) {
            this.tratamientoId = tratamientoId;
        }
    }

    public static class RegistroClinicoJson {
        private Long registroId;
        private String numeroRegistro;
        private tipo_archivo tipo_archivo;
        private String tipo_contenido;
        private String blob_url;
        private String blobName;
        private LocalDateTime fechaCarga;
        private Long pacienteId;
        private Long userId;
        private Long citaId;

        public Long getRegistroId() {
            return registroId;
        }

        public void setRegistroId(Long registroId) {
            this.registroId = registroId;
        }

        public tipo_archivo getTipo_archivo() {
            return tipo_archivo;
        }

        public void setTipo_archivo(tipo_archivo tipo_archivo) {
            this.tipo_archivo = tipo_archivo;
        }

        public String getNumeroRegistro() {
            return numeroRegistro;
        }

        public void setNumeroRegistro(String numeroRegistro) {
            this.numeroRegistro = numeroRegistro;
        }

        public String getTipo_contenido() {
            return tipo_contenido;
        }

        public void setTipo_contenido(String tipo_contenido) {
            this.tipo_contenido = tipo_contenido;
        }

        public String getBlob_url() {
            return blob_url;
        }

        public void setBlob_url(String blob_url) {
            this.blob_url = blob_url;
        }

        public String getBlobName() {
            return blobName;
        }

        public void setBlobName(String blobName) {
            this.blobName = blobName;
        }

        public LocalDateTime getFechaCarga() {
            return fechaCarga;
        }

        public void setFechaCarga(LocalDateTime fechaCarga) {
            this.fechaCarga = fechaCarga;
        }

        public Long getPacienteId() {
            return pacienteId;
        }

        public void setPacienteId(Long pacienteId) {
            this.pacienteId = pacienteId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Long getCitaId() {
            return citaId;
        }

        public void setCitaId(Long citaId) {
            this.citaId = citaId;
        }
    }

    public static class FacturacionJson {
        private Long facturacionId;
        private LocalDateTime fechaEmision;
        private BigDecimal subtotal;
        private BigDecimal total;
        private estado_pago estado_pago;
        private Long tratamientoId;
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

        public Long getTratamientoId() {
            return tratamientoId;
        }

        public void setTratamientoId(Long tratamientoId) {
            this.tratamientoId = tratamientoId;
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

        public static class JornadaLaboralJson {
            private Long jornadaLaboralId;
            private dia_semana dia_semana;
            private LocalTime horaInicio;
            private LocalTime horaFin;
            private Long userId;

            public Long getJornadaLaboralId() {
                return jornadaLaboralId;
            }

            public void setJornadaLaboralId(Long jornadaLaboralId) {
                this.jornadaLaboralId = jornadaLaboralId;
            }

            public dia_semana getDia_semana() {
                return dia_semana;
            }

            public void setDia_semana(dia_semana dia_semana) {
                this.dia_semana = dia_semana;
            }

            public LocalTime getHoraInicio() {
                return horaInicio;
            }

            public void setHoraInicio(LocalTime horaInicio) {
                this.horaInicio = horaInicio;
            }

            public LocalTime getHoraFin() {
                return horaFin;
            }

            public void setHoraFin(LocalTime horaFin) {
                this.horaFin = horaFin;
            }

            public Long getUserId() {
                return userId;
            }

            public void setUserId(Long userId) {
                this.userId = userId;
            }
        }
    }

    public static class JornadaLaboralJson {
        private Long jornadaLaboralId;
        private dia_semana dia_semana;
        private LocalTime horaInicio;
        private LocalTime horaFin;
        private Long userId;

        public Long getJornadaLaboralId() {
            return jornadaLaboralId;
        }

        public void setJornadaLaboralId(Long jornadaLaboralId) {
            this.jornadaLaboralId = jornadaLaboralId;
        }

        public dia_semana getDia_semana() {
            return dia_semana;
        }

        public void setDia_semana(dia_semana dia_semana) {
            this.dia_semana = dia_semana;
        }

        public LocalTime getHoraInicio() {
            return horaInicio;
        }

        public void setHoraInicio(LocalTime horaInicio) {
            this.horaInicio = horaInicio;
        }

        public LocalTime getHoraFin() {
            return horaFin;
        }

        public void setHoraFin(LocalTime horaFin) {
            this.horaFin = horaFin;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }
}
