package org.gmarquez.java.transacciones;


import java.sql.*;
import java.util.Date;

public class EjemploTransacciones {
    public static void main(String[] args) throws SQLException {

        // Obtenemos la conexión
        try (Connection conexion = Conexion.getInstance()) { // Unica instancia de la conexión

            if (conexion.getAutoCommit()) {
                // Pasamos el autocomit a false, para poder implemetar el rollback si falla
                conexion.setAutoCommit(false);
            }

            try {

                // Creando un producto
                Producto producto = new Producto();
                producto.setNombre("Laptop");
                producto.setPrecio(1000);
                producto.setFechaRegistro(new Date());
                producto.setCategoria(new Categoria(1L, "Electrónica"));
                producto.setSku("LAP-009");

                Producto nuevoProducto = EjemploTransacciones.crearProducto(producto);
                System.out.println("Producto creado: " + nuevoProducto);

                // Eliminando un producto
                EjemploTransacciones.eliminarProducto(1L);


                // Recorremos el resultado

                Statement sentencia = conexion.createStatement();   // Creando el statement
                ResultSet resultado = sentencia.executeQuery("SELECT * FROM productos"); // Ejecutando la consulta

                System.out.println("Recorriendo el resultado");
                System.out.println("========================");
                System.out.println("ID\tNOMBRE\tPRECIO\tFECHA REGISTRO");
                while (resultado.next()) {
                    System.out.print(resultado.getInt("id") + "\t");
                    System.out.print(resultado.getString("nombre") + "\t");
                    System.out.print(resultado.getInt("precio") + "\t");
                    System.out.println(resultado.getDate("fecha_registro"));
                }

                // Explicito el commit
                conexion.commit();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
                // Ejecuta el rollback sobre toda la transaccion
                /*
                 * Importante que los demas metodos que se usen en este, no deban tener implementada el catch con la exepcion para
                 * que unicamente entre en este catch si falla la transaccion
                 * */
                conexion.rollback();
                e.printStackTrace();
            }


        }
    }

    public static Producto crearProducto(Producto producto) throws SQLException {
        Producto productoCreado = null;
        // Debe autosear solo el statement no la conexion
        try (PreparedStatement sentencia =
                     Conexion.getInstance()
                             .prepareStatement("INSERT INTO productos (nombre, precio, fecha_registro, categoria_id, sku) VALUES (?, ?, ?,?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            sentencia.setString(1, producto.getNombre());
            sentencia.setInt(2, producto.getPrecio());
            sentencia.setDate(3, new java.sql.Date(producto.getFechaRegistro().getTime()));
            sentencia.setLong(4, producto.getCategoria().getId());
            sentencia.setString(5, producto.getSku());

            sentencia.executeUpdate();

            // Obtenemos el id del producto creado
            try(ResultSet resultado = sentencia.getGeneratedKeys()){
                if(resultado.next()){ // validamos que si se retorne un resulteset
                    producto.setId(resultado.getLong(1)); // 1 => por que solo espermos un valor que es el id
                }
            }

        }
        return producto;
    }

    public static void eliminarProducto(Long id) throws SQLException {
        try (Statement sentencia = Conexion.getInstance().createStatement();) {// Creando el statement
            sentencia.executeUpdate("DELETE FROM productos WHERE id = " + id); // Ejecutando la consulta
        }
    }
}


class Conexion {

    private static String url = "jdbc:mysql://localhost:3306/java_curso_1?serverTimezone=America/Bogota";
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


class Producto {
    private Long id;
    private String nombre;
    private int precio;
    private java.util.Date fechaRegistro;

    private String sku;

    private Categoria categoria;

    public Producto() {
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public java.util.Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", fechaRegistro=" + fechaRegistro +
                ", categoria=" + this.getCategoria() +
                '}';
    }
}


class Categoria {
    private Long id;
    private String nombre;

    public Categoria() {
    }

    public Categoria(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}


