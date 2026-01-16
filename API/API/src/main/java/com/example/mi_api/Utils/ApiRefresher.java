package com.example.mi_api.Utils;

import java.sql.Connection;

import com.example.mi_api.Refresher.CameraRefresher;
import com.example.mi_api.Refresher.IncidenceRefresher;
import com.example.mi_api.Refresher.SourceRefresher;
import com.example.mi_api.Refresher.UsuarioRefresher;

public class ApiRefresher {

	public static void refreshAll(Connection connection) {
		
		CameraRefresher.refreshCameras(connection);
		IncidenceRefresher.refreshIncidences(connection);
		SourceRefresher.refreshSources(connection);
		UsuarioRefresher.refreshUsuraios(connection);
		
		System.out.println("API actualizada correctamente");
	}
}
