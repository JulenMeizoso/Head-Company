package com.example.mi_api.Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mi_api.Class.ItemContainer;
import com.example.mi_api.Class.Usuario;
import com.example.mi_api.Repositories.UsuarioRepo;
import com.example.mi_api.Utils.PasswordAuthentication;

@RestController
@RequestMapping("/users")	//Dirección URL
public class UsuarioController {

	PasswordAuthentication auth = new PasswordAuthentication();

	private static List<Usuario> listaUsuarios = new ArrayList<>();

	public static List<Usuario> getAllUsuarios() {
		return listaUsuarios;
	}

	// CREATE (POST)
		@PostMapping("/create")
		public ResponseEntity<String> createUsuario(@RequestBody Usuario nuevoUsuario) {

			if (nuevoUsuario.getMail() == null || nuevoUsuario.getMail().isEmpty()) {
				return ResponseEntity.badRequest().body("El nombre de usuario es obligatorio.");
			}
			if (nuevoUsuario.getContra() == null || nuevoUsuario.getContra().isEmpty()) {
				return ResponseEntity.badRequest().body("La contraseña es obligatoria.");
			}

			String passwordHasheada = auth.hash(nuevoUsuario.getContra().toCharArray());
			nuevoUsuario.setContra(passwordHasheada);

			try {
				UsuarioRepo.addUsuario(nuevoUsuario);
				listaUsuarios.add(nuevoUsuario);

				System.out.println("Se ha añadido un usuario");
				
				return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado con éxito.");

			} catch (SQLException e) {
				e.printStackTrace();
				
				if (e.getErrorCode() == 1062) { // 1062 = Duplicate Entry en MySQL
					return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe.");
				}

				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error SQL: " + e.getMessage());
			}
		}

	// BUSCAR PAGINA
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping
	public ItemContainer<?> getPagedUsuarios(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "20") int size) {
		int totalItems = listaUsuarios.size();
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
		List<Usuario> paginaActual = listaUsuarios.subList(fromIndex, toIndex);

		return new ItemContainer(totalItems, totalPages, page, paginaActual);
	}

	// LOGIN
	@PostMapping("/login")
	public ResponseEntity<Boolean> loginUsuario(@RequestBody Usuario user) {

	    for (Usuario u : listaUsuarios) {
	        if (u.getMail().equals(user.getMail())) {
	            boolean valid = auth.authenticate(user.getContra().toCharArray(), u.getContra());
	            return ResponseEntity.ok(valid);
	        }
	    }
	    return ResponseEntity.ok(false);
	}

	// ACTUALIZAR MEMORIA LOCAL (API)
	public static void updateUsuarios(List<Usuario> nuevosUsuarios) {
		listaUsuarios.clear();

		if (nuevosUsuarios != null) {
			listaUsuarios.addAll(nuevosUsuarios);
		}
	}
}