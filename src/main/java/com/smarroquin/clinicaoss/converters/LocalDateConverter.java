package com.smarroquin.clinicaoss.converters;

import jakarta.faces.convert.FacesConverter;

@FacesConverter(forClass = LocalDate.class)
public class LocalDateConverter implements Converter<LocalDate> {

    @Override
    public String getAsString(FacesContext context, UIComponent component, LocalDate value) {
        return value != null ? value.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
    }

    @Override
    public LocalDate getAsObject(FacesContext context, UIComponent component, String value) {
        return (value != null && !value.isEmpty())
                ? LocalDate.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                : null;
    }
}

