package com.example.mi_api.Controller;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mi_api.Utils.ApiRefresher;
import com.example.mi_api.Utils.Connector;

@RestController
@RequestMapping("/api") 
public class RefreshController {

    @PostMapping("/refresh")
    public ResponseEntity<String> manualRefresh() {
        try (Connection connection = Connector.getConexion()) {
            
            if (connection != null) {
                ApiRefresher.refreshAll(connection);
                return ResponseEntity.ok("Refresco manual completado con éxito.");
            } else {
                return ResponseEntity.internalServerError().body("Error: No se pudo conectar a la BBDD.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error SQL: " + e.getMessage());
        }
    }
}