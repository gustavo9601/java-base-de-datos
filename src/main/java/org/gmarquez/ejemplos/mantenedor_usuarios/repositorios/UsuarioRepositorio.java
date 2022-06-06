package org.gmarquez.ejemplos.mantenedor_usuarios.repositorios;

import org.gmarquez.ejemplos.mantenedor_usuarios.conexion.Conexion;
import org.gmarquez.ejemplos.mantenedor_usuarios.modelos.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorio implements Repositorio<Usuario> {

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();

        try (Statement statement = Conexion.getInstance().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM usuarios")) {
            while (resultSet.next()) {
                Usuario usuario = generarUsuario(resultSet);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar usuarios");
            e.printStackTrace();
        }
        return usuarios;

    }

    @Override
    public void guardar(Usuario usuario) {

        String sql;
        if(usuario.getId() != null && usuario.getId() > 0){
            sql = "UPDATE usuarios SET username = ?, password = ?, email = ? WHERE id = ?";
        }else{
            sql = "INSERT INTO usuarios (username, password, email) VALUES (?, ?, ?)";
        }

        try (PreparedStatement statement = Conexion.getInstance().prepareStatement(sql)) {
            statement.setString(1, usuario.getUsername());
            statement.setString(2, usuario.getPassword());
            statement.setString(3, usuario.getEmail());
            if(usuario.getId() != null && usuario.getId() > 0){
                statement.setLong(4, usuario.getId());
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al guardar usuario");
            e.printStackTrace();
        }
    }


    @Override
    public void eliminar(Long id) {
        try (PreparedStatement statement = Conexion.getInstance().prepareStatement("DELETE FROM usuarios WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario");
            e.printStackTrace();
        }
    }

    public Usuario generarUsuario(ResultSet resultSet) throws SQLException {
        Usuario producto = new Usuario();
        producto.setId(resultSet.getLong("id"));
        producto.setUsername(resultSet.getString("username"));
        producto.setPassword(resultSet.getString("password"));
        producto.setEmail(resultSet.getString("email"));
        return producto;
    }
}
