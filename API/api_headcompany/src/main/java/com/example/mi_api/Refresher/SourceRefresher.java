package com.example.mi_api.Refresher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.example.mi_api.Class.Source;
import com.example.mi_api.Controller.SourceController;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class SourceRefresher {

	// Recoge los datos de Euskadi, de nuestra BD, 
	// los combina y actualiza en la memoria local de la API
	
	private static List<Source> hcSourceList = new ArrayList<>();
	private static List<Source> eusSourceList = new ArrayList<>();
	private static List<Source> finalSourceList = new ArrayList<>();

	@SuppressWarnings("deprecation")
	public static void getEusSources() {


		ArrayList<Source> resultList = new ArrayList<>();
		String url = "https://api.euskadi.eus/traffic/v1.0/sources";

		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		try {

			String jsonRespuesta = restTemplate.getForObject(url, String.class);

			JsonNode raiz = mapper.readTree(jsonRespuesta);

			if (raiz.isArray()) {
				for (JsonNode nodo : raiz) {

					int id = nodo.path("id").asInt();
					String descripcionEs = nodo.path("descripcionEs").asText("");
					if (descripcionEs.isEmpty()) {
						descripcionEs = nodo.path("name").asText("");
					}

					String descripcionEu = nodo.path("descripcionEu").asText("");

					Source nuevoSource = new Source(id, descripcionEs, descripcionEu);

					resultList.add(nuevoSource);
				}
			} else {
				System.out.println("ERROR: Se esperaba un Array [ ] pero llegó un Objeto { }");
			}

		} catch (Exception e) {
			System.out.println("Error procesando Sources: " + e.getMessage());
			e.printStackTrace();
		}

		eusSourceList = resultList;
	}

	public static void getHcSources(Connection connection) {

		String query = "SELECT * FROM sources";

		try (PreparedStatement pst = connection.prepareStatement(query); ResultSet rs = pst.executeQuery()) {

			hcSourceList.clear();

			while (rs.next()) {
				int sourceId = rs.getInt("source_id");
				String descripcionEs = rs.getString("descripcion_es");
				String descripcionEu = rs.getString("descripcion_eu");

				Source source = new Source(sourceId, descripcionEs, descripcionEu);

				hcSourceList.add(source);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void mergeSourceLists() {
		finalSourceList = new ArrayList<>(eusSourceList);

		if (hcSourceList != null) {
			for (Source hcSource : hcSourceList) {
				finalSourceList.add(hcSource);
			}
		}
	}

	public static void refreshSources(Connection connection) {
		getEusSources();
		getHcSources(connection);
		mergeSourceLists();
		SourceController.updateSources(finalSourceList);
	}
}