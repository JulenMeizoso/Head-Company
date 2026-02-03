package com.example.mi_api.Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.mi_api.Class.Usuario;
import com.example.mi_api.Utils.Connector;

public class UsuarioRepo {
	
	// CONSULTAS SQL

	// INSERT
    public static void addUsuario(Usuario usuario) throws SQLException {

        String sql = "INSERT INTO users (mail, contra) VALUES (?, ?)";

        try (Connection connection = Connector.getConexion();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getMail());
            pstmt.setString(2, usuario.getContra());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // UPDATE
    public static void updateUsuario(Usuario usuario) throws SQLException {

        String sql = "UPDATE users SET contra = ? WHERE mail = ?";

        try (Connection connection = Connector.getConexion();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getContra());
            
            pstmt.setString(2, usuario.getMail());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // DELETE
    public static void deleteUsuario(String mail) throws SQLException {

        String sql = "DELETE FROM users WHERE mail = ?";

        try (Connection connection = Connector.getConexion();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, mail);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}