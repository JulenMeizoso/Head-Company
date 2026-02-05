package com.example.mi_api.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;

import com.example.mi_api.Class.Camera;
import com.example.mi_api.Class.Incidence;
import com.example.mi_api.Class.Source;
import com.example.mi_api.Class.Usuario;
import com.example.mi_api.Repositories.CameraRepo;
import com.example.mi_api.Repositories.IncidenceRepo;
import com.example.mi_api.Repositories.SourceRepo;
import com.example.mi_api.Repositories.UsuarioRepo;

public class InicializarBD {

	// Si BD vacía, insertar datos
	public static void InsertarDatosBD(Connection connection) {

		try {
			boolean existenDatos = ComprobarDatosExisten(connection);

			if (!existenDatos) {
				InsertarUsuarios();
				InsertarSources();
				InsertarCameras();
				InsertarIncidences();
				System.out.println("---------------------------------------");
				System.out.println("Se han introducido datos por defecto");
				System.out.println("---------------------------------------\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public static void InsertarUsuarios() throws Exception {
		
		PasswordAuthentication auth = new PasswordAuthentication();
		
		ArrayList<Usuario> listaUsuarios = new ArrayList<>(Arrays.asList(
			    new Usuario("admin@trafficapp.com", "admin123"),
			    new Usuario("juan.perez@gmail.com", "123456"),
			    new Usuario("maria.gonzalez@hotmail.com", "claveSegura"),
			    new Usuario("antonio.ruiz@yahoo.es", "antonio2023"),
			    new Usuario("laura.sanchez@outlook.com", "lau_san_88"),
			    new Usuario("carlos.martinez@gmail.com", "cmartinez"),
			    new Usuario("david.fernandez@protonmail.com", "qwerty"),
			    new Usuario("lucia.jimenez@empresa.net", "lucia.j"),
			    new Usuario("miguel.angel@terra.es", "miguelangel"),
			    new Usuario("elena.moreno@gmail.com", "elena1234"),
			    new Usuario("javier.alvarez@hotmail.com", "javier_alva"),
			    new Usuario("cristina.romero@live.com", "cristina2024"),
			    new Usuario("pablo.diaz@gmail.com", "pablito_d"),
			    new Usuario("raquel.munoz@yahoo.com", "rmunoz90"),
			    new Usuario("sergio.alonso@outlook.es", "sergio_a"),
			    new Usuario("beatriz.navarro@gmail.com", "bea123bea"),
			    new Usuario("fernando.torres@gmail.com", "nino9"),
			    new Usuario("isabel.ruiz@hotmail.com", "isa_ruiz"),
			    new Usuario("ruben.garcia@gmail.com", "ruben_g"),
			    new Usuario("soporte@trafficapp.com", "SoporteTecnico2024")
			));
		
		try {
			
			for(Usuario usuario : listaUsuarios) {
				usuario.setContra(auth.hash(usuario.getContra()));
				UsuarioRepo.addUsuario(usuario);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	public static void InsertarSources() throws Exception {
		ArrayList<Source> listaSources = new ArrayList<>(Arrays.asList(
			    new Source(67, "HeadCompany", "BuruEnpresa")
			));

			try {

				for (Source source : listaSources) {
					SourceRepo.addSource(source);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

	}

	public static void InsertarCameras() throws Exception {
		ArrayList<Camera> listaCameras = new ArrayList<>(Arrays.asList(
			    new Camera("Paseo de la Castellana", 1, "Camara Norte", "12.5", 40.416775, -3.703790, "M-30", 67, "http://ejemplo.com/img1.jpg"),
			    new Camera("Plaza de Castilla", 2, "Acceso Central", "0.5", 40.466120, -3.689310, "Castellana", 67, "http://ejemplo.com/img2.jpg"),
			    new Camera("Nudo Sur", 3, "Enlace A-4", "14.2", 40.380500, -3.680100, "M-30", 67, "http://ejemplo.com/img3.jpg"),
			    new Camera("Puente de Ventas", 4, "Lateral Este", "6.8", 40.430200, -3.663000, "M-30", 67, "http://ejemplo.com/img4.jpg"),
			    new Camera("Acceso A-6", 5, "Moncloa Salida", "3.0", 40.435500, -3.719000, "A-6", 67, "http://ejemplo.com/img5.jpg"),
			    new Camera("Tunel Pardo", 6, "M-40 Norte", "25.4", 40.501000, -3.750000, "M-40", 67, "http://ejemplo.com/img6.jpg"),
			    new Camera("Avenida de America", 7, "Salida A-2", "4.1", 40.438000, -3.676000, "A-2", 67, "http://ejemplo.com/img7.jpg"),
			    new Camera("Puente de Vallecas", 8, "M-30 Sur", "10.5", 40.398000, -3.668000, "M-30", 67, "http://ejemplo.com/img8.jpg"),
			    new Camera("Cuesta de San Vicente", 9, "Principe Pio", "1.2", 40.420000, -3.720000, "Centro", 67, "http://ejemplo.com/img9.jpg"),
			    new Camera("ODonnell", 10, "Torre España", "2.5", 40.422000, -3.655000, "M-23", 67, "http://ejemplo.com/img10.jpg"),
			    new Camera("Nudo Manoteras", 11, "Acceso M-11", "32.1", 40.485000, -3.660000, "M-40", 67, "http://ejemplo.com/img11.jpg"),
			    new Camera("Via Lusitana", 12, "Plaza Eliptica", "3.8", 40.385000, -3.718000, "A-42", 67, "http://ejemplo.com/img12.jpg"),
			    new Camera("Paseo Extremadura", 13, "Alto Extremadura", "5.5", 40.408000, -3.735000, "A-5", 67, "http://ejemplo.com/img13.jpg"),
			    new Camera("Cristo Rey", 14, "Hospital Clinico", "2.0", 40.440000, -3.715000, "A-6", 67, "http://ejemplo.com/img14.jpg"),
			    new Camera("Embajadores", 15, "M-30 Oeste", "16.0", 40.390000, -3.695000, "M-30", 67, "http://ejemplo.com/img15.jpg"),
			    new Camera("Mendez Alvaro", 16, "Estacion Sur", "12.8", 40.395000, -3.678000, "M-30", 67, "http://ejemplo.com/img16.jpg"),
			    new Camera("Sinesio Delgado", 17, "Barrio del Pilar", "1.5", 40.470000, -3.700000, "Urbana", 67, "http://ejemplo.com/img17.jpg"),
			    new Camera("Ramon y Cajal", 18, "Acceso Colmenar", "9.0", 40.488000, -3.690000, "M-607", 67, "http://ejemplo.com/img18.jpg"),
			    new Camera("Cuatro Vientos", 19, "Aerodromo", "8.5", 40.370000, -3.780000, "A-5", 67, "http://ejemplo.com/img19.jpg"),
			    new Camera("Santa Maria Cabeza", 20, "Tunel", "2.2", 40.400000, -3.705000, "A-42", 67, "http://ejemplo.com/img20.jpg")
			));

		try {

			for (Camera camera: listaCameras) {
				CameraRepo.addCamera(camera);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void InsertarIncidences() throws Exception {
		ArrayList <Incidence> listaIncidences = new ArrayList<>(Arrays.asList(
			    new Incidence("Comunidad de Madrid", "", "Accidente", "Madrid", "Salida", "2023-12-01 12:00:00", "Accidente por alcance en carril central.", 101, "Rojo", "Accidente M-30", "Accidente", 40.416775, -3.703790, 14.500, 14.200, "Madrid", "M-30", 67, "2023-12-01 10:30:00"),
			    new Incidence("Comunidad de Madrid", "", "Obras", "Las Rozas", "Entrada", "2023-12-15 18:00:00", "Corte de carril derecho por asfaltado.", 102, "Amarillo", "Obras A-6", "Obras", 40.490000, -3.870000, 19.500, 18.000, "Madrid", "A-6", 67, "2023-12-10 08:00:00"),
			    new Incidence("Comunidad de Madrid", "", "Avería", "Getafe", "Ambos", "2023-12-01 11:00:00", "Vehículo averiado en arcén.", 103, "Verde", "Avería A-42", "Otros", 40.300000, -3.730000, 12.100, 12.100, "Madrid", "A-42", 67, "2023-12-01 09:45:00"),
			    new Incidence("Comunidad de Madrid", "", "Meteorología", "Somosierra", "Salida", "2023-12-02 10:00:00", "Bancos de niebla densa. Visibilidad reducida.", 104, "Rojo", "Niebla A-1", "Meteorología", 41.100000, -3.580000, 95.000, 80.000, "Madrid", "A-1", 67, "2023-12-02 06:00:00"),
			    new Incidence("Comunidad de Madrid", "", "Retención", "Alcobendas", "Entrada", "2023-12-01 09:30:00", "Tráfico lento por hora punta.", 105, "Amarillo", "Retención A-1", "Retención", 40.540000, -3.640000, 16.000, 12.000, "Madrid", "A-1", 67, "2023-12-01 07:30:00"),
			    new Incidence("Comunidad de Madrid", "", "Accidente", "Vallecas", "Interior", "2023-12-01 13:00:00", "Colisión múltiple, dos carriles cortados.", 106, "Negro", "Accidente M-40", "Accidente", 40.380000, -3.650000, 22.500, 22.000, "Madrid", "M-40", 67, "2023-12-01 11:15:00"),
			    new Incidence("Comunidad de Madrid", "", "Obras", "Pozuelo", "Exterior", "2024-01-01 00:00:00", "Obras de mejora en enlaces.", 107, "Verde", "Obras M-40", "Obras", 40.420000, -3.800000, 45.000, 44.000, "Madrid", "M-40", 67, "2023-11-20 09:00:00"),
			    new Incidence("Comunidad de Madrid", "", "Avería", "Coslada", "Salida", "2023-12-01 10:45:00", "Camión averiado ocupando carril derecho.", 108, "Rojo", "Avería A-2", "Otros", 40.440000, -3.550000, 14.200, 14.000, "Madrid", "A-2", 67, "2023-12-01 09:00:00"),
			    new Incidence("Comunidad de Madrid", "", "Seguridad", "Madrid", "Ambos", "2023-12-01 14:00:00", "Objeto en la calzada.", 109, "Amarillo", "Obstáculo M-30", "Seguridad vial", 40.450000, -3.680000, 5.500, 5.500, "Madrid", "M-30", 67, "2023-12-01 12:30:00"),
			    new Incidence("Comunidad de Madrid", "", "Retención", "Rivas", "Entrada", "2023-12-01 09:00:00", "Retenciones habituales.", 110, "Amarillo", "Retención A-3", "Retención", 40.350000, -3.520000, 18.000, 14.000, "Madrid", "A-3", 67, "2023-12-01 07:00:00"),
			    new Incidence("Comunidad de Madrid", "", "Meteorología", "Navacerrada", "Ambos", "2023-12-03 12:00:00", "Calzada con hielo. Uso de cadenas.", 111, "Negro", "Hielo M-601", "Meteorología", 40.780000, -4.000000, 20.000, 12.000, "Madrid", "M-601", 67, "2023-12-03 05:00:00"),
			    new Incidence("Comunidad de Madrid", "", "Evento", "Madrid", "Centro", "2023-12-05 23:00:00", "Corte por evento deportivo.", 112, "Negro", "Maratón", "Otros", 40.416000, -3.690000, 0.000, 0.000, "Madrid", "Urbana", 67, "2023-12-05 08:00:00"),
			    new Incidence("Comunidad de Madrid", "", "Accidente", "Alcorcón", "Salida", "2023-12-01 16:00:00", "Salida de vía.", 113, "Rojo", "Accidente A-5", "Accidente", 40.340000, -3.830000, 12.500, 12.500, "Madrid", "A-5", 67, "2023-12-01 14:20:00"),
			    new Incidence("Comunidad de Madrid", "", "Obras", "Barajas", "Acceso", "2023-12-20 18:00:00", "Mantenimiento de túneles.", 114, "Verde", "Obras M-11", "Obras", 40.470000, -3.600000, 8.000, 6.000, "Madrid", "M-11", 67, "2023-12-01 00:00:00"),
			    new Incidence("Comunidad de Madrid", "", "Avería", "Leganés", "Entrada", "2023-12-01 11:30:00", "Autobús averiado.", 115, "Amarillo", "Avería M-45", "Otros", 40.320000, -3.750000, 5.000, 5.000, "Madrid", "M-45", 67, "2023-12-01 10:00:00"),
			    new Incidence("Comunidad de Madrid", "", "Accidente", "Pinto", "Salida", "2023-12-01 19:00:00", "Vuelco de carga.", 116, "Rojo", "Accidente A-4", "Accidente", 40.240000, -3.690000, 22.000, 21.500, "Madrid", "A-4", 67, "2023-12-01 15:45:00"),
			    new Incidence("Comunidad de Madrid", "", "Retención", "San Fernando", "Entrada", "2023-12-01 09:15:00", "Accidente leve resuelto, tráfico lento.", 117, "Amarillo", "Retención M-21", "Retención", 40.430000, -3.500000, 3.000, 1.000, "Madrid", "M-21", 67, "2023-12-01 08:30:00"),
			    new Incidence("Comunidad de Madrid", "", "Meteorología", "Aranjuez", "Salida", "2023-12-01 14:00:00", "Lluvia intensa.", 118, "Verde", "Lluvia A-4", "Meteorología", 40.030000, -3.600000, 48.000, 40.000, "Madrid", "A-4", 67, "2023-12-01 10:00:00"),
			    new Incidence("Comunidad de Madrid", "", "Obras", "Tres Cantos", "Ambos", "2024-02-01 12:00:00", "Construcción de tercer carril.", 119, "Blanco", "Obras M-607", "Obras", 40.600000, -3.700000, 25.000, 20.000, "Madrid", "M-607", 67, "2023-09-01 08:00:00"),
			    new Incidence("Comunidad de Madrid", "", "Otros", "Madrid", "Nudo Norte", "2023-12-01 13:30:00", "Semáforos apagados por avería eléctrica.", 120, "Rojo", "Avería Semáforos", "Seguridad vial", 40.480000, -3.690000, 0.500, 0.000, "Madrid", "M-30", 67, "2023-12-01 11:00:00")
			));
		try {

			for (Incidence incidence : listaIncidences) {
				IncidenceRepo.addIncidence(incidence);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Comprueba que hayan datos insertados en la BD
	public static boolean ComprobarDatosExisten(Connection connection) throws Exception {
		String sql = "SELECT COUNT(*) FROM sources";

		try (PreparedStatement pstmt = connection.prepareStatement(sql); java.sql.ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				int total = rs.getInt(1);
				return total > 0;
			}
		} catch (Exception e) {
			System.out.println("Error al comprobar la existencia de datos: " + e.getMessage());
		}

		return false;
	}
}