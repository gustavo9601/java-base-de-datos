package org.gmarquez.ejemplos.mantenedor_usuarios.repositorios;

import java.util.List;

public interface Repositorio<T> {
    List<T> listar();
    void guardar(T t);
    void eliminar(Long id);
}
