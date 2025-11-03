package co.edu.unbosque.hozho.grado_service.controller;

import co.edu.unbosque.hozho.grado_service.model.Grado;
import co.edu.unbosque.hozho.grado_service.service.GradoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grados")
public class GradoController {

    @Autowired
    private GradoService gradoServ;

    public GradoController() {
    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> create(@RequestBody Grado grado){
        int status = gradoServ.create(grado);
        if (status == 1) return new ResponseEntity<String>("No se debe proporcionar un ID al crear un grado", HttpStatus.NOT_ACCEPTABLE);
        else if (status == 2) return new ResponseEntity<String>("Ya existe un Grado con ese nombre de grado", HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<String>("Grado creado exitosamente", HttpStatus.CREATED);
    }

    @GetMapping(path = "/getall")
    public ResponseEntity<List<Grado>> getAll(){
        List<Grado> grados =  gradoServ.getAll();
        if (grados.isEmpty()) {
            return new ResponseEntity<List<Grado>>(grados, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Grado>>(grados, HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Grado grado){
        int status = gradoServ.updateById(id, grado);
        if (status == 1) return new ResponseEntity<String>("No existe un Grado con ese ID", HttpStatus.NOT_FOUND);
        else if (status == 2) return new ResponseEntity<String>("Ya existe un Grado con ese nombre de grado", HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<String>("Grado actualizado exitosamente", HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        int status = gradoServ.deleteById(id);
        if (status == 1) return new ResponseEntity<String>("No existe un Grado con ese ID", HttpStatus.NOT_FOUND);

        return new ResponseEntity<String>("Grado eliminado exitosamente", HttpStatus.OK);
    }
}
