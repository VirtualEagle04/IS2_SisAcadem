package co.edu.unbosque.hozho.nota_service.repository;

import co.edu.unbosque.hozho.nota_service.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Long> {

    // Encontrar notas por actividad
    List<Nota> findByIdActividad(Long idActividade);

    // Encontrar notas por estudiante
    List<Nota> findByIdEstudiante(Long idEstudiante);

    // Encontrar notas por periodo
    List<Nota> findByIdPeriodo(Long idPeriodo);

    // Encontrar notas por calificacion
    List<Nota> findByCalificacion(Float calificacion);

    // Existe nota por actividad
    boolean existsByIdActividad(Long idActividad);

    // Existe nota por estudiante
    boolean existsByIdEstudiante(Long idEstudiante);

    // Existe nota por periodo
    boolean existsByIdPeriodo(Long idPeriodo);

    // Existe nota por calificacion
    boolean existsByCalificacion(Float calificacion);
}
