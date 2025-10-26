package co.edu.unbosque.hozho.usuarioservice.service;

import co.edu.unbosque.hozho.usuarioservice.model.EstudianteDetalle;
import co.edu.unbosque.hozho.usuarioservice.model.Usuario;
import co.edu.unbosque.hozho.usuarioservice.repository.EstudianteDetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteDetalleService implements CRUDOperations<EstudianteDetalle>{

    @Autowired
    private EstudianteDetalleRepository estudianteDetalleRepo;

    public EstudianteDetalleService() {
    }

    @Override
    public int create(EstudianteDetalle data) {
        if (data.getIdDetalle() != null) return 1; // No se debe proporcionar ID al crear un estudiante

        estudianteDetalleRepo.save(data);
        return 0;
    }

    @Override
    public List<EstudianteDetalle> getAll() {
        return estudianteDetalleRepo.findAll();
    }

    @Override
    public int updateById(Long id, EstudianteDetalle data) {
        Optional<EstudianteDetalle> found = estudianteDetalleRepo.findById(id);
        if (found.isPresent()) {
            EstudianteDetalle u = found.get();
            //u.setIdUsuario(data.getIdUsuario());
            u.setIdCurso(data.getIdCurso());
            u.setIdAcudiente(data.getIdAcudiente());

            estudianteDetalleRepo.save(u);
            return 0;
        }
        return 1; // No existe un estudiante con ese ID
    }

    @Override
    public int deleteById(Long id) {
        Optional<EstudianteDetalle> found = estudianteDetalleRepo.findById(id);
        if (found.isEmpty()) return 1; // No existe un estudiante con ese ID

        try {
            estudianteDetalleRepo.delete(found.get());
            return 0;
        } catch (DataIntegrityViolationException e) {
            return 2; // No se puede eliminar el estudiante porque tiene registros asociados
        }
    }
}
