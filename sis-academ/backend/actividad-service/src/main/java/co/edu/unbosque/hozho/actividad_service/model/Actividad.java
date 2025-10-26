package co.edu.unbosque.hozho.actividad_service.model;

import jakarta.persistence.*;

@Entity
@Table(name= "actividad")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private Long idActividad;
    @Column(name = "id_materia")
    private Long idMateria;
    private String nombre;
    private String descripcion;
    private Integer porcentaje;

    public Actividad() {
    }

    public Actividad(String descripcion, Long idActividad, Long idMateria, String nombre, Integer porcentaje) {
        this.descripcion = descripcion;
        this.idActividad = idActividad;
        this.idMateria = idMateria;
        this.nombre = nombre;
        this.porcentaje = porcentaje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Long idActividad) {
        this.idActividad = idActividad;
    }

    public Long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Long idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }
}
