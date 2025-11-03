package co.edu.unbosque.hozho.usuarioservice.service;

import co.edu.unbosque.hozho.usuarioservice.model.Usuario;
import co.edu.unbosque.hozho.usuarioservice.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements CRUDOperations<Usuario>{

    @Autowired
    private UsuarioRepository usuarioRepo;

    public UsuarioService() {
    }

    @Override
    public int create(Usuario data) {
        if (data.getIdUsuario() != null) return 1; // No se debe proporcionar ID al crear un usuario
        else if (usuarioRepo.existsByUsuario(data.getUsuario())) return 2; // Ya existe un usuario con ese nombre de usuario
        else if (usuarioRepo.existsByDocIdentidad(data.getDocIdentidad())) return 3; // Ya existe un usuario con ese documento de identidad
        else if (usuarioRepo.existsByCorreo(data.getCorreo())) return 4; // Ya existe un usuario con ese correo electrónico

        usuarioRepo.save(data);
        return 0;
    }

    @Override
    public List<Usuario> getAll() {
        return usuarioRepo.findAll();
    }

    @Override
    public int updateById(Long id, Usuario data) {
        Optional<Usuario> found = usuarioRepo.findById(id);
        if (found.isEmpty()) return 1; // No existe un usuario con ese ID

        Usuario u = found.get();
        u.setIdRol(data.getIdRol());
        u.setUsuario(data.getUsuario());
        u.setClave(data.getClave());
        u.setNombres(data.getNombres());
        u.setApellidos(data.getApellidos());
        u.setFechaNacimiento(data.getFechaNacimiento());
        u.setEdad(data.getEdad());
        u.setSexo(data.getSexo());
        u.setDocIdentidad(data.getDocIdentidad());
        u.setCiudadNacimiento(data.getCiudadNacimiento());
        u.setTelefono(data.getTelefono());
        u.setCorreo(data.getCorreo());
        u.setFechaRegistro(data.getFechaRegistro());
        u.setActivo(data.isActivo());

        usuarioRepo.save(u);
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        Optional<Usuario> found = usuarioRepo.findById(id);
        if (found.isEmpty()) return 1; // No existe un usuario con ese ID

        try {
            usuarioRepo.delete(found.get());
            return 0;
        } catch (DataIntegrityViolationException e) {
            return 2; // No se puede eliminar el usuario porque tiene registros asociados
        }
    }
}
