package co.edu.unbosque.hozho.horarioservice.controller;

import co.edu.unbosque.hozho.horarioservice.model.PeriodoAcademico;
import co.edu.unbosque.hozho.horarioservice.service.PeriodoAcademicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/periodos-academicos")
public class PeriodoAcademicoController {

    @Autowired
    private PeriodoAcademicoService periodoAcademicoServ;

    public PeriodoAcademicoController() {
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody PeriodoAcademico periodoAcademico) {
        int status = periodoAcademicoServ.create(periodoAcademico);
        if (status == 1) return new ResponseEntity<>("No se debe proporcionar un ID al crear un periodo academico", HttpStatus.NOT_ACCEPTABLE);
        else if (status == 2) return new ResponseEntity<>("Ya existe un periodo academico con ese nombre", HttpStatus.NOT_ACCEPTABLE);
        else if (status == 3) return new ResponseEntity<>("Ya existe un periodo academico con esa fecha de inicio", HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>("Periodo Academico creado exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<PeriodoAcademico>> getAll(){
        List<PeriodoAcademico> periodosAcademicos = periodoAcademicoServ.getAll();
        if (periodosAcademicos.isEmpty()) return new ResponseEntity<>(periodosAcademicos, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(periodosAcademicos, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody PeriodoAcademico periodoAcademico) {
        int status = periodoAcademicoServ.updateById(id, periodoAcademico);
        if (status == 1) return new ResponseEntity<>("No existe un periodo academico con ese ID", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>("Periodo Academico actualizado exitosamente", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        int status = periodoAcademicoServ.deleteById(id);
        if (status == 1) return new ResponseEntity<>("No existe un periodo academico con ese ID", HttpStatus.NOT_FOUND);
        else if (status == 2) return new ResponseEntity<>("No se puede eliminar el periodo academico porque tiene registros asociados", HttpStatus.CONFLICT);

        return new ResponseEntity<>("Periodo Academico eliminado exitosamente", HttpStatus.OK);
    }
}
