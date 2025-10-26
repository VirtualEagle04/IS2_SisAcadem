package co.edu.unbosque.hozho.usuarioservice.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    @Column(name = "id_rol")
    private Long idRol;
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
    private String correo;
    @Column(name = "fecha_registro", updatable = false, insertable = false)
    private LocalDateTime fechaRegistro;
    private boolean activo;

    public Usuario() {
    }

    public Usuario(boolean activo, String apellidos, String ciudadNacimiento, String clave, String correo, Long docIdentidad, Integer edad, LocalDate fechaNacimiento, LocalDateTime fechaRegistro, Long idRol, Long idUsuario, String nombres, Character sexo, String telefono, String usuario) {
        this.activo = activo;
        this.apellidos = apellidos;
        this.ciudadNacimiento = ciudadNacimiento;
        this.clave = clave;
        this.correo = correo;
        this.docIdentidad = docIdentidad;
        this.edad = edad;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
        this.idRol = idRol;
        this.idUsuario = idUsuario;
        this.nombres = nombres;
        this.sexo = sexo;
        this.telefono = telefono;
        this.usuario = usuario;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCiudadNacimiento() {
        return ciudadNacimiento;
    }

    public void setCiudadNacimiento(String ciudadNacimiento) {
        this.ciudadNacimiento = ciudadNacimiento;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Long getDocIdentidad() {
        return docIdentidad;
    }

    public void setDocIdentidad(Long docIdentidad) {
        this.docIdentidad = docIdentidad;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
