DROP DATABASE IF EXISTS HeadCompany;
CREATE DATABASE IF NOT EXISTS HeadCompany;
USE HeadCompany;

CREATE USER 'HCAPI'@'%' IDENTIFIED BY 'admin';
GRANT SELECT ON headcompany.* TO 'HCAPI'@'%';
FLUSH PRIVILEGES;

-- 1.SOURCES (FK)
CREATE TABLE sources (
    source_id INT NOT NULL,
    descripcion_es VARCHAR(255),
    descripcion_eu VARCHAR(255),
    PRIMARY KEY (source_id)
);

-- 2.CAMERAS
CREATE TABLE cameras (
    camera_id INT NOT NULL,
    address VARCHAR(255),
    camera_name VARCHAR(100),
    kilometer VARCHAR(50),
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    road VARCHAR(100),
    source_id INT NOT NULL,
    url_image VARCHAR(500),
    PRIMARY KEY (camera_id, source_id),
    
    CONSTRAINT fk_cameras_sources
        FOREIGN KEY (source_id) 
        REFERENCES sources(source_id)
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);

-- 3.INCIDENCES
CREATE TABLE incidences (
    incidence_id INT NOT NULL,
    autonomous_region VARCHAR(100),
    car_registration VARCHAR(50),
    cause VARCHAR(100),
    city_town VARCHAR(100),
    direction VARCHAR(50),
    end_date VARCHAR(50),
    incidence_description TEXT,
    incidence_level VARCHAR(50),
    incidence_name VARCHAR(100),
    incidence_type VARCHAR(100),
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    pk_end DECIMAL(8, 3),
    pk_start DECIMAL(8, 3),
    province VARCHAR(50),
    road VARCHAR(50),
    source_id INT NOT NULL,
    start_date VARCHAR(50),
    PRIMARY KEY (incidence_id),
    
    CONSTRAINT fk_incidences_sources
        FOREIGN KEY (source_id) 
        REFERENCES sources(source_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- 4.USERS
CREATE TABLE users (
    mail VARCHAR(255) NOT NULL,
    contra VARCHAR(255) NOT NULL,
    PRIMARY KEY (mail)
);

-- 1.INSERTS SOURCES
INSERT INTO sources (source_id, descripcion_es, descripcion_eu)
VALUES (67, 'HeadCompany', 'BuruEnpresa');

-- 2.INSERTS CAMERAS
INSERT INTO cameras (address, camera_id, camera_name, kilometer, latitude, longitude, road, source_id, url_image) VALUES 
('Paseo de la Castellana', 1, 'Camara Norte', '12.5', 40.416775, -3.703790, 'M-30', 67, 'http://ejemplo.com/img1.jpg'),
('Plaza de Castilla', 2, 'Acceso Central', '0.5', 40.466120, -3.689310, 'Castellana', 67, 'http://ejemplo.com/img2.jpg'),
('Nudo Sur', 3, 'Enlace A-4', '14.2', 40.380500, -3.680100, 'M-30', 67, 'http://ejemplo.com/img3.jpg'),
('Puente de Ventas', 4, 'Lateral Este', '6.8', 40.430200, -3.663000, 'M-30', 67, 'http://ejemplo.com/img4.jpg'),
('Acceso A-6', 5, 'Moncloa Salida', '3.0', 40.435500, -3.719000, 'A-6', 67, 'http://ejemplo.com/img5.jpg'),
('Tunel Pardo', 6, 'M-40 Norte', '25.4', 40.501000, -3.750000, 'M-40', 67, 'http://ejemplo.com/img6.jpg'),
('Avenida de America', 7, 'Salida A-2', '4.1', 40.438000, -3.676000, 'A-2', 67, 'http://ejemplo.com/img7.jpg'),
('Puente de Vallecas', 8, 'M-30 Sur', '10.5', 40.398000, -3.668000, 'M-30', 67, 'http://ejemplo.com/img8.jpg'),
('Cuesta de San Vicente', 9, 'Principe Pio', '1.2', 40.420000, -3.720000, 'Centro', 67, 'http://ejemplo.com/img9.jpg'),
('ODonnell', 10, 'Torre España', '2.5', 40.422000, -3.655000, 'M-23', 67, 'http://ejemplo.com/img10.jpg'),
('Nudo Manoteras', 11, 'Acceso M-11', '32.1', 40.485000, -3.660000, 'M-40', 67, 'http://ejemplo.com/img11.jpg'),
('Via Lusitana', 12, 'Plaza Eliptica', '3.8', 40.385000, -3.718000, 'A-42', 67, 'http://ejemplo.com/img12.jpg'),
('Paseo Extremadura', 13, 'Alto Extremadura', '5.5', 40.408000, -3.735000, 'A-5', 67, 'http://ejemplo.com/img13.jpg'),
('Cristo Rey', 14, 'Hospital Clinico', '2.0', 40.440000, -3.715000, 'A-6', 67, 'http://ejemplo.com/img14.jpg'),
('Embajadores', 15, 'M-30 Oeste', '16.0', 40.390000, -3.695000, 'M-30', 67, 'http://ejemplo.com/img15.jpg'),
('Mendez Alvaro', 16, 'Estacion Sur', '12.8', 40.395000, -3.678000, 'M-30', 67, 'http://ejemplo.com/img16.jpg'),
('Sinesio Delgado', 17, 'Barrio del Pilar', '1.5', 40.470000, -3.700000, 'Urbana', 67, 'http://ejemplo.com/img17.jpg'),
('Ramon y Cajal', 18, 'Acceso Colmenar', '9.0', 40.488000, -3.690000, 'M-607', 67, 'http://ejemplo.com/img18.jpg'),
('Cuatro Vientos', 19, 'Aerodromo', '8.5', 40.370000, -3.780000, 'A-5', 67, 'http://ejemplo.com/img19.jpg'),
('Santa Maria Cabeza', 20, 'Tunel', '2.2', 40.400000, -3.705000, 'A-42', 67, 'http://ejemplo.com/img20.jpg');

-- 3.INSERTS INCIDENCES
INSERT INTO incidences (incidence_id, autonomous_region, car_registration, cause, city_town, direction, end_date, incidence_description, incidence_level, incidence_name, incidence_type, latitude, longitude, pk_end, pk_start, province, road, source_id, start_date) VALUES 
(101, 'Comunidad de Madrid', '', 'Accidente', 'Madrid', 'Salida', '2023-12-01 12:00:00', 'Accidente por alcance en carril central.', 'Rojo', 'Accidente M-30', 'Accidente', 40.416775, -3.703790, 14.500, 14.200, 'Madrid', 'M-30', 67, '2023-12-01 10:30:00'),
(102, 'Comunidad de Madrid', '', 'Obras', 'Las Rozas', 'Entrada', '2023-12-15 18:00:00', 'Corte de carril derecho por asfaltado.', 'Amarillo', 'Obras A-6', 'Obras', 40.490000, -3.870000, 19.500, 18.000, 'Madrid', 'A-6', 67, '2023-12-10 08:00:00'),
(103, 'Comunidad de Madrid', '', 'Avería', 'Getafe', 'Ambos', '2023-12-01 11:00:00', 'Vehículo averiado en arcén.', 'Verde', 'Avería A-42', 'Vehículo detenido', 40.300000, -3.730000, 12.100, 12.100, 'Madrid', 'A-42', 67, '2023-12-01 09:45:00'),
(104, 'Comunidad de Madrid', '', 'Meteorología', 'Somosierra', 'Salida', '2023-12-02 10:00:00', 'Bancos de niebla densa. Visibilidad reducida.', 'Rojo', 'Niebla A-1', 'Meteorología', 41.100000, -3.580000, 95.000, 80.000, 'Madrid', 'A-1', 67, '2023-12-02 06:00:00'),
(105, 'Comunidad de Madrid', '', 'Retención', 'Alcobendas', 'Entrada', '2023-12-01 09:30:00', 'Tráfico lento por hora punta.', 'Amarillo', 'Retención A-1', 'Congestión', 40.540000, -3.640000, 16.000, 12.000, 'Madrid', 'A-1', 67, '2023-12-01 07:30:00'),
(106, 'Comunidad de Madrid', '', 'Accidente', 'Vallecas', 'Interior', '2023-12-01 13:00:00', 'Colisión múltiple, dos carriles cortados.', 'Negro', 'Accidente M-40', 'Accidente', 40.380000, -3.650000, 22.500, 22.000, 'Madrid', 'M-40', 67, '2023-12-01 11:15:00'),
(107, 'Comunidad de Madrid', '', 'Obras', 'Pozuelo', 'Exterior', '2024-01-01 00:00:00', 'Obras de mejora en enlaces.', 'Verde', 'Obras M-40', 'Obras', 40.420000, -3.800000, 45.000, 44.000, 'Madrid', 'M-40', 67, '2023-11-20 09:00:00'),
(108, 'Comunidad de Madrid', '', 'Avería', 'Coslada', 'Salida', '2023-12-01 10:45:00', 'Camión averiado ocupando carril derecho.', 'Rojo', 'Avería A-2', 'Vehículo detenido', 40.440000, -3.550000, 14.200, 14.000, 'Madrid', 'A-2', 67, '2023-12-01 09:00:00'),
(109, 'Comunidad de Madrid', '', 'Seguridad', 'Madrid', 'Ambos', '2023-12-01 14:00:00', 'Objeto en la calzada.', 'Amarillo', 'Obstáculo M-30', 'Seguridad Vial', 40.450000, -3.680000, 5.500, 5.500, 'Madrid', 'M-30', 67, '2023-12-01 12:30:00'),
(110, 'Comunidad de Madrid', '', 'Retención', 'Rivas', 'Entrada', '2023-12-01 09:00:00', 'Retenciones habituales.', 'Amarillo', 'Retención A-3', 'Congestión', 40.350000, -3.520000, 18.000, 14.000, 'Madrid', 'A-3', 67, '2023-12-01 07:00:00'),
(111, 'Comunidad de Madrid', '', 'Meteorología', 'Navacerrada', 'Ambos', '2023-12-03 12:00:00', 'Calzada con hielo. Uso de cadenas.', 'Negro', 'Hielo M-601', 'Meteorología', 40.780000, -4.000000, 20.000, 12.000, 'Madrid', 'M-601', 67, '2023-12-03 05:00:00'),
(112, 'Comunidad de Madrid', '', 'Evento', 'Madrid', 'Centro', '2023-12-05 23:00:00', 'Corte por evento deportivo.', 'Negro', 'Maratón', 'Evento', 40.416000, -3.690000, 0.000, 0.000, 'Madrid', 'Urbana', 67, '2023-12-05 08:00:00'),
(113, 'Comunidad de Madrid', '', 'Accidente', 'Alcorcón', 'Salida', '2023-12-01 16:00:00', 'Salida de vía.', 'Rojo', 'Accidente A-5', 'Accidente', 40.340000, -3.830000, 12.500, 12.500, 'Madrid', 'A-5', 67, '2023-12-01 14:20:00'),
(114, 'Comunidad de Madrid', '', 'Obras', 'Barajas', 'Acceso', '2023-12-20 18:00:00', 'Mantenimiento de túneles.', 'Verde', 'Obras M-11', 'Obras', 40.470000, -3.600000, 8.000, 6.000, 'Madrid', 'M-11', 67, '2023-12-01 00:00:00'),
(115, 'Comunidad de Madrid', '', 'Avería', 'Leganés', 'Entrada', '2023-12-01 11:30:00', 'Autobús averiado.', 'Amarillo', 'Avería M-45', 'Vehículo detenido', 40.320000, -3.750000, 5.000, 5.000, 'Madrid', 'M-45', 67, '2023-12-01 10:00:00'),
(116, 'Comunidad de Madrid', '', 'Accidente', 'Pinto', 'Salida', '2023-12-01 19:00:00', 'Vuelco de carga.', 'Rojo', 'Accidente A-4', 'Accidente', 40.240000, -3.690000, 22.000, 21.500, 'Madrid', 'A-4', 67, '2023-12-01 15:45:00'),
(117, 'Comunidad de Madrid', '', 'Retención', 'San Fernando', 'Entrada', '2023-12-01 09:15:00', 'Accidente leve resuelto, tráfico lento.', 'Amarillo', 'Retención M-21', 'Congestión', 40.430000, -3.500000, 3.000, 1.000, 'Madrid', 'M-21', 67, '2023-12-01 08:30:00'),
(118, 'Comunidad de Madrid', '', 'Meteorología', 'Aranjuez', 'Salida', '2023-12-01 14:00:00', 'Lluvia intensa.', 'Verde', 'Lluvia A-4', 'Meteorología', 40.030000, -3.600000, 48.000, 40.000, 'Madrid', 'A-4', 67, '2023-12-01 10:00:00'),
(119, 'Comunidad de Madrid', '', 'Obras', 'Tres Cantos', 'Ambos', '2024-02-01 12:00:00', 'Construcción de tercer carril.', 'Blanco', 'Obras M-607', 'Obras', 40.600000, -3.700000, 25.000, 20.000, 'Madrid', 'M-607', 67, '2023-09-01 08:00:00'),
(120, 'Comunidad de Madrid', '', 'Otros', 'Madrid', 'Nudo Norte', '2023-12-01 13:30:00', 'Semáforos apagados por avería eléctrica.', 'Rojo', 'Avería Semáforos', 'Seguridad Vial', 40.480000, -3.690000, 0.500, 0.000, 'Madrid', 'M-30', 67, '2023-12-01 11:00:00');

-- 4.INSERTS USERS
INSERT INTO users (mail, contra) VALUES 
('admin@trafficapp.com', 'admin123'),
('juan.perez@gmail.com', '123456'),
('maria.gonzalez@hotmail.com', 'claveSegura'),
('antonio.ruiz@yahoo.es', 'antonio2023'),
('laura.sanchez@outlook.com', 'lau_san_88'),
('carlos.martinez@gmail.com', 'cmartinez'),
('david.fernandez@protonmail.com', 'qwerty'),
('lucia.jimenez@empresa.net', 'lucia.j'),
('miguel.angel@terra.es', 'miguelangel'),
('elena.moreno@gmail.com', 'elena1234'),
('javier.alvarez@hotmail.com', 'javier_alva'),
('cristina.romero@live.com', 'cristina2024'),
('pablo.diaz@gmail.com', 'pablito_d'),
('raquel.munoz@yahoo.com', 'rmunoz90'),
('sergio.alonso@outlook.es', 'sergio_a'),
('beatriz.navarro@gmail.com', 'bea123bea'),
('fernando.torres@gmail.com', 'nino9'),
('isabel.ruiz@hotmail.com', 'isa_ruiz'),
('ruben.garcia@gmail.com', 'ruben_g'),
('soporte@trafficapp.com', 'SoporteTecnico2024');

-- 5.SELECT
SELECT * FROM cameras;
SELECT * FROM incidences;
SELECT * FROM users;