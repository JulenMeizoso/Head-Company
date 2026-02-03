package com.example.mi_api.Controller;

import org.springframework.web.bind.annotation.*;

import com.example.mi_api.Class.Source;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sources")	//Dirección URL
public class SourceController {

    private static List<Source> sourceList = new ArrayList<>();

    @GetMapping
    public static List<Source> getAllSources() {
        return sourceList;
    }

    // BUSCAR ID
    @GetMapping("/{id}")
    public Source getSourceById(@PathVariable int id) {
        for (Source s : sourceList) {
            if (s.getId() == (id)) { 
                return s;
            }
        }
        return null;
    }

    // ACTUALIZAR MEMORIA LOCAL (API)
    public static void updateSources(List<Source> newSources) {
        sourceList.clear();
        
        if (newSources != null) {
            sourceList.addAll(newSources);
        }        
    }
}