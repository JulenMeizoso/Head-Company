package com.example.mi_api.Utils;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.mi_api.Refresher.CameraRefresher;
import com.example.mi_api.Refresher.IncidenceRefresher;
import com.example.mi_api.Refresher.SourceRefresher;
import com.example.mi_api.Refresher.UsuarioRefresher;

public class ApiRefresher {
	
	static DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	
	
	// Ejecuta todas las actualizaciones, e imprime un log en consola
	public static void refreshAll(Connection connection) {
		
		System.out.println("------------------------------");
		System.out.println("Refrescando datos de la API...");
		
		CameraRefresher.refreshCameras(connection);
		IncidenceRefresher.refreshIncidences(connection);
		SourceRefresher.refreshSources(connection);
		UsuarioRefresher.refreshUsuraios(connection);
		
		System.out.println("API actualizada correctamente");
		System.out.println(LocalDateTime.now().format(formateador));
		System.out.println("------------------------------");
	}
}
