package co.edu.unbosque.hozho.matriculaservice.controller;

import co.edu.unbosque.hozho.matriculaservice.model.Matricula;
import co.edu.unbosque.hozho.matriculaservice.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaServ;

    public MatriculaController() {
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Matricula matricula) {
        int status = matriculaServ.create(matricula);
        if (status == 1) return new ResponseEntity<>("No se debe proporcionar un ID al crear una matricula", HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>("Matricula creada exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Matricula>> getAll() {
        List<Matricula> matriculas =  matriculaServ.getAll();
        if (matriculas.isEmpty()) return new ResponseEntity<>(matriculas, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(matriculas, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,  @RequestBody Matricula matricula) {
        int status = matriculaServ.updateById(id, matricula);
        if (status == 1) return new ResponseEntity<>("No existe una matricula con ese ID", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>("Matricula actualizada exitosamente", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        int status = matriculaServ.deleteById(id);
        if (status == 1) return new ResponseEntity<>("No existe una matricula con ese ID", HttpStatus.NOT_FOUND);
        else if (status == 2) return new ResponseEntity<>("No se puede eliminar la matricula porque tiene registros asociados", HttpStatus.CONFLICT);

        return new ResponseEntity<>("Matricula eliminada exitosamente", HttpStatus.OK);
    }
}
