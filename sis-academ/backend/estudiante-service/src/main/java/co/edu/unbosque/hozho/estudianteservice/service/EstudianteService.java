package co.edu.unbosque.hozho.estudianteservice.service;

import co.edu.unbosque.hozho.estudianteservice.model.Estudiante;
import co.edu.unbosque.hozho.estudianteservice.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService implements CRUDOperations<Estudiante> {

    @Autowired
    private EstudianteRepository estudianteRepo;

    public EstudianteService() {
    }

    @Override
    public int create(Estudiante data) {
        if (data.getIdEstudiante() != null) return 1; // No se debe proporcionar un ID al crear un estudiante
        else if (estudianteRepo.existsByUsuario(data.getUsuario())) return 2; // Ya existe un Estudiante con ese nombre de usuario
        else if (estudianteRepo.existsByDocIdentidad(data.getDocIdentidad())) return 3; // Ya existe un Estudiante con ese documento de identidad

        // Si en la petición no viene con fecha de ingreso
        if (data.getFechaIngreso() == null) {
            data.setFechaIngreso(LocalDate.now());
        }

        estudianteRepo.save(data);
        return 0;
    }

    @Override
    public List<Estudiante> getAll() {
        return estudianteRepo.findAll();
    }

    @Override
    public int deleteById(Long id) {
        Optional<Estudiante> found = estudianteRepo.findById(id);
        if (found.isEmpty()) {
            return 1; // No existe un Estudiante con ese ID
        }

        try {
            estudianteRepo.delete(found.get());
            return 0;
        } catch (DataIntegrityViolationException e) {
            return 2; // No se puede eliminar el Estudiante porque tiene Notas asociadas
        }
    }

    @Override
    public int updateById(Long id, Estudiante data) {
        Optional<Estudiante> found = estudianteRepo.findById(id);
        if (found.isPresent()) {
            Estudiante e = found.get();
            e.setIdCurso(data.getIdCurso());
            e.setIdAcudiente(data.getIdAcudiente());
            e.setUsuario(data.getUsuario());
            e.setClave(data.getClave());
            e.setNombres(data.getNombres());
            e.setApellidos(data.getApellidos());
            e.setFechaNacimiento(data.getFechaNacimiento());
            e.setEdad(data.getEdad());
            e.setSexo(data.getSexo());
            e.setDocIdentidad(data.getDocIdentidad());
            e.setCiudadNacimiento(data.getCiudadNacimiento());
            e.setTelefono(data.getTelefono());
            //e.setFechaIngreso(data.getFechaIngreso());
            e.setEstado(data.getEstado());

            estudianteRepo.save(e);
            return 0;
        }
        return 1; // No existe un Estudiante con ese ID
    }
}
