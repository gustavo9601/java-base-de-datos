package org.gmarquez.java.transacciones_pools.servicio;

import org.gmarquez.java.transacciones_pools.modelo.Categoria;
import org.gmarquez.java.transacciones_pools.modelo.Producto;
import org.gmarquez.java.transacciones_pools.repositorio.CategoriaRepositorioImpl;
import org.gmarquez.java.transacciones_pools.repositorio.ProductoRepositorioImpl;
import org.gmarquez.java.transacciones_pools.repositorio.Repositorio;
import org.gmarquez.java.transacciones_pools.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CatalogoServicio implements Servicio{
    private Repositorio<Producto> productoRepositorio;
    private Repositorio<Categoria> categoriaRepositorio;

    public CatalogoServicio() {
        this.productoRepositorio = new ProductoRepositorioImpl();
        this.categoriaRepositorio = new CategoriaRepositorioImpl();
    }

    @Override
    public List<Producto> listar() throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            this.productoRepositorio.setConn(conn);
            return this.productoRepositorio.listar();
        }

    }

    @Override
    public Producto porId(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            this.productoRepositorio.setConn(conn);
            return this.productoRepositorio.porId(id);
        }

    }

    @Override
    public Producto guardar(Producto producto) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            this.productoRepositorio.setConn(conn);
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            Producto nuevoProducto = null;
            try {
                nuevoProducto = this.productoRepositorio.guardar(producto);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
            return nuevoProducto;
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            this.productoRepositorio.setConn(conn);
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            try {
                this.productoRepositorio.eliminar(id);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Categoria> listarCategoria() throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            this.categoriaRepositorio.setConn(conn);
            return this.categoriaRepositorio.listar();
        }
    }

    @Override
    public Categoria porIdCategoria(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            this.categoriaRepositorio.setConn(conn);
            return this.categoriaRepositorio.porId(id);
        }

    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            this.categoriaRepositorio.setConn(conn);

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            Categoria nuevaCategoria = null;
            try {
                nuevaCategoria = this.categoriaRepositorio.guardar(categoria);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
            return nuevaCategoria;
        }
    }

    @Override
    public void eliminarCategoria(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            this.categoriaRepositorio.setConn(conn);

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            try {
                this.categoriaRepositorio.eliminar(id);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }

    }

    @Override
    public void guardarProductoConCategoria(Producto producto, Categoria categoria) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            this.productoRepositorio.setConn(conn);
            this.categoriaRepositorio.setConn(conn);

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            try {
                Categoria nuevaCategoria = this.categoriaRepositorio.guardar(categoria);
                producto.setCategoria(nuevaCategoria);
                this.productoRepositorio.guardar(producto);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }

    }
}
