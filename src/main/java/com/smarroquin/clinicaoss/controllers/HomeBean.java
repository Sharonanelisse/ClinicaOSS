package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.Facturacion;
import com.smarroquin.clinicaoss.service.CatalogService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.optionconfig.title.Title;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class HomeBean implements Serializable {

    @Inject
    private CatalogService service;

    // --- CARDS (Variables para las tarjetas) ---
    private Long pacientesNuevos;
    private Long citasHoy;
    private BigDecimal ingresosMes;
    private Long tratamientosSemana;
    private Long canceladasHoy;
    private Long odontologosActivos;

    // --- CHART MODELS ---
    private BarChartModel ingresosChart;
    private DonutChartModel tratamientosChart;
    private BarChartModel doctoresChart;

    @PostConstruct
    public void init() {
        cargarCards();
        createIngresosChart();
        createTratamientosChart();
        createDoctoresChart();
    }

    private void cargarCards() {
        this.pacientesNuevos = service.countPacientesSemana();
        this.citasHoy = service.countCitasHoy();
        this.ingresosMes = service.sumIngresosMes();
        this.tratamientosSemana = service.countTratamientosSemana();
        this.canceladasHoy = service.countCanceladasHoy();
        this.odontologosActivos = service.countOdontologosMes();
    }

    // 1. GRÁFICA DE BARRAS: Ingresos Diarios del Mes
    public void createIngresosChart() {
        ingresosChart = new BarChartModel();
        ChartData data = new ChartData();

        List<Facturacion> facturas = service.getFacturasMesActual();

        // Agrupar por día (Java Streams)
        Map<Integer, BigDecimal> porDia = facturas.stream()
                .collect(Collectors.groupingBy(
                        f -> f.getFechaEmision().getDayOfMonth(),
                        Collectors.reducing(BigDecimal.ZERO, Facturacion::getTotal, BigDecimal::add)
                ));

        BarChartDataSet dataSet = new BarChartDataSet();
        dataSet.setLabel("Ingresos (Quetzales)");

        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<String> bgColors = new ArrayList<>();

        // Llenar datos para todos los días hasta hoy
        int hoy = LocalDate.now().getDayOfMonth();
        for (int i = 1; i <= hoy; i++) {
            labels.add(String.valueOf(i));
            values.add(porDia.getOrDefault(i, BigDecimal.ZERO));
            bgColors.add("rgba(75, 192, 192, 0.2)"); // Color verde suave
        }

        dataSet.setData(values);
        dataSet.setBackgroundColor(bgColors);
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setBorderWidth(1);

        data.addChartDataSet(dataSet);
        data.setLabels(labels);
        ingresosChart.setData(data);

        // Opciones visuales
        BarChartOptions options = new BarChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Ingresos Diarios - " + LocalDate.now().getMonth());
        options.setTitle(title);
        ingresosChart.setOptions(options);
    }

    // 2. GRÁFICA DE DONA: Tratamientos más populares
    public void createTratamientosChart() {
        tratamientosChart = new DonutChartModel();
        ChartData data = new ChartData();

        List<Object[]> resultados = service.getTratamientosPopularesMes();

        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<String> bgColors = new ArrayList<>();

        // Colores predefinidos para la dona
        String[] colors = {"#FF6384", "#36A2EB", "#FFCE56", "#4BC0C0", "#9966FF", "#FF9F40"};
        int i = 0;

        for (Object[] obj : resultados) {
            String tratamiento = (String) obj[0];
            Long cantidad = (Long) obj[1];

            labels.add(tratamiento);
            values.add(cantidad);
            bgColors.add(colors[i % colors.length]);
            i++;
        }

        dataSet.setData(values);
        dataSet.setBackgroundColor(bgColors);
        data.addChartDataSet(dataSet);
        data.setLabels(labels);
        tratamientosChart.setData(data);
    }

    // 3. GRÁFICA BARRAS: Pacientes por Doctor
    public void createDoctoresChart() {
        doctoresChart = new BarChartModel();
        ChartData data = new ChartData();

        List<Object[]> resultados = service.getCitasPorDoctorMes();

        BarChartDataSet dataSet = new BarChartDataSet();
        dataSet.setLabel("Citas Atendidas");

        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<String> bgColors = new ArrayList<>();

        for (Object[] obj : resultados) {
            labels.add((String) obj[0]); // Nombre doctor
            values.add((Long) obj[1]);   // Cantidad
            bgColors.add("rgba(54, 162, 235, 0.5)"); // Azul
        }

        dataSet.setData(values);
        dataSet.setBackgroundColor(bgColors);
        dataSet.setBorderColor("rgb(54, 162, 235)");
        dataSet.setBorderWidth(1);

        data.addChartDataSet(dataSet);
        data.setLabels(labels);
        doctoresChart.setData(data);

        // Configurar para que sea horizontal (Opcional, requiere configuración extra en JS, lo dejamos vertical simple por ahora)
    }

    // Getters
    public Long getPacientesNuevos() { return pacientesNuevos; }
    public Long getCitasHoy() { return citasHoy; }
    public BigDecimal getIngresosMes() { return ingresosMes; }
    public Long getTratamientosSemana() { return tratamientosSemana; }
    public Long getCanceladasHoy() { return canceladasHoy; }
    public Long getOdontologosActivos() { return odontologosActivos; }
    public BarChartModel getIngresosChart() { return ingresosChart; }
    public DonutChartModel getTratamientosChart() { return tratamientosChart; }
    public BarChartModel getDoctoresChart() { return doctoresChart; }
}