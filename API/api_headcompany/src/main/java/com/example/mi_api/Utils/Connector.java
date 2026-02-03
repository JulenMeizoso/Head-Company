package com.example.mi_api.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
	
    private static final String USER = "HCAPI";
    private static final String PASS = "admin";
	private static final String IP = "localhost";
    private static final String URL = "jdbc:mysql://"+IP+":3306/headcompany";
    
    // GET
    public static Connection getConexion() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASS);            
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de MySQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error: Fallo al conectar con la base de datos.");
            e.printStackTrace();
        }
        
        return con;
    }

    
    // CLOSE
    public static void close(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}