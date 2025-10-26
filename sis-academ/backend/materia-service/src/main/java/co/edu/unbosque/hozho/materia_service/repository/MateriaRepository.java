package co.edu.unbosque.hozho.materia_service.repository;

import co.edu.unbosque.hozho.materia_service.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MateriaRepository extends JpaRepository<Materia,Long> {

    // Encontrar materia por docente
    Optional<Materia> findByIdDocente(Long idDocente);

    // Encontrar materia por nombre
    Optional<Materia> findByNombre(String nombre);

    // Existe una materia por docente
    boolean existsByIdDocente(Long idDocente);

    // Existe una materia por nombre
    boolean existsByNombre(String nombre);

}
