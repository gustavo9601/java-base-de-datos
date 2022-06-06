package org.gmarquez.java.dao_repository.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private Conexion() {
    }

    public static Connection getInstance() throws SQLException {
        String usuario = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/java_curso_1?serverTimezone=America/Bogota";
        return  DriverManager.getConnection(url, usuario, password);
    }


}
