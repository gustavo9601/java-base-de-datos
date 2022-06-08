package org.gmarquez.java.transacciones_pools;

import org.gmarquez.java.transacciones_pools.modelo.Categoria;
import org.gmarquez.java.transacciones_pools.modelo.Producto;
import org.gmarquez.java.transacciones_pools.repositorio.CategoriaRepositorioImpl;
import org.gmarquez.java.transacciones_pools.repositorio.ProductoRepositorioImpl;
import org.gmarquez.java.transacciones_pools.repositorio.Repositorio;
import org.gmarquez.java.transacciones_pools.servicio.CatalogoServicio;
import org.gmarquez.java.transacciones_pools.util.ConexionBaseDatos;
import org.gmarquez.java.transacciones_pools.servicio.Servicio;

import java.sql.*;
import java.util.Date;

public class EjemploJdbc {
    public static void main(String[] args) throws SQLException {

        Servicio servicio = new CatalogoServicio();
        System.out.println("============= listar =============");
        servicio.listar().forEach(System.out::println);
        Categoria categoria = new Categoria();
        categoria.setNombre("Iluminación");

        Producto producto = new Producto();
        producto.setNombre("Lámpara led escritorio");
        producto.setPrecio(990);
        producto.setFechaRegistro(new Date());
        producto.setSku("abcdefgh12");
        servicio.guardarProductoConCategoria(producto, categoria);
        System.out.println("Producto guardado con éxito: " + producto.getId());
        servicio.listar().forEach(System.out::println);

    }
}
