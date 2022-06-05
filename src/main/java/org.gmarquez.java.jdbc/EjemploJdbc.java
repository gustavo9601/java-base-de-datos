package org.gmarquez.java.jdbc;

import java.sql.*;

public class EjemploJdbc {
    public static void main(String[] args) {

        System.out.println("conexionBasicaQuery()");
        EjemploConexionesBD.conexionBasicaQuery();

    }
}


class EjemploConexionesBD {
    public static void conexionBasicaQuery() {
        // UTC => Timezone / /https://en.wikipedia.org/wiki/List_of_tz_database_time_zones
        String url = "jdbc:mysql://localhost:3306/java_curso_1?serverTimezone=America/Bogota";
        String usuario = "root";
        String password = "";

        // Manejamos el try cath por recurso de forma que si hay error se cierra el recurso, y cierre la conexion automaticamente al finalizar
        try (
                Connection conexion = DriverManager.getConnection(url, usuario, password);     // Creando una conexión a la base de datos
                Statement sentencia = conexion.createStatement();   // Creando el statement
                ResultSet resultado = sentencia.executeQuery("SELECT * FROM productos"); // Ejecutando la consulta
        ) {

            // Recorriendo el resultado
            while (resultado.next()) {
                // .getTIPO de la columna de la tabla, por nombre o por posicion (0, 1, 2, etc)
                System.out.println("id=\t" + resultado.getInt("id"));
                System.out.println("nombre=\t" + resultado.getString("nombre"));
                System.out.println("precio=\t" + resultado.getInt("precio"));
                System.out.println("fecha_registro=\t" + resultado.getDate("fecha_registro"));
                System.out.println("---------------------------------");
            }

        } catch (SQLException exception) {
            System.out.println("Error de conexion: " + exception.getMessage());
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }
}
