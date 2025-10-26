package co.edu.unbosque.hozho.actividad_service.service;

import co.edu.unbosque.hozho.actividad_service.model.Actividad;
import co.edu.unbosque.hozho.actividad_service.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActividadService implements CRUDOperations<Actividad> {

    @Autowired
    private ActividadRepository actividadRepository;

    public ActividadService() {
    }

    @Override
    public int create(Actividad data) {
        if (data.getIdActividad() !=null) return 1; // No se debe proporcionar un ID al crear una actividad
        else if(actividadRepository.existsByNombre(data.getNombre())) return 2; // Ya existe una actividad con ese nombre

        actividadRepository.save(data);
        return 0;
    }

    @Override
    public List<Actividad> getAll() {
        return actividadRepository.findAll();
    }

    @Override
    public int deleteById(Long id) {
        Optional<Actividad> found = actividadRepository.findById(id);
        if (found.isEmpty()) return 1; // No existe una actividad con ese ID

        try {
            actividadRepository.delete(found.get());
            return 0;
        } catch (DataIntegrityViolationException e) {
            return 2; // No se puede eliminar la actividad porque hay registros asociados
        }
    }

    @Override
    public int updateById(Long id, Actividad data) {
        Optional<Actividad> found = actividadRepository.findById(id);
        if (found.isEmpty()) return 1; // No existe una actividad con ese ID
        else if(actividadRepository.existsByNombre(data.getNombre())) return 2; // Ya existe una actividad con ese nombre

        Actividad actividad = found.get();
        actividad.setIdMateria(data.getIdMateria());
        actividad.setNombre(data.getNombre());
        actividad.setDescripcion(data.getDescripcion());
        actividad.setPorcentaje(data.getPorcentaje());

        actividadRepository.save(actividad);
        return 0;
    }
}
