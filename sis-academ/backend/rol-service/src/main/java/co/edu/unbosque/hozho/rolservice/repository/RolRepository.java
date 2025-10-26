package co.edu.unbosque.hozho.rolservice.repository;

import co.edu.unbosque.hozho.rolservice.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    // Encontrar rol por nombre
    Optional<Rol> findByNombre(String nombre);

    // Existe un rol por nombre
    boolean existsByNombre(String nombre);
}
