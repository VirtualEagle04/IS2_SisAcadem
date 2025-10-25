package co.edu.unbosque.hozho.asistenciaservice.repository;

import co.edu.unbosque.hozho.asistenciaservice.model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia,Long> {

    // Encontrar asistencias por estudiante
    List<Asistencia> findByIdEstudiante(Long idEstudiante);

    // Encontrar asistencias por clase
    List<Asistencia> findByIdClase(Long idClase);

    // Encontrar asistencias por estado
    List<Asistencia> findByEstado(String estado);

    // Existe una asistencia por estudiante
    boolean existsByIdEstudiante(Long idEstudiante);

    // Existe una asistencias por clase
    boolean existsByIdClase(Long idClase);

    // Existe una asistencia por estado
    boolean existsByEstado(String estado);

}
