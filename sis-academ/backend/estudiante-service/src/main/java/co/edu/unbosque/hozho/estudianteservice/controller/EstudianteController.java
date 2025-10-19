package co.edu.unbosque.hozho.estudianteservice.controller;

import co.edu.unbosque.hozho.estudianteservice.model.Estudiante;
import co.edu.unbosque.hozho.estudianteservice.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteServ;

    public EstudianteController() {
    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> create(@RequestBody Estudiante estudiante){
        int status = estudianteServ.create(estudiante);
        if (status == 1) return new ResponseEntity<String>("No se debe proporcionar un ID al crear un estudiante", HttpStatus.NOT_ACCEPTABLE);
        else if (status == 2) return new ResponseEntity<String>("Ya existe un Estudiante con ese nombre de usuario", HttpStatus.NOT_ACCEPTABLE);
        else if (status == 3) return new ResponseEntity<String>("Ya existe un Estudiante con ese documento de identidad", HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<String>("Estudiante creado exitosamente", HttpStatus.CREATED);
    }

    @GetMapping(path = "/getall")
    public ResponseEntity<List<Estudiante>> getAll(){
        List<Estudiante> estudiantes =  estudianteServ.getAll();
        if (estudiantes.isEmpty()) {
            return new ResponseEntity<List<Estudiante>>(estudiantes, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Estudiante>>(estudiantes, HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Estudiante estudiante){
        int status = estudianteServ.updateById(id, estudiante);
        if (status == 1) return new ResponseEntity<String>("No existe un Estudiante con ese ID", HttpStatus.NOT_FOUND);

        return new ResponseEntity<String>("Estudiante actualizado exitosamente", HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        int status = estudianteServ.deleteById(id);
        if (status == 1) return new ResponseEntity<String>("No existe un Estudiante con ese ID", HttpStatus.NOT_FOUND);
        else if (status == 2) return new ResponseEntity<String>("No se puede eliminar el Estudiante porque tiene Notas asociadas", HttpStatus.CONFLICT);

        return new ResponseEntity<String>("Estudiante eliminado exitosamente", HttpStatus.OK);
    }
}
