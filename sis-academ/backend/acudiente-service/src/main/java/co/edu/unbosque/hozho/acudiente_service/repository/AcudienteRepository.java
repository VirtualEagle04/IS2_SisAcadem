package co.edu.unbosque.hozho.acudienteservice.repository;

import co.edu.unbosque.hozho.estudianteservice.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcudienteRepository extends JpaRepository<Estudiante, Long> {

    // Encontrar acudiente por nombre de usuario (usuario)
    Optional<Acudiente> findByUsuario(String usuario);

    // Encontrar acudientes por curso (idCurso)
    List<Acudiente> findByIdCurso(Long idCurso);

    // Encontrar acudiente por documento de identidad (docIdentidad)
    Optional<Acudiente> findByDocIdentidad(Long docIdentidad);

    // Existe un acudiente por nombre de usuario (usuario)
    boolean existsByUsuario(String usuario);

    // Existe un acudiente por documento de identidad (docIdentidad)
    boolean existsByDocIdentidad(Long docIdentidad);
}
