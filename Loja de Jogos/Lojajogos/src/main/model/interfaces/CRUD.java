package main.model.interfaces;

import java.util.List;

public interface CRUD<T> {
    void cadastrar(T t);
    T buscarPorId(int id);
    List<T> listarTodos();
    void atualizar(T t);
    void deletar(int id);
}