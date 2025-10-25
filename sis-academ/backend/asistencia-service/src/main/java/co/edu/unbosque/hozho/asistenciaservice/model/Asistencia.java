package co.edu.unbosque.hozho.asistenciaservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "asistencia")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistencia")
    private Long idAsistencia;
    @Column(name = "id_estudiante")
    private Long idEstudiante;
    @Column(name = "id_clase")
    private Long idClase;
    @Column(columnDefinition = "ENUM('Presente', 'Ausente', 'Tarde', 'Excusado')")
    private String estado;
    private String observacion;

    public Asistencia() {
    }

    public Asistencia(String estado, Long idAsistencia, Long idClase, Long idEstudiante, String observacion) {
        this.estado = estado;
        this.idAsistencia = idAsistencia;
        this.idClase = idClase;
        this.idEstudiante = idEstudiante;
        this.observacion = observacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(Long idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public Long getIdClase() {
        return idClase;
    }

    public void setIdClase(Long idClase) {
        this.idClase = idClase;
    }

    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
