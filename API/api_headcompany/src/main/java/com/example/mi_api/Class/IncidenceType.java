package com.example.mi_api.Class;

import com.fasterxml.jackson.annotation.JsonValue;

// Esto asegura que todas las Incidencias entren dentro de estos tipos, 
// para facilitar la busqueda y gestión de datos

public enum IncidenceType {
    ACCIDENTE("Accidente"),
    OBRAS("Obras"),
    METEOROLOGIA("Meteorología"),
    SEGURIDAD_VIAL("Seguridad vial"),
    RETENCION("Retención"),
    OTROS("Otros");

    private final String textoOriginal;

    IncidenceType(String textoOriginal) {
        this.textoOriginal = textoOriginal;
    }

    
    // Hay que llamar a esta función en vez del Getter original.
    // Si no, devolverá el nombre en MAYUS
    @JsonValue 
    public String getTextoOriginal() {
        return this.textoOriginal;
    }

    public static IncidenceType fromString(String text) {
        if (text == null || text.trim().isEmpty()) {
            return OTROS;
        }
        
        for (IncidenceType tipo : IncidenceType.values()) {
            if (tipo.textoOriginal.equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        return OTROS;
    }
}