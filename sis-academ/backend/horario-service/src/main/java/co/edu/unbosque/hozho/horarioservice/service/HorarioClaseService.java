package co.edu.unbosque.hozho.horarioservice.service;

import co.edu.unbosque.hozho.horarioservice.model.HorarioClase;
import co.edu.unbosque.hozho.horarioservice.repository.HorarioClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class HorarioClaseService implements CRUDOperations<HorarioClase>{

    @Autowired
    private HorarioClaseRepository horarioClaseRepo;

    public HorarioClaseService() {
    }

    private boolean hayConflictoHorario(LocalTime inicio1, LocalTime fin1, LocalTime inicio2, LocalTime fin2) {
        return (inicio1.isBefore(fin2) && fin1.isAfter(inicio2));
    }

    @Override
    public int create(HorarioClase data) {
        if (data.getIdHorario() != null) return 1; // No se debe proporcionar un ID al crear un horario clase
        else if (!data.getHoraFin().isAfter(data.getHoraInicio())) return 2; // La hora de fin debe ser posterior a la hora de inicio

        List<HorarioClase> horariosGrado = horarioClaseRepo.findByIdGrado(data.getIdGrado());
        for (HorarioClase h : horariosGrado) {
            if (h.getDiaSemana().equals(data.getDiaSemana())) {
                if (hayConflictoHorario(data.getHoraInicio(), data.getHoraFin(), h.getHoraInicio(), h.getHoraFin())) {
                    return 3; // El grado ya tiene clase en ese horario
                }
            }
        }

        List<HorarioClase> horariosSalon = horarioClaseRepo.findBySalon(data.getSalon());
        for (HorarioClase h : horariosSalon) {
            if (h.getDiaSemana().equals(data.getDiaSemana())) {
                if (hayConflictoHorario(data.getHoraInicio(), data.getHoraFin(), h.getHoraInicio(), h.getHoraFin())) {
                    return 4; // El salon ya está ocupado en ese horario
                }
            }
        }

        List<HorarioClase> horariosMateria = horarioClaseRepo.findByIdMateria(data.getIdMateria());
        for (HorarioClase h : horariosMateria) {
            if (h.getDiaSemana().equals(data.getDiaSemana())) {
                if (hayConflictoHorario(data.getHoraInicio(), data.getHoraFin(), h.getHoraInicio(), h.getHoraFin())) {
                    return 5; // El docente ya tiene clase en ese horario
                }
            }
        }

        horarioClaseRepo.save(data);
        return 0;
    }

    @Override
    public List<HorarioClase> getAll() {
        return horarioClaseRepo.findAll();
    }

    @Override
    public int updateById(Long id, HorarioClase data) {
        Optional<HorarioClase> found = horarioClaseRepo.findById(id);
        if (found.isEmpty()) return 1; // No existe un horario clase con ese ID
        else if (!data.getHoraFin().isAfter(data.getHoraInicio())) return 2; // La hora de fin debe ser posterior a la hora de inicio

        HorarioClase hc = found.get();
        hc.setIdGrado(data.getIdGrado());
        hc.setIdMateria(data.getIdMateria());
        hc.setDiaSemana(data.getDiaSemana());
        hc.setHoraInicio(data.getHoraInicio());
        hc.setHoraFin(data.getHoraFin());
        hc.setSalon(data.getSalon());

        horarioClaseRepo.save(hc);
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        Optional<HorarioClase> found = horarioClaseRepo.findById(id);
        if (found.isEmpty()) {
            return 1; // No existe un horario clase con ese ID
        }

        try {
            horarioClaseRepo.delete(found.get());
            return 0;
        } catch (DataIntegrityViolationException e) {
            return 2; // No se puede eliminar el horario clase porque tiene registros asociados
        }
    }
}
