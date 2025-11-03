package co.edu.unbosque.hozho.horarioservice.controller;

import co.edu.unbosque.hozho.horarioservice.model.HorarioClase;
import co.edu.unbosque.hozho.horarioservice.service.HorarioClaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios/horarios-clases")
public class HorarioClaseController {

    @Autowired
    private HorarioClaseService horarioClaseServ;

    public HorarioClaseController() {
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody HorarioClase horarioClase) {
        int status = horarioClaseServ.create(horarioClase);
        if (status == 1) return new ResponseEntity<>("No se debe proporcionar un ID al crear un horario clase", HttpStatus.NOT_ACCEPTABLE);
        else if (status == 2) return new ResponseEntity<>("La hora de fin debe ser posterior a la hora de inicio", HttpStatus.NOT_ACCEPTABLE);
        else if (status == 3) return new ResponseEntity<>("El grado ya tiene clase en ese horario", HttpStatus.NOT_ACCEPTABLE);
        else if (status == 4) return new ResponseEntity<>("El salon ya está ocupado en ese horario", HttpStatus.NOT_ACCEPTABLE);
        else if (status == 5) return new ResponseEntity<>("El docente ya tiene clase en ese horario", HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>("Horario Clase creado exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<HorarioClase>> getAll() {
        List<HorarioClase> horariosClases = horarioClaseServ.getAll();
        if (horariosClases.isEmpty()) return new ResponseEntity<>(horariosClases, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(horariosClases, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody HorarioClase horarioClase) {
        int status = horarioClaseServ.updateById(id, horarioClase);
        if (status == 1) return new ResponseEntity<>("No existe un horario clase con ese ID", HttpStatus.NOT_FOUND);
        else if (status == 2) return new ResponseEntity<>("La hora de fin debe ser posterior a la hora de inicio", HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>("Horario Clase actualizado exitosamente", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<String> delete(@PathVariable Long id) {
        int status = horarioClaseServ.deleteById(id);
        if (status == 1) return new ResponseEntity<>("No existe un horario clase con ese ID", HttpStatus.NOT_FOUND);
        else if (status == 2) return new ResponseEntity<>("No se puede eliminar el horario clase porque tiene registros asociados", HttpStatus.CONFLICT);

        return new ResponseEntity<>("Horario Clase eliminado exitosamente", HttpStatus.OK);
    }
}
