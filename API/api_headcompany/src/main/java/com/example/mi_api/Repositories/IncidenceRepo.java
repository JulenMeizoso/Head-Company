package com.example.mi_api.Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.mi_api.Class.Incidence;
import com.example.mi_api.Utils.Connector;

public class IncidenceRepo {

	// CONSULTAS SQL
	
    // INSERT
    public static void addIncidence(Incidence incidence) throws SQLException {

        String sql = "INSERT INTO incidences "
                + "(autonomous_region, car_registration, cause, city_town, direction, "
                + "end_date, incidence_description, incidence_level, incidence_name, incidence_type, "
                + "latitude, longitude, pk_end, pk_start, province, road, source_id, start_date) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Connector.getConexion();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, incidence.getAutonomousRegion());
            pstmt.setString(2, incidence.getCarRegistration());
            pstmt.setString(3, incidence.getCause());
            pstmt.setString(4, incidence.getCityTown());
            pstmt.setString(5, incidence.getDirection());
            pstmt.setString(6, incidence.getEndDate());
            pstmt.setString(7, incidence.getIncidenceDescription());
            pstmt.setString(8, incidence.getIncidenceLevel());
            pstmt.setString(9, incidence.getIncidenceName());
            pstmt.setString(10, incidence.getIncidenceType());
            pstmt.setDouble(11, incidence.getLatitude());
            pstmt.setDouble(12, incidence.getLongitude());
            pstmt.setDouble(13, incidence.getPkEnd());
            pstmt.setDouble(14, incidence.getPkStart());
            pstmt.setString(15, incidence.getProvince());
            pstmt.setString(16, incidence.getRoad());
            pstmt.setInt(17, incidence.getSourceId());
            pstmt.setString(18, incidence.getStartDate());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // UPDATE
    public static void updateIncidence(Incidence incidence) throws SQLException {

        String sql = "UPDATE incidences SET "
                + "autonomous_region = ?, car_registration = ?, cause = ?, city_town = ?, direction = ?, "
                + "end_date = ?, incidence_description = ?, incidence_level = ?, incidence_name = ?, incidence_type = ?, "
                + "latitude = ?, longitude = ?, pk_end = ?, pk_start = ?, province = ?, road = ?, source_id = ?, start_date = ? "
                + "WHERE incidence_id = ?";

        try (Connection connection = Connector.getConexion();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, incidence.getAutonomousRegion());
            pstmt.setString(2, incidence.getCarRegistration());
            pstmt.setString(3, incidence.getCause());
            pstmt.setString(4, incidence.getCityTown());
            pstmt.setString(5, incidence.getDirection());
            pstmt.setString(6, incidence.getEndDate());
            pstmt.setString(7, incidence.getIncidenceDescription());
            pstmt.setString(8, incidence.getIncidenceLevel());
            pstmt.setString(9, incidence.getIncidenceName());
            pstmt.setString(10, incidence.getIncidenceType());
            pstmt.setDouble(11, incidence.getLatitude());
            pstmt.setDouble(12, incidence.getLongitude());
            pstmt.setDouble(13, incidence.getPkEnd());
            pstmt.setDouble(14, incidence.getPkStart());
            pstmt.setString(15, incidence.getProvince());
            pstmt.setString(16, incidence.getRoad());
            pstmt.setInt(17, incidence.getSourceId());
            pstmt.setString(18, incidence.getStartDate());

            pstmt.setInt(19, incidence.getIncidenceId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // DELETE
    public static void deleteIncidence(int incidenceId) throws SQLException {

        String sql = "DELETE FROM incidences WHERE incidence_id = ?";

        try (Connection connection = Connector.getConexion();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, incidenceId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}