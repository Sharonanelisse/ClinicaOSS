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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class CargarDesdeJsonD {

    private static final String PERSISTENCE_UNIT = "clinicaossPU";

    private static final String TRATAMIENTOS_PATH = "data/Tratamiento.json";
    private static final String CITAS_PATH = "data/Cita.json";
    private static final String REGISTROS_PATH = "data/RegistroClinico.json";
    private static final String FACTURACIONES_PATH = "data/Facturacion.json";
    private static final String JORNADAS_PATH = "data/JornadaLaboral.json";

    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();

        try {
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
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
            emf.close();
        }
    }


    private static void cargarTratamientos(EntityManager em) throws Exception {
        List<TratamientoJson> lista = leerJson(TRATAMIENTOS_PATH, TratamientoJson.class);
        for (TratamientoJson json : lista) {
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

            em.persist(t);
        }
        System.out.println("Tratamientos insertados: " + lista.size());
    }

    private static void cargarCitas(EntityManager em) throws Exception {
        List<CitaJson> lista = leerJson(CITAS_PATH, CitaJson.class);
        for (CitaJson json : lista) {
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
        List<RegistroClinicoJson> lista = leerJson(REGISTROS_PATH, RegistroClinicoJson.class);
        for (RegistroClinicoJson json : lista) {
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
        List<CargarDesdeJsonD.FacturacionJson> lista = leerJson(FACTURACIONES_PATH, CargarDesdeJsonD.FacturacionJson.class);
        for (CargarDesdeJsonD.FacturacionJson json : lista) {
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
        List<CargarDesdeJsonD.JornadaLaboralJson> lista = leerJson(JORNADAS_PATH, CargarDesdeJsonD.JornadaLaboralJson.class);
        for (CargarDesdeJsonD.JornadaLaboralJson json : lista) {
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

    public static class TratamientoJson {
        private Long tratamientoId;
        private String nombreTratamiento;
        private String descripcion;
        private Double duracionEstimado;
        private BigDecimal costo;
        private Long especialidadId;
        // getters y setters...

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
