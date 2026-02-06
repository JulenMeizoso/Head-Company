DROP DATABASE IF EXISTS HeadCompany;
CREATE DATABASE IF NOT EXISTS HeadCompany;
USE HeadCompany;

-- 1.SOURCES (FK)
CREATE TABLE sources (
    source_id INT NOT NULL,
    descripcion_es VARCHAR(255),
    descripcion_eu VARCHAR(255),
    PRIMARY KEY (source_id)
);

-- 2.CAMERAS
CREATE TABLE cameras (
    camera_id INT AUTO_INCREMENT,
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
    incidence_id INT AUTO_INCREMENT,
    autonomous_region VARCHAR(100),
    car_registration VARCHAR(50),
    cause VARCHAR(100),
    city_town VARCHAR(100),
    direction VARCHAR(50),
    end_date VARCHAR(50),
    incidence_description TEXT,
    incidence_level VARCHAR(50),
    incidence_name VARCHAR(100),
    incidence_type ENUM(
        'Accidente', 
        'Obras', 
        'Meteorología', 
        'Seguridad vial', 
        'Retención', 
        'Otros'
    ) DEFAULT 'Otros',
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


CREATE USER IF NOT EXISTS 'HCAPI'@'%' IDENTIFIED BY 'admin';
GRANT SELECT, INSERT, UPDATE, DELETE ON headcompany.* TO 'HCAPI'@'%';
FLUSH PRIVILEGES;

CREATE USER IF NOT EXISTS 'HCDESKTOP'@'%' IDENTIFIED BY 'admin';
GRANT SELECT ON headcompany.* TO 'HCDESKTOP'@'%';
GRANT INSERT, UPDATE, DELETE ON headcompany.cameras TO 'HCDESKTOP'@'%';
GRANT INSERT, UPDATE, DELETE ON headcompany.incidences TO 'HCDESKTOP'@'%';
FLUSH PRIVILEGES;

SELECT * FROM CAMERAS;
SELECT * FROM INCIDENCES;
SELECT * FROM USERS;