package co.edu.unbosque.hozho.materia_service.controller;


import co.edu.unbosque.hozho.materia_service.model.Materia;
import co.edu.unbosque.hozho.materia_service.repository.MateriaRepository;
import co.edu.unbosque.hozho.materia_service.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materias")

public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    public MateriaController() {

    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> create(@RequestBody Materia materia) {
        int status = materiaService.create(materia);
        if (status == 1) return new ResponseEntity<String>("No se debe proporcionar un ID al crear Materia", HttpStatus.NOT_ACCEPTABLE);
        else if (status == 2) return new ResponseEntity<String>("Ya existe una materia con ese nombre", HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<String>("Materia creado exitosamente", HttpStatus.CREATED);

    }

    @GetMapping(path = "/getall")
    public ResponseEntity<List<Materia>> getAll(){
        List<Materia> materia =  materiaService.getAll();
        if (materia.isEmpty()) {
            return new ResponseEntity<List<Materia>>(materia, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Materia>>(materia, HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Materia materia){
        int status = materiaService.updateById(id, materia);
        if (status == 1) return new ResponseEntity<String>("No existe una materia con ese ID", HttpStatus.NOT_FOUND);

        return new ResponseEntity<String>("Materia actualizado exitosamente", HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        int status = materiaService.deleteById(id);
        if (status == 1) return new ResponseEntity<String>("No existe una materia con ese ID", HttpStatus.NOT_FOUND);
        else if (status == 2) return new ResponseEntity<String>("No se puede eliminar la materia porque tiene actividades asociadas", HttpStatus.CONFLICT);

        return new ResponseEntity<String>("Materia eliminada exitosamente", HttpStatus.OK);
    }
}
