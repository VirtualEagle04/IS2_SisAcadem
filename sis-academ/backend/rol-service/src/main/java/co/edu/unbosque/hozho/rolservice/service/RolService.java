package co.edu.unbosque.hozho.rolservice.service;

import co.edu.unbosque.hozho.rolservice.model.Rol;
import co.edu.unbosque.hozho.rolservice.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService implements CRUDOperations<Rol>{

    @Autowired
    private RolRepository rolRepo;

    public RolService() {
    }

    @Override
    public int create(Rol data) {
        if (data.getIdRol() != null) return 1; // No se debe proporcionar un ID al crear un rol
        else if (rolRepo.existsByNombre(data.getNombre())) return 2; // Ya existe un rol con ese nombre

        rolRepo.save(data);
        return 0;
    }

    @Override
    public List<Rol> getAll() {
        return rolRepo.findAll();
    }

    @Override
    public int deleteById(Long id) {
        Optional<Rol> found = rolRepo.findById(id);
        if (found.isEmpty()) {
            return 1; // No existe un rol con ese ID
        }

        try {
            rolRepo.delete(found.get());
            return 0;
        } catch (DataIntegrityViolationException e) {
            return 2; // No se puede eliminar el rol porque tiene registros asociados
        }
    }

    @Override
    public int updateById(Long id, Rol data) {
        Optional<Rol> found = rolRepo.findById(id);
        if (found.isEmpty()) return 1; // No existe un rol con ese ID
        else if (rolRepo.existsByNombre(data.getNombre())) return 2; // Ya existe un rol con ese nombre

        Rol r = found.get();
        r.setNombre(data.getNombre());
        r.setSoloLectura(data.isSoloLectura());
        r.setRol(data.isRol());
        r.setUsuario(data.isUsuario());
        r.setGrado(data.isGrado());
        r.setCurso(data.isCurso());
        r.setMateria(data.isMateria());
        r.setActividad(data.isActividad());
        r.setHorario(data.isHorario());
        r.setNota(data.isNota());
        r.setAsistencia(data.isAsistencia());
        r.setMatricula(data.isMatricula());

        rolRepo.save(r);
        return 0;
    }
}
