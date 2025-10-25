package co.edu.unbosque.hozho.materia_service.service;


import co.edu.unbosque.hozho.materia_service.model.Materia;
import co.edu.unbosque.hozho.materia_service.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MateriaService implements CRUDOperations<Materia> {

    @Autowired
    private MateriaRepository materiaRepository;

    public MateriaService() {}

    @Override
    public int create(Materia data) {
        if (data.getIdMateria() !=null) return 1; // No se debe proporcionar un ID al crear Materia
        else if(materiaRepository.existsByNombre(data.getNombre())) return 2; // Ya existe una materia con ese nombre
        materiaRepository.save(data);
        return 0;
    }

    @Override
    public List<Materia> getAll() {
        return  materiaRepository.findAll();
    }

    @Override
    public int deleteById(Long id) {

        Optional<Materia> found = materiaRepository.findById(id);
        if (found.isEmpty()) {
            return 1; // No existe una materia con ese ID
        }

        try {
            materiaRepository.delete(found.get());
            return 0;
        } catch (DataIntegrityViolationException e) {
            return 2; // No se puede eliminar la materia porque tiene Notas asociadas
        }

    }

    @Override
    public int updateById(Long id, Materia data) {
        Optional<Materia> found = materiaRepository.findById(id);
        if (found.isPresent()) {
            Materia materia = found.get();
            materia.setNombre(data.getNombre());
            materia.setIdDocente(data.getIdDocente());
            materia.setDescripcion(data.getDescripcion());
            materiaRepository.save(materia);


            return 0;

        }

        return 1;
    }




}
