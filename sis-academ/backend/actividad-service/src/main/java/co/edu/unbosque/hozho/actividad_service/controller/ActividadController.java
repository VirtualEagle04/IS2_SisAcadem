package co.edu.unbosque.hozho.actividad_service.controller;

import co.edu.unbosque.hozho.actividad_service.model.Actividad;
import co.edu.unbosque.hozho.actividad_service.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actividades")

public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    public ActividadController() {}

    @PostMapping(path = "/create")
    public ResponseEntity<String> create(@RequestBody Actividad actividad) {
        int status = actividadService.create(actividad);
        if (status == 1) return new ResponseEntity<String>("No se debe proporcionar un ID al crear actividad", HttpStatus.NOT_ACCEPTABLE);
        else if (status == 2) return new ResponseEntity<String>("Ya existe una actividad con ese nombre", HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<String>("Actividad creado exitosamente", HttpStatus.CREATED);
    }

    @GetMapping(path = "/getall")
    public ResponseEntity<List<Actividad>> getAll(){
        List<Actividad> actividad =  actividadService.getAll();
        if (actividad.isEmpty()) {
            return new ResponseEntity<List<Actividad>>(actividad, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Actividad>>(actividad, HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Actividad actividad){
        int status = actividadService.updateById(id, actividad);
        if (status == 1) return new ResponseEntity<String>("No existe una Actividad con ese ID", HttpStatus.NOT_FOUND);

        return new ResponseEntity<String>("Actividad actualizado exitosamente", HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        int status = actividadService.deleteById(id);
        if (status == 1) return new ResponseEntity<String>("No existe una Actividad con ese ID", HttpStatus.NOT_FOUND);
        else if (status == 2) return new ResponseEntity<String>("No se puede eliminar la Actividad porque tiene notas asociadas", HttpStatus.CONFLICT);

        return new ResponseEntity<String>("Actividad eliminada exitosamente", HttpStatus.OK);
    }




}
