package co.edu.unbosque.hozho.horarioservice.repository;

import co.edu.unbosque.hozho.horarioservice.model.PeriodoAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PeriodoAcademicoRepository extends JpaRepository<PeriodoAcademico,Long> {

    // Encontrar periodo por nombre (nombre)
    Optional<PeriodoAcademico> findByNombre(String nombre);

    // Existe un periodo por nombre (nombre)
    boolean existsByNombre(String nombre);

    // Existe un periodo por fecha de inicio (fechaInicio)
    boolean existsByFechaInicio(LocalDate fechaInicio);
}
