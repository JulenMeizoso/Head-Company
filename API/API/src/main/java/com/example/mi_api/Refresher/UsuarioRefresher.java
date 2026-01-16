package com.example.mi_api.Refresher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.mi_api.Class.Usuario;
import com.example.mi_api.Controller.UsuarioController;
import com.example.mi_api.Utils.PasswordAuthentication;

public class UsuarioRefresher {
    
    private static List<Usuario> UsuarioList = new ArrayList<>();
    
    public static void getUsuarios(Connection connection) {
    	
    	PasswordAuthentication auth = new PasswordAuthentication();
    	
		String query = "SELECT * FROM users";

		try (PreparedStatement pst = connection.prepareStatement(query); ResultSet rs = pst.executeQuery()) {

			UsuarioList.clear();

			while (rs.next()) {
				String mail = rs.getString("mail");
				String contra = rs.getString("contra");
				contra = auth.hash(contra.toCharArray());
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