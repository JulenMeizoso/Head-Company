package com.example.mi_api.Refresher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.web.client.RestTemplate;

import com.example.mi_api.Class.Incidence;
import com.example.mi_api.Controller.IncidenceController;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class IncidenceRefresher {

	// Nombre de nuestra Source. Sirve para identificar nuestros objetos
	private static ArrayList<Incidence> hcIncidenceList = new ArrayList<>();
	private static ArrayList<Incidence> eusIncidenceList = new ArrayList<>();
	private static ArrayList<Incidence> finalIncidenceList = new ArrayList<>();

	@SuppressWarnings("deprecation")
	public static void getEusIncidences() {
		hcIncidenceList = (ArrayList<Incidence>) IncidenceController.getAllIncidences();

		ArrayList<Incidence> resultList = new ArrayList<>();
		String baseUrl = "https://api.euskadi.eus/traffic/v1.0/incidences";
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		int page = 1;
		boolean hasData = true;
		int maxIncidences = 100;

		try {
			while (hasData && resultList.size() < maxIncidences) {
				String url = baseUrl + "?_page=" + page + "&_elements=50";

				String jsonRespuesta = restTemplate.getForObject(url, String.class);

				JsonNode raiz = mapper.readTree(jsonRespuesta);
				JsonNode nodoIncidencias = raiz.path("incidences");

				if (nodoIncidencias.isMissingNode() || nodoIncidencias.isEmpty()) {
					hasData = false;
					break;
				}

				if (nodoIncidencias.isArray()) {
					for (JsonNode nodo : nodoIncidencias) {

						// 1. Extraemos cada campo
						String autonomousRegion = nodo.path("autonomousRegion").asText("");
						String carRegistration = nodo.path("carRegistration").asText("");
						String cause = nodo.path("cause").asText("");
						String cityTown = nodo.path("cityTown").asText("");
						String direction = nodo.path("direction").asText("");
						String endDate = nodo.path("endDate").asText("");
						String description = nodo.path("incidenceDescription").asText("");
						int id = nodo.path("incidenceId").asInt();
						String level = nodo.path("incidenceLevel").asText("");
						String name = nodo.path("incidenceName").asText("");
						String type = nodo.path("incidenceType").asText("");
						double lat = nodo.path("latitude").asDouble();
						double lon = nodo.path("longitude").asDouble();
						double pkEnd = nodo.path("pkEnd").asDouble();
						double pkStart = nodo.path("pkStart").asDouble();
						String province = nodo.path("province").asText("");
						String road = nodo.path("road").asText("");
						int source = nodo.path("sourceId").asInt();
						String startDate = nodo.path("startDate").asText("");

						// 2. Creamos el objeto
						Incidence nuevaIncidencia = new Incidence(autonomousRegion, carRegistration, cause, cityTown,
								direction, endDate, description, id, level, name, type, lat, lon, pkEnd, pkStart,
								province, road, source, startDate);

						// 3. Añadimos a la lista
						resultList.add(nuevaIncidencia);

						if (resultList.size() >= maxIncidences) {
							hasData = false; // Detiene el bucle while principal
							break; // Detiene el bucle for actual
						}
					}
				}
				page++;
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

		eusIncidenceList = resultList;
	}

	public static void getHcIncidences(Connection connection) {

		String query = "SELECT * FROM incidences";

		try (PreparedStatement pst = connection.prepareStatement(query); ResultSet rs = pst.executeQuery()) {

			hcIncidenceList.clear();

			while (rs.next()) {
				String autonomousRegion = rs.getString("autonomous_region");
				String carRegistration = rs.getString("car_registration");
				String cause = rs.getString("cause");
				String cityTown = rs.getString("city_town");
				String direction = rs.getString("direction");
				String endDate = rs.getString("end_date");
				String incidenceDescription = rs.getString("incidence_description");
				int incidenceId = rs.getInt("incidence_id");
				String incidenceLevel = rs.getString("incidence_level");
				String incidenceName = rs.getString("incidence_name");
				String incidenceType = rs.getString("incidence_type");
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				double pkEnd = rs.getDouble("pk_end");
				double pkStart = rs.getDouble("pk_start");
				String province = rs.getString("province");
				String road = rs.getString("road");
				int sourceId = rs.getInt("source_id");
				String startDate = rs.getString("start_date");

				Incidence inc = new Incidence(autonomousRegion, carRegistration, cause, cityTown, direction, endDate,
						incidenceDescription, incidenceId, incidenceLevel, incidenceName, incidenceType, latitude,
						longitude, pkEnd, pkStart, province, road, sourceId, startDate);

				hcIncidenceList.add(inc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void mergeIncidenceLists() {
		finalIncidenceList = eusIncidenceList;
		for (Incidence hcIncidence : hcIncidenceList) {
			finalIncidenceList.add(hcIncidence);
		}
	}

	public static void refreshIncidences(Connection connection) {
		getEusIncidences();
		getHcIncidences(connection);
		mergeIncidenceLists();
		IncidenceController.updateIncidences(finalIncidenceList);
	}

}
