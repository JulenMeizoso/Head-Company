package com.example.mi_api.Refresher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.mi_api.Class.Usuario;
import com.example.mi_api.Controller.UsuarioController;

public class UsuarioRefresher {
    
	// Recoge los datos de nuestra BD y los actualiza en la memoria local de la API
	
    private static List<Usuario> UsuarioList = new ArrayList<>();
    
    public static void getUsuarios(Connection connection) {
    	    	
		String query = "SELECT * FROM users";

		try (PreparedStatement pst = connection.prepareStatement(query); ResultSet rs = pst.executeQuery()) {

			UsuarioList.clear();

			while (rs.next()) {
				String mail = rs.getString("mail");
				String contra = rs.getString("contra");
				Usuario user = new Usuario(mail, contra);

				UsuarioList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    public static void refreshUsuraios(Connection connection) {
        getUsuarios(connection);
        UsuarioController.updateUsuarios(UsuarioList);
    }
}