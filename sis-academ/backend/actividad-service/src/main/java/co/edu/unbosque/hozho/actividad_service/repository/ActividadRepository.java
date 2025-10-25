package co.edu.unbosque.hozho.actividad_service.repository;


import co.edu.unbosque.hozho.actividad_service.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad,Long> {

    // Encontrar actividad por materia
    Optional<Actividad> findByIdMateria(Long idMateria);

    // Encontrar actividad por nombre
    Optional<Actividad> findByNombre(String nombre);

    // Existe una actividad por materia
    boolean existsByIdMateria(Long idMateria);

    // Existe una actividad por nombre
    boolean existsByNombre(String nombre);
}
