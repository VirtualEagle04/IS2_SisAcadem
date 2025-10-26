package co.edu.unbosque.hozho.usuarioservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estudiante_detalle")
public class EstudianteDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;
    @Column(name = "id_usuario")
    private Long idUsuario;
    @Column(name = "id_curso")
    private Long idCurso;
    @Column(name = "id_acudiente")
    private Long idAcudiente;

    public EstudianteDetalle() {
    }

    public EstudianteDetalle(Long idAcudiente, Long idCurso, Long idDetalle, Long idUsuario) {
        this.idAcudiente = idAcudiente;
        this.idCurso = idCurso;
        this.idDetalle = idDetalle;
        this.idUsuario = idUsuario;
    }

    public Long getIdAcudiente() {
        return idAcudiente;
    }

    public void setIdAcudiente(Long idAcudiente) {
        this.idAcudiente = idAcudiente;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public Long getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Long idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
