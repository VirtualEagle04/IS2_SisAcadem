package co.edu.unbosque.hozho.rolservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;
    private String nombre;
    @Column(name = "solo_lectura")
    private boolean soloLectura;
    private boolean rol;
    private boolean usuario;
    private boolean grado;
    private boolean curso;
    private boolean materia;
    private boolean actividad;
    private boolean horario;
    private boolean nota;
    private boolean asistencia;
    private boolean matricula;

    public Rol() {
    }

    public Rol(boolean actividad, boolean asistencia, boolean curso, boolean grado, boolean horario, Long idRol, boolean materia, boolean matricula, String nombre, boolean nota, boolean rol, boolean soloLectura, boolean usuario) {
        this.actividad = actividad;
        this.asistencia = asistencia;
        this.curso = curso;
        this.grado = grado;
        this.horario = horario;
        this.idRol = idRol;
        this.materia = materia;
        this.matricula = matricula;
        this.nombre = nombre;
        this.nota = nota;
        this.rol = rol;
        this.soloLectura = soloLectura;
        this.usuario = usuario;
    }

    public boolean isActividad() {
        return actividad;
    }

    public void setActividad(boolean actividad) {
        this.actividad = actividad;
    }

    public boolean isAsistencia() {
        return asistencia;
    }

    public void setAsistencia(boolean asistencia) {
        this.asistencia = asistencia;
    }

    public boolean isCurso() {
        return curso;
    }

    public void setCurso(boolean curso) {
        this.curso = curso;
    }

    public boolean isGrado() {
        return grado;
    }

    public void setGrado(boolean grado) {
        this.grado = grado;
    }

    public boolean isHorario() {
        return horario;
    }

    public void setHorario(boolean horario) {
        this.horario = horario;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public boolean isMateria() {
        return materia;
    }

    public void setMateria(boolean materia) {
        this.materia = materia;
    }

    public boolean isMatricula() {
        return matricula;
    }

    public void setMatricula(boolean matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isNota() {
        return nota;
    }

    public void setNota(boolean nota) {
        this.nota = nota;
    }

    public boolean isRol() {
        return rol;
    }

    public void setRol(boolean rol) {
        this.rol = rol;
    }

    public boolean isSoloLectura() {
        return soloLectura;
    }

    public void setSoloLectura(boolean soloLectura) {
        this.soloLectura = soloLectura;
    }

    public boolean isUsuario() {
        return usuario;
    }

    public void setUsuario(boolean usuario) {
        this.usuario = usuario;
    }
}
