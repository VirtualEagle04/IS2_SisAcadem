package co.edu.unbosque.hozho.nota_service.controller;

import co.edu.unbosque.hozho.nota_service.model.Nota;
import co.edu.unbosque.hozho.nota_service.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notas")
public class NotaController {

    @Autowired
    private NotaService notaServ;

    public NotaController() {
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Nota nota) {
        int status = notaServ.create(nota);
        if (status == 1) return new ResponseEntity<>("No se debe proporcionar un ID al crear una nota", HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>("Nota creada exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Nota>> getAll() {
        List<Nota> notas =  notaServ.getAll();
        if (notas.isEmpty()) return new ResponseEntity<>(notas, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(notas, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Nota nota) {
        int status = notaServ.updateById(id, nota);
        if (status == 1) return new ResponseEntity<>("No exista una nota con ese ID",  HttpStatus.NOT_FOUND);

        return new ResponseEntity<>("Nota actualizada exitosamente", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        int status = notaServ.deleteById(id);
        if (status == 1) return new ResponseEntity<>("No exista una nota con ese ID",  HttpStatus.NOT_FOUND);
        else if (status == 2) return new ResponseEntity<>("No se puede eliminar la nota porque tiene registros asociados", HttpStatus.CONFLICT);

        return new ResponseEntity<>("Nota eliminada exitosamente", HttpStatus.OK);
    }
}
