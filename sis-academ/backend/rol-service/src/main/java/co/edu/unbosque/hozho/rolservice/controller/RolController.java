package co.edu.unbosque.hozho.rolservice.controller;

import co.edu.unbosque.hozho.rolservice.model.Rol;
import co.edu.unbosque.hozho.rolservice.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private RolService rolServ;

    public RolController() {
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Rol rol){
        int status = rolServ.create(rol);
        if (status == 1) return new ResponseEntity<>("No se debe proporcionar un ID al crear un rol", HttpStatus.NOT_ACCEPTABLE);
        else if (status == 2) return new ResponseEntity<>("Ya existe un rol con ese nombre", HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>("Rol creado exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Rol>> getAll(){
        List<Rol> roles = rolServ.getAll();
        if (roles.isEmpty()) return new ResponseEntity<>(roles, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Rol rol){
        int status = rolServ.updateById(id, rol);
        if (status == 1) return new ResponseEntity<>("No existe un rol con ese ID", HttpStatus.NOT_FOUND);
        else if (status == 2) return new ResponseEntity<>("Ya existe un rol con ese nombre", HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>("Rol actualizado exitosamente", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        int status = rolServ.deleteById(id);
        if (status == 1) return new ResponseEntity<>("No existe un rol con ese ID", HttpStatus.NOT_FOUND);
        else if (status == 2) return new ResponseEntity<>("No se puede eliminar el rol porque tiene registros asociados", HttpStatus.CONFLICT);

        return new ResponseEntity<>("Rol eliminado exitosamente", HttpStatus.OK);
    }
}
