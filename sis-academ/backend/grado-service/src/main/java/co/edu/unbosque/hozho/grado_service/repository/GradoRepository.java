package co.edu.unbosque.hozho.grado_service.repository;

import co.edu.unbosque.hozho.grado_service.model.Grado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradoRepository extends JpaRepository<Grado, Long> {

    // Encontrar Grado por nombre de Grado
    Optional<Grado> findByNombre(String grado);

    // Encontrar Grado por ID
    List<Grado> findByIdGrado(Long idGrado);

    // Existe un Grado por nombre de Grado (nombre)
    boolean existsByNombre(String nombre);

    // Existe un Grado por id de grado (idGrado)
    boolean existsByIdGrado(Long idGrado);
}
