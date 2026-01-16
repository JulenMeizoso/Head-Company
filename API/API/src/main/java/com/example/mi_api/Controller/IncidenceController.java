package com.example.mi_api.Controller;

import org.springframework.web.bind.annotation.*;

import com.example.mi_api.Class.Incidence;
import com.example.mi_api.Class.ItemContainer;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/incidences") // URL base: http://localhost:8080/incidences
public class IncidenceController {

    private static List<Incidence> incidenceList = new ArrayList<>();

    public static List<Incidence> getAllIncidences() {
        return incidenceList;
    }
    
    // (/incidences?page=1)
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @GetMapping
    public ItemContainer<?> getPagedIncidences(
            @RequestParam(defaultValue = "1") int page, 
            @RequestParam(defaultValue = "20") int size
    ) {
        // 1. Obtenemos el total de elementos reales
        int totalItems = incidenceList.size();

        // 2. Calculamos el total de páginas
        int totalPages = (int) Math.ceil((double) totalItems / size);

        // 3. Control de límites
        // Nota: Pasamos un ArrayList vacío si nos pasamos de página
        ItemContainer itemContainer = new ItemContainer(totalItems, totalPages, page, new ArrayList<>());
        
        if (page > totalPages && totalPages > 0) {
            return itemContainer;
        }

        // 4. Calcular índices de corte (Slice)
        int fromIndex = (page - 1) * size;
        
        // Si fromIndex es negativo o mayor que el tamaño, devolvemos vacío
        if (fromIndex < 0 || fromIndex >= totalItems) {
             return itemContainer;
        }

        int toIndex = Math.min(fromIndex + size, totalItems);

        // 5. Cortamos la lista (Ahora es List<Incidence>)
        List<Incidence> paginaActual = incidenceList.subList(fromIndex, toIndex);

        // 6. Devolvemos la "Caja" con las incidencias
        return new ItemContainer(totalItems, totalPages, page, paginaActual);
    }

    // 2.(/incidences/inc-001)
    @GetMapping("/{id}")
    public Incidence getIncidenceById(@PathVariable int id) {
        for (Incidence i : incidenceList) {
            if (i.getIncidenceId() == (id)) {
                return i;
            }
        }
        return null; // Devuelve null si no la encuentra
    }
    
 // 3. Update
    public static void updateIncidences(ArrayList<Incidence> nuevasIncidencias) {
        incidenceList.clear();
        
        if (nuevasIncidencias != null) {
        	incidenceList.addAll(nuevasIncidencias);
        }        
    }
}