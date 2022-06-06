package org.gmarquez.java.dao_repository.repository;

import org.gmarquez.java.dao_repository.conexion.Conexion;
import org.gmarquez.java.dao_repository.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorio implements Repositorio<Producto> {

    private Connection getConnection() throws SQLException {
        return Conexion.getInstance();
    }

    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();

        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM productos")) {

            while (resultSet.next()) {
                Producto producto = crearProducto(resultSet);
                productos.add(producto);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar productos");
            e.printStackTrace();
        }

        return productos;

    }

    private Producto crearProducto(ResultSet resultSet) throws SQLException {
        Producto producto = new Producto();
        producto.setId(resultSet.getLong("id"));
        producto.setNombre(resultSet.getString("nombre"));
        producto.setPrecio(resultSet.getInt("precio"));
        producto.setFechaRegistro(resultSet.getDate("fecha_registro"));
        return producto;
    }

    @Override
    public Producto buscarPorId(Long id) {
        Producto producto = new Producto();

        try (PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM productos WHERE id = ?")) {
            // Bindiamos los parametrs  .tipo(posicion en el query, valor)
            statement.setLong(1, id);
            // EJecutamos la consulta
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                producto = crearProducto(resultSet);
            }

            // Cerrando el resultset
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar producto");
            e.printStackTrace();
        }

        return producto;
    }

    @Override
    public Producto guardar(Producto producto) {
        String sql;
        if (producto.getId() != null && producto.getId() > 0) {
            sql = "UPDATE productos SET nombre = ?, precio = ?, fecha_registro = ? WHERE id = ?";
        } else {
            sql = "INSERT INTO productos (nombre, precio, fecha_registro) VALUES (?, ?, ?)";
        }

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            // Bindiamos los parametros
            statement.setString(1, producto.getNombre());
            statement.setInt(2, producto.getPrecio());
            statement.setDate(3, new Date(producto.getFechaRegistro().getTime()));
            // Si es un update
            if (producto.getId() != null && producto.getId() > 0) {
                statement.setLong(4, producto.getId());
            }

            // Ejecutamos el query
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al guardar producto");
            e.printStackTrace();
        }
        return producto;
    }


    @Override
    public void eliminar(Long id) {
        try(PreparedStatement statement = getConnection().prepareStatement("DELETE FROM productos WHERE id = ?")){
            // Bindiamos los parametros
            statement.setLong(1, id);
            // Ejecutamos el query
            statement.executeUpdate();

        }catch (SQLException e) {
            System.out.println("Error al eliminar producto");
            e.printStackTrace();
        }
    }
}
