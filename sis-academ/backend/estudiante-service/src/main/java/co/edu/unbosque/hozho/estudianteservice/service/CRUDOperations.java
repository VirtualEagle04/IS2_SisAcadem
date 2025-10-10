package co.edu.unbosque.hozho.estudianteservice.service;

import co.edu.unbosque.hozho.estudianteservice.model.Estudiante;

import java.util.List;

public interface CRUDOperations<T> {

    public int create(T data);

    public List<T> getAll();

    public int deleteById(Long id);

    public int updateById(Long id, T data);
}
