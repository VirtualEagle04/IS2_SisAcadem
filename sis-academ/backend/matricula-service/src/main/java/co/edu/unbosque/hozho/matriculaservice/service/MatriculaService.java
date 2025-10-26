package co.edu.unbosque.hozho.matriculaservice.service;

import co.edu.unbosque.hozho.matriculaservice.model.Matricula;
import co.edu.unbosque.hozho.matriculaservice.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatriculaService implements CRUDOperations<Matricula>{

    @Autowired
    private MatriculaRepository matriculaRepo;

    @Override
    public int create(Matricula data) {
        if (data.getIdMatricula() != null) return 1; // No se debe proporcionar un ID al crear una matricula

        matriculaRepo.save(data);
        return 0;
    }

    @Override
    public List<Matricula> getAll() {
        return matriculaRepo.findAll();
    }

    @Override
    public int updateById(Long id, Matricula data) {
        Optional<Matricula> found =  matriculaRepo.findById(id);
        if (found.isEmpty()) return 1; // No existe una matricula con ese ID

        Matricula m = found.get();
        m.setIdEstudiante(data.getIdEstudiante());
        m.setIdPeriodo(data.getIdPeriodo());
        m.setFechaMatricula(data.getFechaMatricula());
        m.setEstado(data.getEstado());

        matriculaRepo.save(data);
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        Optional<Matricula> found =  matriculaRepo.findById(id);
        if (found.isEmpty()) return 1; // No existe una matricula con ese ID

        try {
            matriculaRepo.deleteById(id);
            return 0;
        } catch (DataIntegrityViolationException e) {
            return 2; // No se puede eliminar la matricula porque tiene registros asociados
        }
    }
}
