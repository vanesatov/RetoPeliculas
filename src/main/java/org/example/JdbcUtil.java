package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * La clase JdbcUtil proporciona una conexión a la base de datos utilizando JDBC.
 * Se conecta a una base de datos MySQL utilizando las credenciales proporcionadas.
 */
public class JdbcUtil {

    private static Connection connection;

    static {
        String url = "jdbc:mysql://localhost:3307/datos";
        String user = "root";
        String password = System.getenv("MYSQL_ROOT_PASSWORD");
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtiene la conexión a la base de datos.
     *
     * @return la conexión a la base de datos
     */
    public static Connection getConnection() {
        return connection;
    }
}

