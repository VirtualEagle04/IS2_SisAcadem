package co.edu.unbosque.hozho.horarioservice.service;

import java.util.List;

public interface CRUDOperations<T> {

    public int create(T data);

    public List<T> getAll();

    public int deleteById(Long id);

    public int updateById(Long id, T data);
}
