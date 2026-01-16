package com.example.mi_api.Controller;

import org.springframework.web.bind.annotation.*;

import com.example.mi_api.Class.Camera;
import com.example.mi_api.Class.ItemContainer;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cameras")
public class CameraController {

    private static List<Camera> cameraList = new ArrayList<>();

    public static List<Camera> getAllCameras() {
        return cameraList;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @GetMapping
    public ItemContainer<?> getPagedCameras(
            @RequestParam(defaultValue = "1") int page, 
            @RequestParam(defaultValue = "20") int size
    ) {
        int totalItems = cameraList.size();

        int totalPages = (int) Math.ceil((double) totalItems / size);

        ItemContainer itemContainer = new ItemContainer(totalItems, totalPages, page, new ArrayList<>());
        if (page > totalPages && totalPages > 0) {
            return itemContainer;
        }

        int fromIndex = (page - 1) * size;
        
        if (fromIndex < 0 || fromIndex >= totalItems) {
             return itemContainer;
        }

        int toIndex = Math.min(fromIndex + size, totalItems);

        List<Camera> paginaActual = cameraList.subList(fromIndex, toIndex);

        return new ItemContainer(totalItems, totalPages, page, paginaActual);
    }

    @GetMapping("/{sourceId}")
    public List<Camera> getCamerasBySource(@PathVariable int sourceId) {
        List<Camera> filteredList = new ArrayList<>();
        for (Camera c : cameraList) {
            if (c.getSourceId() == sourceId) {
                filteredList.add(c);
            }
        }
        return filteredList;
    }

    public static void updateSources(ArrayList<Camera> nuevasCamaras) {
        cameraList.clear();
        
        if (nuevasCamaras != null) {
            cameraList.addAll(nuevasCamaras);
        }        
    }
}