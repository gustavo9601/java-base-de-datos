package org.gmarquez.ejemplos.mantenedor_usuarios.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static String url = "jdbc:mysql://localhost:3306/java_usuarios?serverTimezone=America/Bogota";
    private static String usuario = "root";
    private static String password = "";
    private static Connection instance = null;

    private Conexion() {
    }

    public static Connection getInstance() throws SQLException {
        if (instance == null) {
            instance = DriverManager.getConnection(url, usuario, password);
        }
        return instance;
    }
}
