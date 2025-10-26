package co.edu.unbosque.hozho.grado_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "grado")
public class Grado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grado")
    private Long idGrado;

    private String nombre;

    public Grado(){

    }

    public Grado(Long idGrado, String nombre) {
        this.idGrado = idGrado;
        this.nombre = nombre;
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

