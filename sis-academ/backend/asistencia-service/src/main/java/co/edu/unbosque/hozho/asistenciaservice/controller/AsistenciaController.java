package co.edu.unbosque.hozho.asistenciaservice.controller;

import co.edu.unbosque.hozho.asistenciaservice.model.Asistencia;
import co.edu.unbosque.hozho.asistenciaservice.service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaServ;

    public AsistenciaController() {
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Asistencia asistencia) {
        int status = asistenciaServ.create(asistencia);
        if (status == 1) return new ResponseEntity<>("No se debe proporcionar un ID al crear una asistencia", HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>("Asistencia creada exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Asistencia>> getAll() {
        List<Asistencia> asistencias = asistenciaServ.getAll();
        if (asistencias.isEmpty()) return new ResponseEntity<>(asistencias, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(asistencias, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Asistencia asistencia) {
        int status = asistenciaServ.updateById(id, asistencia);
        if (status == 1) return new ResponseEntity<>("No existe una asistencia con ese ID", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>("Asistencia actualizada exitosamente", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        int status = asistenciaServ.deleteById(id);
        if (status == 1) return new ResponseEntity<>("No existe una asistencia con ese ID", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>("Asistencia eliminada exitosamente", HttpStatus.OK);
    }
}
