package co.edu.unbosque.hozho.estudianteservice.service;

import co.edu.unbosque.hozho.estudianteservice.model.Estudiante;
import co.edu.unbosque.hozho.estudianteservice.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (estudianteRepo.existsById(data.getIdEstudiante())) return 1; // Ya existe un Estudiante con ese ID
        else if (estudianteRepo.existsByUsuario(data.getUsuario())) return 2; // Ya existe un Estudiante con ese nombre de usuario
        else if (estudianteRepo.existsByDocIdentidad(data.getDocIdentidad())) return 3; // Ya existe un Estudiante con ese documento de identidad

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
        if (found.isPresent()) {
            estudianteRepo.delete(found.get());
            return 0;
        }
        return 1; // No existe un Estudiante con ese ID
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

            estudianteRepo.save(e);
            return 0;
        }
        return 1; // No existe un Estudiante con ese ID
    }
}
