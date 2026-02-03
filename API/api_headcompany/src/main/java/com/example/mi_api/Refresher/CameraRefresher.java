package com.example.mi_api.Refresher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.web.client.RestTemplate;

import com.example.mi_api.Class.Camera;
import com.example.mi_api.Controller.CameraController;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class CameraRefresher {

	// Recoge los datos de Euskadi, de nuestra BD, 
	// los combina y actualiza en la memoria local de la API
	
	private static ArrayList<Camera> hcCameraList = new ArrayList<>();
	private static ArrayList<Camera> eusCameraList = new ArrayList<>();
	private static ArrayList<Camera> finalCameraList = new ArrayList<>();

	@SuppressWarnings("deprecation")
	public static void getEusCameras() {

		ArrayList<Camera> resultList = new ArrayList<>();
		String baseUrl = "https://api.euskadi.eus/traffic/v1.0/cameras";
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		int page = 1;
		boolean hasData = true;

		try {
			while (hasData) {
				String url = baseUrl + "?_page=" + page + "&_elements=50";
				String jsonRespuesta = restTemplate.getForObject(url, String.class);

				JsonNode raiz = mapper.readTree(jsonRespuesta);
				JsonNode nodoCamaras = raiz.path("cameras");

				if (nodoCamaras.isMissingNode() || nodoCamaras.isEmpty()) {
					hasData = false;
					break;
				}

				if (nodoCamaras.isArray()) {
					for (JsonNode nodo : nodoCamaras) {

						String urlImg = nodo.path("urlImage").asText("");

						if (urlImg != null && !urlImg.isEmpty() && !urlImg.contains("www.bilbao.eus")
								&& !urlImg.contains("www.trafikoa.eus") && !urlImg.contains("www.trafikoa.net")
								&& !urlImg.contains("/imagenes/trafico")) {

							int id = nodo.path("cameraId").asInt();
							String name = nodo.path("cameraName").asText("");
							double lat = nodo.path("latitude").asDouble();
							double lon = nodo.path("longitude").asDouble();
							String road = nodo.path("road").asText("");
							String km = nodo.path("kilometer").asText("");
							int source = nodo.path("sourceId").asInt();
							String address = nodo.path("address").asText("");

							Camera nuevaCamara = new Camera(address, id, name, km, lat, lon, road, source, urlImg);

							resultList.add(nuevaCamara);
						}
					}
				}
				page++;
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

		eusCameraList = resultList;
	}

	public static void getHcCameras(Connection connection) {

		String query = "SELECT * FROM cameras";

		try (PreparedStatement pst = connection.prepareStatement(query); ResultSet rs = pst.executeQuery()) {

			hcCameraList.clear();

			while (rs.next()) {
				String address = rs.getString("address");
				int cameraId = rs.getInt("camera_id");
				String cameraName = rs.getString("camera_name");
				String kilometer = rs.getString("kilometer");

				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");

				String road = rs.getString("road");
				int sourceId = rs.getInt("source_id");
				String urlImage = rs.getString("url_image");

				Camera cam = new Camera(address, cameraId, cameraName, kilometer, latitude, longitude, road, sourceId,
						urlImage);

				hcCameraList.add(cam);
			}

		} catch (SQLException e) {
			System.err.println("Error al recuperar las cámaras.");
			e.printStackTrace();
		}
	}

	public static void mergeCameraLists() {
		finalCameraList = eusCameraList;
		for (Camera hcCamera : hcCameraList) {
			finalCameraList.add(hcCamera);
		}
	}

	public static void refreshCameras(Connection connection) {
		getEusCameras();
		getHcCameras(connection);
		mergeCameraLists();
		CameraController.updateSources(finalCameraList);
	}

}
