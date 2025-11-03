package co.edu.unbosque.hozho.grado_service.service;

import co.edu.unbosque.hozho.grado_service.model.Grado;
import co.edu.unbosque.hozho.grado_service.repository.GradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class GradoService implements CRUDOperations<Grado> {

    @Autowired
    private GradoRepository gradoRepository;

    public GradoService(){

    }

    @Override
    public int create(Grado data) {

        if(data.getIdGrado() != null) return 1; //No se debe proporcionar un ID al crear un Grado
        else if(gradoRepository.existsByNombre(data.getNombre())) return 2; //Ya existe un grado con el nombre proporcionado


        gradoRepository.save(data);
        return 0;
    }

    @Override
    public List<Grado> getAll() {
        return gradoRepository.findAll();
    }

    @Override
    public int deleteById(Long id) {

        Optional<Grado> found = gradoRepository.findById(id);
        if (found.isEmpty()) {
            return 1; // No existe un Grado con ese ID
        }

        try {
            gradoRepository.delete(found.get());
            return 0;
        } catch (DataIntegrityViolationException e) {
            return 2; // No se puede eliminar el Grado porque tiene Notas asociadas
        }

    }

    @Override
    public int updateById(Long id, Grado data) {
        Optional <Grado> found = gradoRepository.findById(id);
        if (found.isEmpty()) return 1; // No existe un grado con ese ID

        Grado g = found.get();
        g.setNombre(data.getNombre());

        gradoRepository.save(g);
        return 0;
    }
}
