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

import com.example.mi_api.Class.Incidence;
import com.example.mi_api.Class.ItemContainer;
import com.example.mi_api.Repositories.IncidenceRepo;

@RestController
@RequestMapping("/incidences")	//Dirección URL
public class IncidenceController {

	private static List<Incidence> incidenceList = new ArrayList<>();

	public static List<Incidence> getAllIncidences() {
		return incidenceList;
	}

	// CREATE (POST)
	@PostMapping
	public ResponseEntity<Incidence> addIncidence(@RequestBody Incidence incidence) {
		incidence.setSourceId(67);

		// Comprobación
		boolean exists = incidenceList.stream().anyMatch(i -> i.getIncidenceId() == incidence.getIncidenceId());

		if (exists) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		// Ejecución
		try {
			IncidenceRepo.addIncidence(incidence);
			incidenceList.add(incidence);
			System.out.println("Se ha añadido una incidencia");

			return new ResponseEntity<>(incidence, HttpStatus.CREATED);

		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}

	}

	// UPDATE (PUT)
	@PutMapping("/{id}")
	public ResponseEntity<Incidence> updateIncidence(@PathVariable int id, @RequestBody Incidence incidenceDetails) {

		Incidence incidenceToUpdate = null;

		// Comprobación
		for (Incidence i : incidenceList) {
			if (i.getIncidenceId() == id) {
				incidenceToUpdate = i;
				break;
			}
		}

		if (incidenceToUpdate == null) {
			return ResponseEntity.notFound().build();
		}
		
		// Ejecución
		try {
			IncidenceRepo.updateIncidence(incidenceDetails);

			incidenceToUpdate.setAutonomousRegion(incidenceDetails.getAutonomousRegion());
			incidenceToUpdate.setCarRegistration(incidenceDetails.getCarRegistration());
			incidenceToUpdate.setCause(incidenceDetails.getCause());
			incidenceToUpdate.setCityTown(incidenceDetails.getCityTown());
			incidenceToUpdate.setDirection(incidenceDetails.getDirection());
			incidenceToUpdate.setEndDate(incidenceDetails.getEndDate());
			incidenceToUpdate.setIncidenceDescription(incidenceDetails.getIncidenceDescription());
			incidenceToUpdate.setIncidenceLevel(incidenceDetails.getIncidenceLevel());
			incidenceToUpdate.setIncidenceName(incidenceDetails.getIncidenceName());
			incidenceToUpdate.setIncidenceType(incidenceDetails.getIncidenceType());
			incidenceToUpdate.setLatitude(incidenceDetails.getLatitude());
			incidenceToUpdate.setLongitude(incidenceDetails.getLongitude());
			incidenceToUpdate.setPkEnd(incidenceDetails.getPkEnd());
			incidenceToUpdate.setPkStart(incidenceDetails.getPkStart());
			incidenceToUpdate.setProvince(incidenceDetails.getProvince());
			incidenceToUpdate.setRoad(incidenceDetails.getRoad());
			incidenceToUpdate.setStartDate(incidenceDetails.getStartDate());

			System.out.println("Se ha editado una incidencia");

			return ResponseEntity.ok(incidenceToUpdate);

		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	// DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteIncidence(@PathVariable int id) {

		try {
			// Comprobación
			boolean removed = incidenceList.removeIf(i -> i.getIncidenceId() == id);
			
			// Ejecución
			IncidenceRepo.deleteIncidence(id);

			if (removed) {
				System.out.println("Se ha eliminado una incidencia");
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.notFound().build();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	// BUSCAR PAGINA
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping
	public ItemContainer<?> getPagedIncidences(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "20") int size) {
		int totalItems = incidenceList.size();
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

		List<Incidence> paginaActual = incidenceList.subList(fromIndex, toIndex);

		return new ItemContainer(totalItems, totalPages, page, paginaActual);
	}

	// BUSCAR ID
	@GetMapping("/{id}")
	public ResponseEntity<Incidence> getIncidenceById(@PathVariable int id) {
		for (Incidence i : incidenceList) {
			if (i.getIncidenceId() == id) {
				return ResponseEntity.ok(i);
			}
		}
		return ResponseEntity.notFound().build();
	}

	// BUSCAR TIPO
	@GetMapping("/type/{textSearch}")
	public ResponseEntity<List<Incidence>> getIncidencesByType(@PathVariable String textSearch) {

		List<Incidence> filteredList = new ArrayList<>();

		String searchLower = textSearch.toLowerCase();

		for (Incidence i : incidenceList) {
			if (i.getIncidenceType() != null) {

				String typeName = i.getIncidenceType().toString().toLowerCase();

				if (typeName.contains(searchLower)) {
					filteredList.add(i);
				}
			}
		}

		return ResponseEntity.ok(filteredList);
	}
	
	// BUSCAR ROAD
		@GetMapping("/road/{textSearch}")
		public ResponseEntity<List<Incidence>> getIncidencesByRoad(@PathVariable String textSearch) {

			List<Incidence> filteredList = new ArrayList<>();

			String searchLower = textSearch.toLowerCase();

			for (Incidence i : incidenceList) {
				if (i.getRoad() != null) {

					String roadName = i.getRoad().toLowerCase();

					if (roadName.contains(searchLower)) {
						filteredList.add(i);
					}
				}
			}

			return ResponseEntity.ok(filteredList);
		}


	// ACTUALIZAR MEMORIA LOCAL (API)
	public static void updateIncidences(ArrayList<Incidence> nuevasIncidencias) {
		incidenceList.clear();

		if (nuevasIncidencias != null) {
			incidenceList.addAll(nuevasIncidencias);
		}
	}
}