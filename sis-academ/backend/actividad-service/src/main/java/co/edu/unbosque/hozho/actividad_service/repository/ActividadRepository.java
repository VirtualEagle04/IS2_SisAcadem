package co.edu.unbosque.hozho.actividad_service.repository;


import co.edu.unbosque.hozho.actividad_service.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad,Long> {

    Optional<Actividad> findByIdMateria(Long idMateria);

    Optional<Actividad> findByNombre(String nombre);

    Optional<Actividad> findByPorcentaje(int porcentaje);

    boolean existsByIdMateria(Long idMateria);

    boolean existsByNombre(String nombre);
    
    boolean existsByPorcentaje(int porcentaje);
    
    
}
