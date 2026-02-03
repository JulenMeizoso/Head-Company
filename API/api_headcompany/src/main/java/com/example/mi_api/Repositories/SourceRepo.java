package com.example.mi_api.Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.mi_api.Class.Source;
import com.example.mi_api.Utils.Connector;

public class SourceRepo {

	// CONSULTAS SQL

	// INSERT
	public static void addSource(Source source) throws SQLException {

		String sql = "INSERT INTO sources (source_id, descripcion_es, descripcion_eu) VALUES (?, ?, ?)";

		try (Connection connection = Connector.getConexion();
				PreparedStatement pstmt = connection.prepareStatement(sql)) {

			pstmt.setInt(1, source.getId());
			pstmt.setString(2, source.getDescripcionEs());
			pstmt.setString(3, source.getDescripcionEu());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	// UPDATE
	public static void updateSource(Source source) throws SQLException {

		String sql = "UPDATE sources SET descripcion_es = ?, descripcion_eu = ? WHERE source_id = ?";

		try (Connection connection = Connector.getConexion();
				PreparedStatement pstmt = connection.prepareStatement(sql)) {

			pstmt.setString(1, source.getDescripcionEs());
			pstmt.setString(2, source.getDescripcionEu());

			pstmt.setInt(3, source.getId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	// DELETE
	public static void deleteSource(int sourceId) throws SQLException {

		String sql = "DELETE FROM sources WHERE source_id = ?";

		try (Connection connection = Connector.getConexion();
				PreparedStatement pstmt = connection.prepareStatement(sql)) {

			pstmt.setInt(1, sourceId);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
}