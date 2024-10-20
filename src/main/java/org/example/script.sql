CREATE DATABASE IF NOT EXISTS datos;
USE datos;

CREATE TABLE Pelicula (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          titulo VARCHAR(200),
                          genero VARCHAR(200),
                          año INT,
                          descripcion VARCHAR(200),
                          director VARCHAR(200)
);

CREATE TABLE Usuario (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nombre_usuario VARCHAR(200),
                         contraseña VARCHAR(200)
);

CREATE TABLE Copia (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       id_pelicula INT,
                       id_usuario INT,
                       estado VARCHAR(200),
                       soporte VARCHAR(200),
                       FOREIGN KEY (id_pelicula) REFERENCES Pelicula(id),
                       FOREIGN KEY (id_usuario) REFERENCES Usuario(id)
);

-- Seleccionar la base de datos
USE datos;

-- Insertar datos en la tabla Usuario
INSERT INTO Usuario (nombre_usuario, contraseña) VALUES
                                                     ('juanperez', 'password123'),
                                                     ('mariagonzalez', 'mypassword');

-- Insertar datos en la tabla Pelicula
INSERT INTO Pelicula (titulo, genero, año, descripcion, director) VALUES
                                                                      ('Origen', 'Sci-Fi', 2010, 'Un ladrón que roba secretos corporativos a través del uso de la tecnología de compartir sueños recibe una oportunidad para borrar su historial criminal.', 'Christopher Nolan'),
                                                                      ('Matrix', 'Acción', 1999, 'Un hacker de ordenadores aprende de rebeldes misteriosos sobre la verdadera naturaleza de su realidad y su papel en la guerra contra sus controladores.', 'Lana Wachowski'),
                                                                      ('Interstellar', 'Sci-Fi', 2014, 'Un equipo de exploradores viaja a través de un agujero de gusano en el espacio en un intento de asegurar la supervivencia de la humanidad.', 'Christopher Nolan'),
                                                                      ('Star Wars: Episodio IV - Una nueva esperanza', 'Sci-Fi', 1977, 'Un joven granjero se une a una rebelión contra un imperio galáctico tiránico.', 'George Lucas');

-- Insertar datos en la tabla Copia
INSERT INTO Copia (id_pelicula, id_usuario, estado, soporte) VALUES
                                                                 (1, 1, 'bueno', 'DVD'),
                                                                 (1, 1, 'bueno', 'Blu-ray'),
                                                                 (2, 2, 'dañado', 'DVD'),
                                                                 (3, 1, 'bueno', 'Blu-ray'),
                                                                 (4, 2, 'bueno', 'DVD'),
                                                                 (4, 1, 'bueno', 'Blu-ray');
