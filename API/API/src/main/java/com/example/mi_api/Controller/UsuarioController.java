package com.example.mi_api.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mi_api.Class.ItemContainer;
import com.example.mi_api.Class.Usuario;
import com.example.mi_api.Utils.PasswordAuthentication;

@RestController // 1. Indica que esta clase maneja peticiones web
@RequestMapping("/users") // 2. La ruta base será http://localhost:8080/users
public class UsuarioController {

	PasswordAuthentication auth = new PasswordAuthentication();

	private static List<Usuario> listaUsuarios = new ArrayList<>();

		//Hashear contraseñas
		//"ana@test.com", auth.hash("ana1234".toCharArray());

    public static List<Usuario> getAllUsuarios() {
        return listaUsuarios;
    }
    
    // 2. (/usuarios?page=1&size=20)
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @GetMapping
    public ItemContainer<?> getPagedUsuarios(
            @RequestParam(defaultValue = "1") int page, 
            @RequestParam(defaultValue = "20") int size
    ) {
        // 1. Obtenemos el total de elementos reales
        int totalItems = listaUsuarios.size();

        // 2. Calculamos el total de páginas
        int totalPages = (int) Math.ceil((double) totalItems / size);

        // 3. Control de límites: Creamos el contenedor vacío por defecto
        ItemContainer itemContainer = new ItemContainer(totalItems, totalPages, page, new ArrayList<>());
        
        // Si piden una página que no existe (ej: página 5 cuando solo hay 2), devolvemos vacío
        if (page > totalPages && totalPages > 0) {
            return itemContainer;
        }

        // 4. Calcular índices de corte (Slice) para la sublista
        int fromIndex = (page - 1) * size;
        
        // Si el índice inicial está fuera de rango, devolvemos vacío
        if (fromIndex < 0 || fromIndex >= totalItems) {
             return itemContainer;
        }

        // El índice final no puede superar el tamaño total de la lista
        int toIndex = Math.min(fromIndex + size, totalItems);

        // 5. Cortamos la lista original para obtener solo los de esta página
        List<Usuario> paginaActual = listaUsuarios.subList(fromIndex, toIndex);

        // 6. Devolvemos la "Caja" con los datos de la página
        return new ItemContainer(totalItems, totalPages, page, paginaActual);
    }

    public static void updateUsuarios(List<Usuario> nuevosUsuarios) {
        listaUsuarios.clear();
        
        if (nuevosUsuarios != null) {
            listaUsuarios.addAll(nuevosUsuarios);
        }        
    }
    }