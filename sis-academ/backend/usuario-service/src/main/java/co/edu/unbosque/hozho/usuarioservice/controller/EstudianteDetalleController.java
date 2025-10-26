package co.edu.unbosque.hozho.usuarioservice.controller;

import co.edu.unbosque.hozho.usuarioservice.model.EstudianteDetalle;
import co.edu.unbosque.hozho.usuarioservice.service.EstudianteDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios/estudiantes-detalles")
public class EstudianteDetalleController {

    @Autowired
    private EstudianteDetalleService estudianteDetalleServ;

    public EstudianteDetalleController() {
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody EstudianteDetalle estudianteDetalle) {
        int status =  estudianteDetalleServ.create(estudianteDetalle);
        if (status == 1) return new ResponseEntity<>("No se debe proporcionar ID al crear un estudiante", HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>("Detalle Estudiante creado exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<EstudianteDetalle>> getAll() {
        List<EstudianteDetalle> estudiantesDetalles = estudianteDetalleServ.getAll();
        if (estudiantesDetalles.isEmpty()) return new ResponseEntity<>(estudiantesDetalles, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(estudiantesDetalles, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody EstudianteDetalle estudianteDetalle) {
        int status = estudianteDetalleServ.updateById(id, estudianteDetalle);
        if (status == 1) return new ResponseEntity<>("No existe un estudiante con ese ID", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>("Detalle Estudiante actualizado exitosamente", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        int status = estudianteDetalleServ.deleteById(id);
        if (status == 1) return new ResponseEntity<>("No existe un estudiante con ese ID", HttpStatus.NOT_FOUND);
        else if (status == 2) return new ResponseEntity<>("No se puede eliminar el estudiante porque tiene registros asociados", HttpStatus.CONFLICT);

        return new ResponseEntity<>("Detalle Estudiante eliminado exitosamente", HttpStatus.OK);
    }
}
