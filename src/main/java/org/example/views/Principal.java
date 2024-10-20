package org.example.views;

import org.example.JdbcUtil;
import org.example.Session;
import org.example.dao.CopiaDAO;
import org.example.dao.PeliculaDAO;
import org.example.models.Copia;
import org.example.models.Pelicula;
import org.example.models.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


/**
 * La clase Principal representa la ventana principal de la aplicación,
 * donde se muestran las copias de películas asociadas al usuario que ha iniciado sesión.
 * Permite visualizar detalles de las películas y regresar a la pantalla de Login.
 * Extiende JFrame para proporcionar una interfaz gráfica de usuario.
 */

public class Principal extends JFrame {
    private JPanel panel1;
    private JTable table1;
    private JButton atrásButton;
    private JButton salirButton;
    private JButton informaciónPeliculaButton;

    private DefaultTableModel model;

    /**
     * Constructor de la clase Principal.
     * Configura la interfaz gráfica, inicializa la tabla de copias de películas
     * y asigna los eventos para los botones.
     */

    public Principal() {
        // Configuración de la tabla
        String[] cabecera = {"Titulo", "Estado", "Soporte"};
        model = new DefaultTableModel(cabecera, 0);
        table1.setModel(model);

        // Mostrar las copias de películas del usuario actual
        mostrarCopias();

        // Configuración de la ventana
        setContentPane(panel1);
        setTitle("Mis Películas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);


        // Acción para el botón "Atrás"
        atrásButton.addActionListener((ActionEvent e) -> {
            // Crear una nueva instancia de la pantalla de Login
            Login login = new Login();

            // Hacer visible la pantalla de Login
            login.setVisible(true);

            // Cerrar la ventana actual (Principal)
            dispose();
        });

        // Acción para el botón "Salir"
        salirButton.addActionListener((ActionEvent e) -> {
            dispose();
        });

        // Acción para el botón "Información de la Película"
        informaciónPeliculaButton.addActionListener((ActionEvent e) -> {
            // Obtener la fila seleccionada en la tabla
            int filaSeleccionada = table1.getSelectedRow();

            if (filaSeleccionada == -1) {
                // Si no hay ninguna fila seleccionada, mostrar un mensaje
                JOptionPane.showMessageDialog(Principal.this, "Por favor, selecciona una película.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Obtener el título de la película seleccionada de la tabla
            String tituloPelicula = (String) model.getValueAt(filaSeleccionada, 0);

            // Buscar la película por título usando PeliculaDAO
            PeliculaDAO peliculaDAO = new PeliculaDAO(JdbcUtil.getConnection());
            Pelicula pelicula = peliculaDAO.findByTitulo(tituloPelicula);

            if (pelicula != null) {
                // Mostrar los datos de la película en un cuadro de diálogo
                String informacionPelicula = "Título: " + pelicula.getTitulo() +
                        "\nGénero: " + pelicula.getGenero() +
                        "\nAño: " + pelicula.getAño() +
                        "\nDescripción: " + pelicula.getDescripcion() +
                        "\nDirector: " + pelicula.getDirector();

                JOptionPane.showMessageDialog(Principal.this, informacionPelicula, "Información de la Película", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(Principal.this, "No se encontró información para la película seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Muestra las copias de películas asociadas al usuario actual en la tabla.
     * Obtiene las copias desde la base de datos y las añade al modelo de la tabla.
     */
    private void mostrarCopias() {

        // Obtiene el usuario actualmente conectado desde la sesión
        Usuario usuarioConectado = Session.usuarioActual;

        // Verifica si hay un usuario conectado
        if (usuarioConectado == null) {
            System.out.println("No hay un usuario conectado.");
            return;
        }

        // Inicializa los DAOs para interactuar con la base de datos
        CopiaDAO copiaDAO = new CopiaDAO(JdbcUtil.getConnection());
        PeliculaDAO peliculaDAO = new PeliculaDAO(JdbcUtil.getConnection());


        // Obtiene todas las copias de películas asociadas al usuario conectado
        List<Copia> copias = copiaDAO.findAllByUser(usuarioConectado);

        // Verifica si el usuario no tiene copias de películas
        if (copias.isEmpty()) {
            System.out.println("El usuario no tiene copias de películas.");
            return;
        }

        // Itera sobre cada copia de la lista obtenida
        for (Copia copia : copias) {
            // Busca la película asociada a la copia utilizando el ID de la película
            Pelicula pelicula = peliculaDAO.findById(copia.getId_pelicula());

            // Verifica si la película fue encontrada
            if (pelicula != null) {
                // Asigna la película a la copia
                copia.setPelicula(pelicula);

                // Añade una fila a la tabla con los datos de la película y la copia
                Object[] fila = {pelicula.getTitulo(), copia.getEstado(), copia.getSoporte()};

                // Añade la fila al modelo de la tabla
                model.addRow(fila);

            } else {
                System.out.println("Película no encontrada para la copia con ID: " + copia.getId_pelicula());
            }
        }
    }
}


