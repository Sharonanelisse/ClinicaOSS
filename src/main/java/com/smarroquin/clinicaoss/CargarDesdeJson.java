package com.smarroquin.clinicaoss;

import com.smarroquin.clinicaoss.enums.*;
import com.smarroquin.clinicaoss.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.validation.constraints.NotBlank;

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

    // Rutas de los JSON
    private static final String USUARIOS_PATH = "data/Usuario.json";
    private static final String PACIENTES_PATH = "data/Paciente.json";
    private static final String ESPECIALIDADES_PATH = "data/Especialidad.json";
    private static final String TRATAMIENTOS_PATH = "data/Tratamiento.json";
    private static final String CITAS_PATH = "data/Cita.json";
    private static final String REGISTROS_PATH = "data/RegistroClinico.json";
    private static final String DESCUENTOS_PATH = "data/Descuento.json";
    private static final String SEGUROS_PATH = "data/Seguro.json";
    private static final String FACTURACIONES_PATH = "data/Facturacion.json";
    private static final String JORNADAS_PATH = "data/JornadaLaboral.json";

    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            cargarUsuarios(em);
            cargarPacientes(em);
            cargarEspecialidades(em);
            cargarTratamientos(em);
            cargarCitas(em);
            cargarRegistros(em);
            cargarDescuentos(em);
            cargarSeguros(em);
            cargarFacturaciones(em);
            cargarJornadas(em);

            em.getTransaction().commit();
            System.out.println("Carga desde JSON completada correctamente.");
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void cargarUsuarios(EntityManager em) throws Exception {
        List<UsuarioJson> lista = leerJson(USUARIOS_PATH, UsuarioJson.class);
        int contador = 0;
        for (UsuarioJson json : lista) {
            Usuario u = new Usuario();
            u.setId(json.getId());
            u.setNombreUsuario(json.getNombreUsuario());
            u.setApellidoUsuario(json.getApellidoUsuario());
            u.setRole_name(role_name.valueOf(json.getRole_name()));
            u.setEmail(json.getEmail());
            u.setTelefono(json.getTelefono());
            u.setPassword(json.getPassword());
            u.setStatus(json.getStatus() != null ? json.getStatus() : Boolean.TRUE);
            em.persist(u);

            if (++contador % TAMANO_LOTE == 0) {
                em.flush();
                em.clear();
            }
        }
        System.out.println("Usuarios insertados: " + lista.size());
    }

    private static void cargarPacientes(EntityManager em) throws Exception {
        List<PacienteJson> lista = leerJson(PACIENTES_PATH, PacienteJson.class);
        int contador = 0;
        for (PacienteJson json : lista) {
            Paciente p = new Paciente();
            p.setId(json.getId());
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

            if (++contador % TAMANO_LOTE == 0) {
                em.flush();
                em.clear();
            }
        }
        System.out.println("Pacientes insertados: " + lista.size());
    }

    private static void cargarEspecialidades(EntityManager em) throws Exception {
        List<EspecialidadJson> lista = leerJson(ESPECIALIDADES_PATH, EspecialidadJson.class);
        for (EspecialidadJson json : lista) {
            Especialidad e = new Especialidad();
            e.setId(json.getId());
            e.setNombreEspecialidad(json.getNombreEspecialidad());
            e.setDescripcion(json.getDescripcion());
            em.persist(e);
        }
        System.out.println("Especialidades insertadas: " + lista.size());
    }

    private static void cargarTratamientos(EntityManager em) throws Exception {
        List<TratamientoJson> lista = leerJson(TRATAMIENTOS_PATH, TratamientoJson.class);
        for (TratamientoJson json : lista) {
            Tratamiento t = new Tratamiento();
            t.setId(json.getId());
            t.setNombreTratamiento(json.getNombreTratamiento());
            t.setDescripcion(json.getDescripcion());
            t.setDuracionEstimado(json.getDuracionEstimado());
            t.setCosto(json.getCosto());
            t.setEspecialidad(em.find(Especialidad.class, json.getEspecialidad().getId()));
            em.persist(t);
        }
        System.out.println("Tratamientos insertados: " + lista.size());
    }

    private static void cargarCitas(EntityManager em) throws Exception {
        List<CitaJson> lista = leerJson(CITAS_PATH, CitaJson.class);
        for (CitaJson json : lista) {
            Cita c = new Cita();
            c.setId(json.getId());
            c.setCodigo(json.getCodigo());
            c.setFechaApertura(json.getFechaApertura());
            c.setEstado_cita(json.getEstado_cita());
            c.setObservacionesCita(json.getObservacionesCita());
            c.setPaciente(em.find(Paciente.class, json.getPaciente().getId()));
            c.setUser(em.find(Usuario.class, json.getUser().getId()));
            c.setTratamiento(em.find(Tratamiento.class, json.getTratamiento().getId()));
            em.persist(c);
        }
        System.out.println("Citas insertadas: " + lista.size());
    }

    private static void cargarRegistros(EntityManager em) throws Exception {
        List<RegistroClinicoJson> lista = leerJson(REGISTROS_PATH, RegistroClinicoJson.class);
        for (RegistroClinicoJson json : lista) {
            RegistroClinico r = new RegistroClinico();
            r.setId(json.getId());
            r.setNumeroRegistro(json.getNumeroRegistro());
            r.setTipo_archivo(json.getTipo_archivo());
            r.setTipo_contenido(json.getTipo_contenido());
            r.setBlob_url(json.getBlob_url());
            r.setBlobName(json.getBlob_url());
            r.setFechaCarga(json.getFechaCarga());
            r.setPaciente(em.find(Paciente.class, json.getPaciente().getId()));
            r.setUser(em.find(Usuario.class, json.getUser().getId()));
            r.setCita(em.find(Cita.class, json.getCita().getId()));
            em.persist(r);
        }
        System.out.println("Registros insertados: " + lista.size());
    }

    private static void cargarDescuentos(EntityManager em) throws Exception {
        List<DescuentoJson> lista = leerJson(DESCUENTOS_PATH, DescuentoJson.class);
        for (DescuentoJson json : lista) {
            Descuento d = new Descuento();
            d.setId(json.getId());
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
            s.setId(json.getId());
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

    private static void cargarFacturaciones(EntityManager em) throws Exception {
        List<FacturacionJson> lista = leerJson(FACTURACIONES_PATH, FacturacionJson.class);
        for (FacturacionJson json : lista) {
            Facturacion f = new Facturacion();
            f.setId(json.getId());
            f.setFechaEmision(json.getFechaEmision());
            f.setSubtotal(json.getSubtotal());
            f.setTotal(json.getTotal());
            f.setEstado_pago(json.getEstado_pago());
            f.setTratamiento(em.find(Tratamiento.class, json.getTratamiento().getId()));
            f.setPaciente(em.find(Paciente.class, json.getPaciente().getId()));
            f.setCita(em.find(Cita.class, json.getCita().getId()));
            f.setDescuento(em.find(Descuento.class, json.getDescuento().getId()));
            f.setSeguro(em.find(Seguro.class, json.getSeguro().getId()));
            em.persist(f);
        }
        System.out.println("Facturaciones insertadas: " + lista.size());
    }

    private static void cargarJornadas(EntityManager em) throws Exception {
        List<JornadaLaboralJson> lista = leerJson(JORNADAS_PATH, JornadaLaboralJson.class);
        for (JornadaLaboralJson json : lista) {
            JornadaLaboral j = new JornadaLaboral();
            j.setId(json.getId());
            j.setDia_semana(json.getDia_semana());
            j.setHoraInicio(json.getHoraInicio());
            j.setHoraFin(json.getHoraFin());
            j.setUser(em.find(Usuario.class,json.getUser().getId()));
            em.persist(j);
        }
        System.out.println("Jornadas insertadas: " + lista.size());
    }

    // ---------- Lectura genérica de JSON ----------
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

    // ---------- POJOs JSON (tipos explícitos) ----------

    public static class UsuarioJson {
        private Long id;
        private String nombreUsuario;
        private String apellidoUsuario;
        private String role_name;
        private String email;
        private String telefono;
        private String password;
        private Boolean status;
        // getters y setters...

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
        private Long id;
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


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

    public static class EspecialidadJson {
        private Long id;
        private String nombreEspecialidad;
        private String descripcion;
        // getters y setters...

        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
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

    public static class TratamientoJson {
        private Long id;
        private String nombreTratamiento;
        private String descripcion;
        private Double duracionEstimado;
        private BigDecimal costo;
        private EspecialidadJson especialidad;
        // getters y setters...

        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
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

        public EspecialidadJson getEspecialidad() {
            return especialidad;
        }

        public void setEspecialidad(EspecialidadJson especialidad) {
            this.especialidad = especialidad;
        }
    }

    public static class CitaJson {
        private Long id;
        private String codigo;
        private LocalDateTime fechaApertura;
        private estado_cita estado_cita;
        private String observacionesCita;
        private PacienteJson paciente;
        private UsuarioJson user;
        private TratamientoJson tratamiento;
        // getters y setters...


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public PacienteJson getPaciente() {
            return paciente;
        }

        public void setPaciente(PacienteJson paciente) {
            this.paciente = paciente;
        }

        public UsuarioJson getUser() {
            return user;
        }

        public void setUser(UsuarioJson user) {
            this.user = user;
        }

        public TratamientoJson getTratamiento() {
            return tratamiento;
        }

        public void setTratamiento(TratamientoJson tratamiento) {
            this.tratamiento = tratamiento;
        }
    }

    public static class RegistroClinicoJson {
        private Long id;
        private String numeroRegistro;
        private tipo_archivo tipo_archivo;
        private String tipo_contenido;
        private String blob_url;
        private String blobName;
        private LocalDateTime fechaCarga;
        private PacienteJson paciente;
        private UsuarioJson user;
        private CitaJson cita;
        // getters y setters...


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNumeroRegistro() {
            return numeroRegistro;
        }

        public void setNumeroRegistro(String numeroRegistro) {
            this.numeroRegistro = numeroRegistro;
        }

        public tipo_archivo getTipo_archivo() {
            return tipo_archivo;
        }

        public void setTipo_archivo(tipo_archivo tipo_archivo) {
            this.tipo_archivo = tipo_archivo;
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

        public PacienteJson getPaciente() {
            return paciente;
        }

        public void setPaciente(PacienteJson paciente) {
            this.paciente = paciente;
        }

        public UsuarioJson getUser() {
            return user;
        }

        public void setUser(UsuarioJson user) {
            this.user = user;
        }

        public CitaJson getCita() {
            return cita;
        }

        public void setCita(CitaJson cita) {
            this.cita = cita;
        }
    }

    public static class DescuentoJson {
        private Long id;
        private String nombrePromocion;
        private String descripcionPromocion;
        private Double descuentoPromocion;
        private Boolean estado;
        // getters y setters...


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
    }

    public static class SeguroJson {
        private Long id;
        private String codigoAseguradora;
        private String nombreAseguradora;
        private Double porcentajeDescuento;
        private Date fechaInicio;
        private BigDecimal deducible;
        private Date fechaFinal;
        private Boolean estado;
        // getters y setters...


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

    public static class FacturacionJson {
        private Long id;
        private LocalDateTime fechaEmision;
        private BigDecimal subtotal;
        private BigDecimal total;
        private estado_pago estado_pago;
        private TratamientoJson tratamiento;
        private PacienteJson paciente;
        private CitaJson cita;
        private DescuentoJson descuento;
        private SeguroJson seguro;
        // getters y setters...


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

        public TratamientoJson getTratamiento() {
            return tratamiento;
        }

        public void setTratamiento(TratamientoJson tratamiento) {
            this.tratamiento = tratamiento;
        }

        public PacienteJson getPaciente() {
            return paciente;
        }

        public void setPaciente(PacienteJson paciente) {
            this.paciente = paciente;
        }

        public CitaJson getCita() {
            return cita;
        }

        public void setCita(CitaJson cita) {
            this.cita = cita;
        }

        public DescuentoJson getDescuento() {
            return descuento;
        }

        public void setDescuento(DescuentoJson descuento) {
            this.descuento = descuento;
        }

        public SeguroJson getSeguro() {
            return seguro;
        }

        public void setSeguro(SeguroJson seguro) {
            this.seguro = seguro;
        }
    }

    public static class JornadaLaboralJson {
        private Long id;
        private dia_semana dia_semana;
        private LocalTime horaInicio;
        private LocalTime horaFin;
        private UsuarioJson user;
        // getters y setters...


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public UsuarioJson getUser() {
            return user;
        }

        public void setUser(UsuarioJson user) {
            this.user = user;
        }
    }
}
