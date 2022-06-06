package org.gmarquez.java.dao_repository.repository;

import java.util.List;

public interface Repositorio<T> {
    List<T> listar();
    T buscarPorId(Long id);
    T guardar(T t);
    void eliminar(Long id);
}
