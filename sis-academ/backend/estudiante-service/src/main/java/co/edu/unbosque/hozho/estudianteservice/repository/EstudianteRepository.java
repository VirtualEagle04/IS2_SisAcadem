package co.edu.unbosque.hozho.estudianteservice.repository;

import co.edu.unbosque.hozho.estudianteservice.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    // Encontrar estudiante por nombre de usuario (usuario)
    Optional<Estudiante> findByUsuario(String usuario);

    // Encontrar estudiantes por curso (idCurso)
    List<Estudiante> findByIdCurso(Long idCurso);

    // Encontrar estudiante por documento de identidad (docIdentidad)
    Optional<Estudiante> findByDocIdentidad(Long docIdentidad);

    // Existe un estudiante por nombre de usuario (usuario)
    boolean existsByUsuario(String usuario);

    // Existe un estudiante por documento de identidad (docIdentidad)
    boolean existsByDocIdentidad(Long docIdentidad);
}
