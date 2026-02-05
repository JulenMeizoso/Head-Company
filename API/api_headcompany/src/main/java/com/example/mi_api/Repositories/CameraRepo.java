package com.example.mi_api.Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.mi_api.Class.Camera;
import com.example.mi_api.Utils.Connector;

public class CameraRepo {

	// CONSULTAS SQL
	
	// INSERT
	public static void addCamera(Camera camera) throws SQLException {

		try (Connection connection = Connector.getConexion()) {
			String sql = "INSERT INTO cameras (address, camera_name, kilometer, latitude, longitude, road, source_id, url_image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
				pstmt.setString(1, camera.getAddress());
				pstmt.setString(2, camera.getCameraName());
				pstmt.setString(3, camera.getKilometer());
				pstmt.setDouble(4, camera.getLatitude());
				pstmt.setDouble(5, camera.getLongitude());
				pstmt.setString(6, camera.getRoad());
				pstmt.setInt(7, camera.getSourceId());
				pstmt.setString(8, camera.getUrlImage());

				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error al acceder a la Base de Datos");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// UPDATE
	public static void updateCamera(Camera camera) throws SQLException {

		try (Connection connection = Connector.getConexion()) {
			String sql = "UPDATE cameras SET address = ?, camera_name = ?, kilometer = ?, latitude = ?, longitude = ?, road = ?, url_image = ? WHERE source_id = ? AND camera_id = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
				pstmt.setString(1, camera.getAddress());
				pstmt.setString(2, camera.getCameraName());
				pstmt.setString(3, camera.getKilometer());
				pstmt.setDouble(4, camera.getLatitude());
				pstmt.setDouble(5, camera.getLongitude());
				pstmt.setString(6, camera.getRoad());
				pstmt.setString(7, camera.getUrlImage());

				pstmt.setInt(8, camera.getSourceId());
				pstmt.setInt(9, camera.getCameraId());

				pstmt.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error al acceder a la Base de Datos");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// DELETE
	public static void deleteCamera(int sourceId, int cameraId) throws SQLException {

		try (Connection connection = Connector.getConexion()) {
			String sql = "DELETE FROM cameras WHERE source_id = ? AND camera_id = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
				pstmt.setInt(1, sourceId);
				pstmt.setInt(2, cameraId);

				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error al acceder a la Base de Datos");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}