package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Representa una película en el sistema.
 * Esta clase es un modelo para la entidad `Pelicula`.
 * Incluye detalles de la película como id, título, género, año, descripción y director.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pelicula implements Serializable {

    /**
     * El identificador único de la película.
     */
    private Integer id;

    /**
     * El título de la película.
     */
    private String titulo;

    /**
     * El género de la película.
     */
    private String genero;

    /**
     * El año de lanzamiento de la película.
     */
    private Integer año;

    /**
     * La descripción de la película.
     */
    private String descripcion;

    /**
     * El director de la película.
     */
    private String director;
}
