package co.edu.unbosque.hozho.matriculaservice.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "matricula")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private Long idMatricula;
    @Column(name = "id_estudiante")
    private Long idEstudiante;
    @Column(name = "id_periodo")
    private Long idPeriodo;
    @Column(name = "fecha_matricula", updatable = false, insertable = false)
    private LocalDate fechaMatricula;
    @Column(columnDefinition = "ENUM('Activa', 'Inactiva', 'Retirado')")
    private String estado;

    public Matricula() {
    }

    public Matricula(String estado, LocalDate fechaMatricula, Long idEstudiante, Long idMatricula, Long idPeriodo) {
        this.estado = estado;
        this.fechaMatricula = fechaMatricula;
        this.idEstudiante = idEstudiante;
        this.idMatricula = idMatricula;
        this.idPeriodo = idPeriodo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(LocalDate fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Long getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(Long idMatricula) {
        this.idMatricula = idMatricula;
    }

    public Long getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Long idPeriodo) {
        this.idPeriodo = idPeriodo;
    }
}
