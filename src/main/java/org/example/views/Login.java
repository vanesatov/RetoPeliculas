package org.example.views;

import org.example.JdbcUtil;
import org.example.Session;
import org.example.dao.UsuarioDAO;
import org.example.models.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.example.Session.parametrosANull;

/**
 * La clase Login representa la ventana de inicio de sesión de la aplicación.
 * Permite a los usuarios ingresar su nombre de usuario y contraseña para iniciar sesión,
 * y ofrece la opción de salir de la aplicación.
 * Extiende JFrame para proporcionar una interfaz gráfica de usuario.
 */

public class Login extends JFrame {
    private JPanel panel1;
    private JButton iniciarSesiónButton;
    private JButton salirButton;
    private JTextField textField1;
    private JPasswordField passwordField1;

    /**
     * Constructor de la clase Login.
     * Configura la ventana de inicio de sesión y los eventos para los botones.
     */
    public Login(){
        // Configuración de la ventana
        JTextField nombre = new JTextField();
        JPasswordField pass = new JPasswordField();
        JButton iniciarSesión = new JButton("Iniciar Sesión");

        setContentPane(panel1);
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        // Acción para el botón de iniciar sesión
        iniciarSesiónButton.addActionListener((ActionEvent e)-> {
            // Obtener el nombre de usuario y la contraseña del formulario
            String nombreUsuario = textField1.getText();
            char[] passwordChars = passwordField1.getPassword();
            String password = new String(passwordChars);

            // Validar que los campos no estén vacíos
            if (nombreUsuario.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa el nombre de usuario y la contraseña.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Conectar con la base de datos y validar el usuario
            UsuarioDAO usuarioDAO = new UsuarioDAO(JdbcUtil.getConnection());
            Usuario usuario = usuarioDAO.validarUsuario(nombreUsuario, password);

            if (usuario != null) {
                // Iniciar sesión si la validación es exitosa
                Session.usuarioActual = usuario;

                // Mostrar la pantalla principal
                Principal principal = new Principal();
                principal.setVisible(true);

                // Cerrar la ventana de login actual
                dispose();
            } else {
                // Mostrar mensaje de error si las credenciales son incorrectas
                JOptionPane.showMessageDialog(this, "Nombre de usuario o contraseña incorrectos.", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
            }


        });

        // Acción para el botón de salir
        salirButton.addActionListener((ActionEvent e) -> {
            parametrosANull();
                dispose();

        });
    }
}
