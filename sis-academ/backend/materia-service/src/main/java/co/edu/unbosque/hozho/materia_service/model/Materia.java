package co.edu.unbosque.hozho.materia_service.model;

import jakarta.persistence.*;

@Entity
@Table(name= "materia")


public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_materia")
    private Long idMateria;
    @Column(name = "id_docente ")
    private Long idDocente;
    private String nombre;
    private String descripcion;

    public Materia() {}

    public Materia(String descripcion, String nombre, Long idDocente, Long idMateria) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.idDocente = idDocente;
        this.idMateria = idMateria;
    }

    public Long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Long idMateria) {
        this.idMateria = idMateria;
    }

    public Long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Long idDocente) {
        this.idDocente = idDocente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}