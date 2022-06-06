package org.gmarquez.java.dao_repository;

import org.gmarquez.java.dao_repository.modelo.Categoria;
import org.gmarquez.java.dao_repository.modelo.Producto;
import org.gmarquez.java.dao_repository.repository.ProductoRepositorio;

import java.util.Date;


public class EjemploDaoRepositorio {
    public static void main(String[] args) {

        ProductoRepositorio productoRepositorio = new ProductoRepositorio();
        System.out.println("Lista de productos");
        productoRepositorio.listar().forEach(System.out::println);

        System.out.println("Producto por id");
        System.out.println(productoRepositorio.buscarPorId(  3L));

        System.out.println("Creando producto");
        Categoria categoria = new Categoria(2L, "moda");
        productoRepositorio.guardar(new Producto(null, "Xbox", 9000, new Date(), categoria));

        System.out.println("Actualizando producto");
        productoRepositorio.guardar(new Producto(2L, "Atari", 865202, new Date(), categoria));

        System.out.println("Eliminando producto");
        productoRepositorio.eliminar(6L);

    }
}
