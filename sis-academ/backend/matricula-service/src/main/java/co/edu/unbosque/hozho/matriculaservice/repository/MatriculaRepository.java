package co.edu.unbosque.hozho.matriculaservice.repository;

import co.edu.unbosque.hozho.matriculaservice.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    // Encontrar matriculas por estudiante
    List<Matricula> findByIdEstudiante(Long idEstudiante);

    // Encontrar matriculas por periodo
    List<Matricula> findByIdPeriodo(Long idPeriodo);

    // Encontrar matriculas por estado
    List<Matricula> findByEstado(String estado);
}
