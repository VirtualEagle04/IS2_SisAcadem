package co.edu.unbosque.hozho.usuarioservice.repository;

import co.edu.unbosque.hozho.usuarioservice.model.EstudianteDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteDetalleRepository extends JpaRepository<EstudianteDetalle,Long> {

    // Encontrar estudiantes por curso
    List<EstudianteDetalle> findByIdCurso(Long idCurso);

    // Encontrar estudiante por acudiente
    Optional<EstudianteDetalle> findByIdAcudiente(Long idAcudiente);

    // Existe un estudiante por acudiente
    boolean existsByIdAcudiente(Long idAcudiente);

}
