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

@SpringBootApplication
@EnableScheduling
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}

@Component
class ScheduledTasks {

	@Scheduled(fixedRate = 240000)
	public void scheduleTaskWithFixedRate() {
		System.out.println("Ejecutando ApiRefresher programado...");
		
		try (Connection connection = Connector.getConexion()) {
			if (connection != null) {
				ApiRefresher.refreshAll(connection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}