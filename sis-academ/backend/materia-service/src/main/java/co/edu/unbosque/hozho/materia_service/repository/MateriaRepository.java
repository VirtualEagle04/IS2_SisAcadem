package co.edu.unbosque.hozho.materia_service.repository;

import co.edu.unbosque.hozho.materia_service.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MateriaRepository extends JpaRepository<Materia,Long> {

    Optional<Materia> findByIdDocente(Long idDocente);

    Optional<Materia> findByNombre(String nombre);

    boolean existsByIdDocente(Long idDocente);

    boolean existsByNombre(String nombre);

}
