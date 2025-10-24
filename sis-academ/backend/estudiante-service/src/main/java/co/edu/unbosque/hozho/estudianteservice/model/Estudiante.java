package co.edu.unbosque.hozho.estudianteservice.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "estudiante")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estudiante")
    private Long idEstudiante;
    @Column(name = "id_curso")
    private Long idCurso;
    @Column(name = "id_acudiente")
    private Long idAcudiente;
    private String usuario;
    private String clave;
    private String nombres;
    private String apellidos;
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    private Integer edad;
    private Character sexo;
    @Column(name = "doc_identidad")
    private Long docIdentidad;
    @Column(name = "ciudad_nacimiento")
    private String ciudadNacimiento;
    private String telefono;
    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;
    @Column(columnDefinition = "ENUM('Activo', 'Inactivo')")
    private String estado;

    public Estudiante() {
    }

    public Estudiante(String apellidos, String ciudadNacimiento, String clave, Long docIdentidad, Integer edad, String estado, LocalDate fechaIngreso, LocalDate fechaNacimiento, Long idAcudiente, Long idCurso, Long idEstudiante, String nombres, Character sexo, String telefono, String usuario) {
        this.apellidos = apellidos;
        this.ciudadNacimiento = ciudadNacimiento;
        this.clave = clave;
        this.docIdentidad = docIdentidad;
        this.edad = edad;
        this.estado = estado;
        this.fechaIngreso = fechaIngreso;
        this.fechaNacimiento = fechaNacimiento;
        this.idAcudiente = idAcudiente;
        this.idCurso = idCurso;
        this.idEstudiante = idEstudiante;
        this.nombres = nombres;
        this.sexo = sexo;
        this.telefono = telefono;
        this.usuario = usuario;
    }

    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public Long getIdAcudiente() {
        return idAcudiente;
    }

    public void setIdAcudiente(Long idAcudiente) {
        this.idAcudiente = idAcudiente;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Long getDocIdentidad() {
        return docIdentidad;
    }

    public void setDocIdentidad(Long docIdendidad) {
        this.docIdentidad = docIdendidad;
    }

    public String getCiudadNacimiento() {
        return ciudadNacimiento;
    }

    public void setCiudadNacimiento(String ciudadNacimiento) {
        this.ciudadNacimiento = ciudadNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
