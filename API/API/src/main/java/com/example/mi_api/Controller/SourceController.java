package com.example.mi_api.Controller;

import org.springframework.web.bind.annotation.*;

import com.example.mi_api.Class.Source;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sources") // URL base: http://localhost:8080/sources
public class SourceController {

    private static List<Source> sourceList = new ArrayList<>();

    // 1. (/sources)
    @GetMapping
    public static List<Source> getAllSources() {
        return sourceList;
    }

    // 2. (/sources/1)
    @GetMapping("/{id}")
    public Source getSourceById(@PathVariable int id) {
        for (Source s : sourceList) {
            if (s.getId() == (id)) { 
                return s;
            }
        }
        return null; // Devuelve vacío si no lo encuentra
    }

    // 3. UPDATE (Método estático para ser llamado desde el actualizador)
    public static void updateSources(List<Source> newSources) {
        sourceList.clear();
        
        if (newSources != null) {
            sourceList.addAll(newSources);
        }        
    }
}