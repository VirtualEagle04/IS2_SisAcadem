package co.edu.unbosque.hozho.curso_service.model;

import jakarta.persistence.*;

@Entity
@Table (name = "curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_curso")
    private Long idCurso;

    @Column (name = "id_grado")
    private Long idGrado;

    private String nombre;

    public Curso(){

    }

    public Curso(Long idCurso, Long idGrado, String nombre) {
        this.idCurso = idCurso;
        this.idGrado = idGrado;
        this.nombre = nombre;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public Long getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(Long idGrado) {
        this.idGrado = idGrado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
