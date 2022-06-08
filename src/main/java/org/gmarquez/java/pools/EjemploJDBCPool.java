package org.gmarquez.java.pools;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EjemploJDBCPool {

    public static void main(String[] args) {
        try {
            Connection connection = Conexion.getConection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM productos");

            System.out.println("ID\tNOMBRE\tPRECIO\tFECHA REGISTRO");
            while (resultSet.next()) {
                System.out.print(resultSet.getInt("id") + "\t");
                System.out.print(resultSet.getString("nombre") + "\t");
                System.out.print(resultSet.getInt("precio") + "\t");
                System.out.println(resultSet.getDate("fecha_registro"));
            }

            connection.close(); // cierra la conexion para liberarla del pool

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}


class Conexion {

    private static String url = "jdbc:mysql://localhost:3306/java_curso_1?serverTimezone=America/Bogota";
    private static String usuario = "root";
    private static String password = "";
    private static BasicDataSource pool;

    private Conexion() {
    }

    public static BasicDataSource getInstance() {
        if (pool == null) {
            pool = new BasicDataSource();
            pool.setUrl(url);
            pool.setUsername(usuario);
            pool.setPassword(password);
            // cantidad de conexion iniciales
            pool.setInitialSize(5);
            // cantidad de conexiones minimas
            pool.setMinIdle(3);
            // cantidad de conexiones maximas
            pool.setMaxIdle(10);
            // maximo de conexiones en el pool
            pool.setMaxTotal(10);
        }
        return pool;
    }


    public static Connection getConection() throws SQLException {
        return getInstance().getConnection();
    }

}
