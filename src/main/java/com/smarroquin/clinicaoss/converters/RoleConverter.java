package com.smarroquin.clinicaoss.converters;

import com.smarroquin.clinicaoss.models.Role;
import com.smarroquin.clinicaoss.service.CatalogService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.FacesConverter;
import jakarta.faces.convert.Converter;
import jakarta.inject.Inject;

@FacesConverter(value = "roleConverter", managed = true)
public class RoleConverter implements Converter {

    @Inject
    private CatalogService catalogService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.trim().isEmpty()) return null;
        try {
            Long id = Long.valueOf(s);
            if (catalogService == null) return null;
            return catalogService.roles().stream()
                    .filter(r -> r.getId() != null && r.getId().equals(id))
                    .findFirst().orElse(null);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o == null) return "";
        if (o instanceof Role) {
            Long id = ((Role) o).getId();
            return id == null ? "" : String.valueOf(id);
        }
        return "";
    }
}

