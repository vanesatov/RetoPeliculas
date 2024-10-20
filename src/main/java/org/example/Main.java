package org.example;



import org.example.views.Login;
import org.example.views.Principal;

public class Main {
    public static void main(String[] args) {

       /**
        * El punto de entrada principal de la aplicación.
        * Inicializa y muestra la vista de inicio de sesión.
        */
       var login = new Login();
         login.setVisible(true);

    }
}