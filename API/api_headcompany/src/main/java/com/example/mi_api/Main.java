package com.example.mi_api;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.mi_api.Utils.ApiRefresher;
import com.example.mi_api.Utils.Connector;
import com.example.mi_api.Utils.InicializarBD;

@SpringBootApplication
@EnableScheduling
public class Main {
	
	public static void main(String[] args) {
		try {
			
			// Inicializar BD
			Connection connection = Connector.getConexion();
			InicializarBD.InsertarDatosBD(connection);
			connection.close();
			
			// Activar servidor
			SpringApplication.run(Main.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


// TAREA DE ACTUALIZACIÓN
// Se ejecuta cada X minutos y actualiza los datos de la API
@Component
class ScheduledTasks {

	public static final int MINUTOS = 4;
	
	@Scheduled(fixedRate = MINUTOS * 60 * 1000)
	public void scheduleTaskWithFixedRate() {

		try (Connection connection = Connector.getConexion()) {
			if (connection != null) {
				ApiRefresher.refreshAll(connection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}