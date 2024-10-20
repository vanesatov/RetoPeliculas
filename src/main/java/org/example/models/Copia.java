package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor

/**
 * Representa una copia de una película en el sistema.
 * Esta clase es un modelo para la entidad `Copia`.
 * Incluye detalles de la copia como id, id de la película, id del usuario, estado y soporte.
 */
public class Copia implements Serializable {

    /**
     * El identificador único de la copia.
     */
    private Integer id;

    /**
     * El identificador de la película asociada con la copia.
     */
    private Integer id_pelicula;

    /**
     * El identificador del usuario asociado con la copia.
     */
    private Integer id_usuario;

    /**
     * El estado de la copia.
     */
    private String estado;

    /**
     * El tipo de soporte de la copia (por ejemplo, DVD, Blu-ray, etc.).
     */
    private String soporte;

    /**
     * El usuario asociado con la copia.
     */
    private Usuario usuario;

    /**
     * La película asociada con la copia.
     */
    private Pelicula pelicula;

}
