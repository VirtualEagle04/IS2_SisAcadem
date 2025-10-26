package co.edu.unbosque.hozho.horarioservice.service;

import co.edu.unbosque.hozho.horarioservice.model.PeriodoAcademico;
import co.edu.unbosque.hozho.horarioservice.repository.PeriodoAcademicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeriodoAcademicoService implements CRUDOperations<PeriodoAcademico>{

    @Autowired
    private PeriodoAcademicoRepository periodoAcademicoRepo;

    public PeriodoAcademicoService() {
    }

    @Override
    public int create(PeriodoAcademico data) {
        if (data.getIdPeriodo() != null) return 1; // No se debe proporcionar un ID al crear un periodo academico
        else if (periodoAcademicoRepo.existsByNombre(data.getNombre())) return 2; // Ya existe un periodo academico con ese nombre
        else if (periodoAcademicoRepo.existsByFechaInicio(data.getFechaInicio())) return 3; // Ya existe un periodo academico con esa fecha de inicio

        periodoAcademicoRepo.save(data);
        return 0;
    }

    @Override
    public List<PeriodoAcademico> getAll() {
        return periodoAcademicoRepo.findAll();
    }

    @Override
    public int updateById(Long id, PeriodoAcademico data) {
        Optional<PeriodoAcademico> found = periodoAcademicoRepo.findById(id);

        if (found.isEmpty()) return 1; // No existe un periodo academico con ese ID
        else if (periodoAcademicoRepo.existsByNombre(data.getNombre())) return 2; // Ya existe un periodo academico con ese nombre
        else if (periodoAcademicoRepo.existsByFechaInicio(data.getFechaInicio())) return 3; // Ya existe un periodo academico con esa fecha de inicio


        PeriodoAcademico pa = found.get();
        pa.setNombre(data.getNombre());
        pa.setFechaInicio(data.getFechaInicio());
        pa.setFechaFin(data.getFechaFin());

        periodoAcademicoRepo.save(pa);
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        Optional<PeriodoAcademico> found = periodoAcademicoRepo.findById(id);
        if (found.isEmpty()) {
            return 1; // No existe un periodo academico con ese ID
        }

        try {
            periodoAcademicoRepo.delete(found.get());
            return 0;
        } catch (DataIntegrityViolationException e) {
            return 2; // No se puede eliminar el periodo academico porque tiene registros asociados
        }
    }
}
