package com.example.mi_api.Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mi_api.Class.Camera;
import com.example.mi_api.Class.ItemContainer;
import com.example.mi_api.Repositories.CameraRepo;

@RestController
@RequestMapping("/cameras")	//Dirección URL
public class CameraController {

	private static List<Camera> cameraList = new ArrayList<>();

	public static List<Camera> getAllCameras() {
		return cameraList;
	}

	// CREATE (POST)
	@PostMapping
	public ResponseEntity<Camera> createCamera(@RequestBody Camera camera) {
		
		camera.setSourceId(67);

		// Ejecución
		try {
			CameraRepo.addCamera(camera);
			cameraList.add(camera);
			System.out.println("Se ha añadido una cámara");

			return new ResponseEntity<>(camera, HttpStatus.CREATED);

		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	// UPDATE (PUT)
	@PutMapping("/{sourceId}/{cameraId}")
	public ResponseEntity<Camera> updateCamera(@PathVariable int sourceId, @PathVariable int cameraId,
			@RequestBody Camera cameraDetails) {

		Camera cameraToUpdate = null;

		// Comprobación
		for (Camera c : cameraList) {
			if (c.getCameraId() == cameraId && c.getSourceId() == sourceId) {
				cameraToUpdate = c;
				break;
			}
		}

		if (cameraToUpdate == null) {
			return ResponseEntity.notFound().build();
		}

		// Ejecución
		try {
			CameraRepo.updateCamera(cameraDetails);

			cameraToUpdate.setAddress(cameraDetails.getAddress());
			cameraToUpdate.setCameraName(cameraDetails.getCameraName());
			cameraToUpdate.setKilometer(cameraDetails.getKilometer());
			cameraToUpdate.setLatitude(cameraDetails.getLatitude());
			cameraToUpdate.setLongitude(cameraDetails.getLongitude());
			cameraToUpdate.setRoad(cameraDetails.getRoad());
			cameraToUpdate.setUrlImage(cameraDetails.getUrlImage());

			System.out.println("Se ha editado una cámara.");

			return ResponseEntity.ok(cameraToUpdate);

		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}

	}

	// DELETE
	@DeleteMapping("/{sourceId}/{cameraId}")
	public ResponseEntity<Void> deleteCamera(@PathVariable int sourceId, @PathVariable int cameraId) {

		try {
			// Comprobación
			boolean removed = cameraList.removeIf(c -> c.getCameraId() == cameraId && c.getSourceId() == sourceId);
			
			// Ejecución
			CameraRepo.deleteCamera(sourceId, cameraId);

			if (removed) {
				System.out.println("Se ha eliminado una cámara");
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.notFound().build();
			}
			
		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}

	}

	// BUSCAR PAGINA
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping
	public ItemContainer<?> getPagedCameras(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "20") int size) {
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

	// BUSCAR SOURCE
	@GetMapping("/source/{sourceId}")
	public List<Camera> getCamerasBySource(@PathVariable int sourceId) {
		List<Camera> filteredList = new ArrayList<>();
		for (Camera c : cameraList) {
			if (c.getSourceId() == sourceId) {
				filteredList.add(c);
			}
		}
		return filteredList;
	}

	// BUSCAR NOMBRE
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Camera>> getCamerasByName(@PathVariable String name) {
		List<Camera> filteredList = new ArrayList<>();

		String searchLower = name.toLowerCase();

		for (Camera c : cameraList) {
			if (c.getCameraName() != null) {

				String cameraNameLower = c.getCameraName().toLowerCase();

				if (cameraNameLower.contains(searchLower)) {
					filteredList.add(c);
				}
			}
		}

		return ResponseEntity.ok(filteredList);
	}

	// ACTUALIZAR MEMORIA LOCAL (API)
	public static void updateSources(ArrayList<Camera> nuevasCamaras) {
		cameraList.clear();

		if (nuevasCamaras != null) {
			cameraList.addAll(nuevasCamaras);
		}
	}
}