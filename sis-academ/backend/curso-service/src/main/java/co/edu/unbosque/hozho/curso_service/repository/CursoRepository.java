package co.edu.unbosque.hozho.curso_service.repository;

import co.edu.unbosque.hozho.curso_service.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Encontrar Curso por nombre de Curso
    Optional<Curso> findByNombre(String curso);

    // Encontrar Curso por ID
    List<Curso> findByIdCurso(Long idCurso);

    // Existe un Curso por nombre de Curso (nombre)
    boolean existsByNombre(String nombre);

    // Existe un Curso por id de curso (idCurso)
    boolean existsByIdCurso(Long idCurso);
}
