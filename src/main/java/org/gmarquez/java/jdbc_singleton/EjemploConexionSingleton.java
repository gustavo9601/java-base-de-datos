package org.gmarquez.java.jdbc_singleton;

import java.sql.*;

public class EjemploConexionSingleton {

    public static void main(String[] args) {

        // Obtenemos la conexión
        try (Connection conexion = ConexionSingleton.getInstance(); // Unica instancia de la conexión
             Statement sentencia = conexion.createStatement();   // Creando el statement
             ResultSet resultado = sentencia.executeQuery("SELECT * FROM productos"); // Ejecutando la consulta
        ) {

            // Recorremos el resultado
            System.out.println("Recorriendo el resultado");
            System.out.println("========================");
            System.out.println("ID\tNOMBRE\tPRECIO\tFECHA REGISTRO");
            while (resultado.next()) {
                System.out.print(resultado.getInt("id") + "\t");
                System.out.print(resultado.getString("nombre") + "\t");
                System.out.print(resultado.getInt("precio") + "\t");
                System.out.println(resultado.getDate("fecha_registro"));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}

class ConexionSingleton {

    private static String url = "jdbc:mysql://localhost:3306/java_curso_1?serverTimezone=America/Bogota";
    private static String usuario = "root";
    private static String password = "";
    private static Connection instance = null;

    private ConexionSingleton() {
    }

    public static Connection getInstance() throws SQLException {
        if (instance == null) {
            instance = DriverManager.getConnection(url, usuario, password);
        }
        return instance;
    }

}
