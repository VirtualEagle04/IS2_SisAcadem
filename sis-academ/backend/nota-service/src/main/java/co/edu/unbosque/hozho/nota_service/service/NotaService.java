package co.edu.unbosque.hozho.nota_service.service;

import co.edu.unbosque.hozho.nota_service.model.Nota;
import co.edu.unbosque.hozho.nota_service.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotaService implements CRUDOperations<Nota>{

    @Autowired
    private NotaRepository notaRepo;

    public NotaService() {
    }

    @Override
    public int create(Nota data) {
        if (data.getIdNota() != null) return 1; // No se debe proporcionar un ID al crear una nota

        notaRepo.save(data);
        return 0;
    }

    @Override
    public List<Nota> getAll() {
        return notaRepo.findAll();
    }

    @Override
    public int updateById(Long id, Nota data) {
        Optional<Nota> found = notaRepo.findById(id);
        if (found.isEmpty()) return 1; // No existe una nota con ese ID

        Nota n = found.get();
        n.setIdActividad(data.getIdActividad());
        n.setIdEstudiante(data.getIdEstudiante());
        n.setIdPeriodo(data.getIdPeriodo());
        n.setCalificacion(data.getCalificacion());
        n.setObservacion(data.getObservacion());
        n.setTipo(data.getTipo());

        notaRepo.save(n);
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        Optional<Nota> found = notaRepo.findById(id);
        if (found.isEmpty()) return 1; // No existe una nota con ese ID

        try {
            notaRepo.deleteById(id);
            return 0;
        } catch (DataIntegrityViolationException e) {
            return 2; // No se puede eliminar la nota porque tiene registros asociados
        }
    }
}
