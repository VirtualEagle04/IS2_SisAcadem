package co.edu.unbosque.hozho.curso_service.controller;

import co.edu.unbosque.hozho.curso_service.model.Curso;
import co.edu.unbosque.hozho.curso_service.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoServ;

    public CursoController() {
    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> create(@RequestBody Curso curso){
        int status = cursoServ.create(curso);
        if (status == 1) return new ResponseEntity<String>("No se debe proporcionar un ID al crear un curso", HttpStatus.NOT_ACCEPTABLE);
        else if (status == 2) return new ResponseEntity<String>("Ya existe un Grado con ese nombre de curso", HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<String>("Curso creado exitosamente", HttpStatus.CREATED);
    }

    @GetMapping(path = "/getall")
    public ResponseEntity<List<Curso>> getAll(){
        List<Curso> cursos =  cursoServ.getAll();
        if (cursos.isEmpty()) {
            return new ResponseEntity<List<Curso>>(cursos, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Curso>>(cursos, HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Curso curso){
        int status = cursoServ.updateById(id, curso);
        if (status == 1) return new ResponseEntity<String>("No existe un Curso con ese ID", HttpStatus.NOT_FOUND);

        return new ResponseEntity<String>("Curso actualizado exitosamente", HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        int status = cursoServ.deleteById(id);
        if (status == 1) return new ResponseEntity<String>("No existe un Curso con ese ID", HttpStatus.NOT_FOUND);

        return new ResponseEntity<String>("Curso eliminado exitosamente", HttpStatus.OK);
    }
}
