package co.edu.unbosque.hozho.asistenciaservice.service;

import co.edu.unbosque.hozho.asistenciaservice.model.Asistencia;
import co.edu.unbosque.hozho.asistenciaservice.repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsistenciaService implements CRUDOperations<Asistencia>{

    @Autowired
    private AsistenciaRepository asistenciaRepo;

    public AsistenciaService() {
    }

    @Override
    public int create(Asistencia data) {
        if (data.getIdAsistencia() != null) return 1; // No se debe proporcionar un ID al crear una asistencia

        asistenciaRepo.save(data);
        return 0;
    }

    @Override
    public List<Asistencia> getAll() {
        return asistenciaRepo.findAll();
    }

    @Override
    public int updateById(Long id, Asistencia data) {
        Optional<Asistencia> found =  asistenciaRepo.findById(id);
        if (found.isEmpty()) return 1; // No existe una asistencia con ese ID

        Asistencia a = found.get();
        a.setIdEstudiante(data.getIdEstudiante());
        a.setIdClase(data.getIdClase());
        a.setEstado(data.getEstado());
        a.setObservacion(data.getObservacion());

        asistenciaRepo.save(a);
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        Optional<Asistencia> found =  asistenciaRepo.findById(id);
        if (found.isEmpty()) return 1; // No existe una asistencia con ese ID

        try {
            asistenciaRepo.deleteById(id);
            return 0;
        } catch (DataIntegrityViolationException e) {
            return 2; // No se puede eliminar la asistencia porque tiene registros asociados
        }
    }
}
