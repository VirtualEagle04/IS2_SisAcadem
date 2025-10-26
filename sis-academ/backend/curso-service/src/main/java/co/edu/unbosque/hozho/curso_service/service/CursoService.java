package co.edu.unbosque.hozho.curso_service.service;

import co.edu.unbosque.hozho.curso_service.model.Curso;
import co.edu.unbosque.hozho.curso_service.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CursoService implements CRUDOperations<Curso> {

    @Autowired
    private CursoRepository cursoRepository;

    public CursoService(){

    }

    @Override
    public int create(Curso data) {

        if(data.getIdCurso() != null) return 1; //No se debe proporcionar un ID al crear un Curso
        else if(cursoRepository.existsByNombre(data.getNombre())) return 2; //Ya existe un Curso con el nombre proporcionado


        cursoRepository.save(data);
        return 0;
    }

    @Override
    public List<Curso> getAll() {
        return cursoRepository.findAll();
    }

    @Override
    public int deleteById(Long id) {

        Optional<Curso> found = cursoRepository.findById(id);
        if (found.isEmpty()) {
            return 1; // No existe un Curso con ese ID
        }

        try {
            cursoRepository.delete(found.get());
            return 0;
        } catch (DataIntegrityViolationException e) {
            return 2; // No se puede eliminar el Curso porque tiene Notas asociadas
        }

    }

    @Override
    public int updateById(Long id, Curso data) {

        Optional <Curso> found = cursoRepository.findById(id);

        if (found.isPresent()) {
            Curso g =  found.get();
            g.setNombre(data.getNombre());

            cursoRepository.save(g);
            return 0;
        }
        return 1;
    }
}
