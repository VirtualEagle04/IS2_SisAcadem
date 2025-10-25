package co.edu.unbosque.hozho.usuarioservice.repository;

import co.edu.unbosque.hozho.usuarioservice.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    // Encontrar usuario por nombre de usuario
    Optional<Usuario> findByUsuario(String usuario);

    // Encontrar usuarios por rol
    List<Usuario> findByIdRol(Long idRol);

    // Encontrar usuario por correo
    Optional<Usuario> findByCorreo(String correo);

    // Existe un usuario por nombre de usuario
    boolean existsByUsuario(String usuario);

    // Existe un usuario por documento de identidad
    boolean existsByDocIdentidad(Long docIdentidad);

    // Existe un usuario por correo
    boolean existsByCorreo(String correo);
}
