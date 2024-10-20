package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un usuario en el sistema.
 * Esta clase es un modelo para la entidad `Usuario`.
 * Incluye detalles del usuario como id, nombre de usuario, contraseña y una lista de copias asociadas con el usuario.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {

    /**
     * El identificador único del usuario.
     */
    private Integer id;

    /**
     * El nombre de usuario del usuario.
     */
    private String nombre_usuario;

    /**
     * La contraseña del usuario.
     */
    private String contraseña;

    /**
     * La lista de copias asociadas con el usuario.
     */
    private List<Copia> copias = new ArrayList<>(0);
}