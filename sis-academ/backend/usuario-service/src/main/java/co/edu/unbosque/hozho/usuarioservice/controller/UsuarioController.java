package co.edu.unbosque.hozho.usuarioservice.controller;

import co.edu.unbosque.hozho.usuarioservice.model.Usuario;
import co.edu.unbosque.hozho.usuarioservice.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioServ;

    public UsuarioController() {
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Usuario usuario) {
        int status =  usuarioServ.create(usuario);
        if (status == 1) return new ResponseEntity<>("No se debe proporcionar ID al crear un usuario", HttpStatus.NOT_ACCEPTABLE);
        else if (status == 2) return new ResponseEntity<>("Ya existe un usuario con ese nombre de usuario", HttpStatus.NOT_ACCEPTABLE);
        else if (status == 3) return new ResponseEntity<>("Ya existe un usuario con ese documento de identidad", HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>("Usuario creado exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Usuario>> getAll() {
        List<Usuario> usuarios = usuarioServ.getAll();
        if (usuarios.isEmpty()) return new ResponseEntity<>(usuarios, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        int status = usuarioServ.updateById(id, usuario);
        if (status == 1) return new ResponseEntity<>("No existe un usuario con ese ID", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>("Usuario actualizado exitosamente", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        int status = usuarioServ.deleteById(id);
        if (status == 1) return new ResponseEntity<>("No existe un usuario con ese ID", HttpStatus.NOT_FOUND);
        else if (status == 2) return new ResponseEntity<>("No se puede eliminar el usuario porque tiene registros asociados", HttpStatus.CONFLICT);

        return new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.OK);
    }
}
