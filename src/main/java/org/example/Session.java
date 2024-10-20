package org.example;

import org.example.models.Pelicula;
import org.example.models.Usuario;

/**
 * La clase Session gestiona la sesión actual del usuario y la película seleccionada.
 * Proporciona métodos para restablecer estos parámetros a null.
 */
public class Session {
    /**
     * La película seleccionada en la sesión actual.
     */
    public static Pelicula peliculaSeleccionada = null;

    /**
     * El usuario actual de la sesión.
     */
    public static Usuario usuarioActual = null;

    /**
     * Restablece los parámetros de la sesión a null.
     */
    public static void parametrosANull() {
        peliculaSeleccionada = null;
        usuarioActual = null;
    }
}
