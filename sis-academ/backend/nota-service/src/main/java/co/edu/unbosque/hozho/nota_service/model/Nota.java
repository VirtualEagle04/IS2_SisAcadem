package co.edu.unbosque.hozho.nota_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "nota")
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota")
    private Long idNota;
    @Column(name = "id_actividad")
    private Long idActividad;
    @Column(name = "id_estudiante")
    private Long idEstudiante;
    @Column(name = "id_periodo")
    private Long idPeriodo;
    private Float calificacion;
    private String observacion;
    @Column(columnDefinition = "ENUM('Normal', 'Recuperación')")
    private String tipo;

    public Nota() {
    }

    public Nota(Float calificacion, Long idActividad, Long idEstudiante, Long idNota, Long idPeriodo, String observacion, String tipo) {
        this.calificacion = calificacion;
        this.idActividad = idActividad;
        this.idEstudiante = idEstudiante;
        this.idNota = idNota;
        this.idPeriodo = idPeriodo;
        this.observacion = observacion;
        this.tipo = tipo;
    }

    public Float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Float calificacion) {
        this.calificacion = calificacion;
    }

    public Long getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Long idActividad) {
        this.idActividad = idActividad;
    }

    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Long getIdNota() {
        return idNota;
    }

    public void setIdNota(Long idNota) {
        this.idNota = idNota;
    }

    public Long getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Long idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
