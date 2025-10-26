package co.edu.unbosque.hozho.horarioservice.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "horario_clase")
public class HorarioClase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private Long idHorario;
    @Column(name = "id_grado")
    private Long idGrado;
    @Column(name = "id_materia")
    private Long idMateria;
    @Column(name = "dia_semana", columnDefinition = "ENUM('Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes')")
    private String diaSemana;
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;
    @Column(name = "hora_fin")
    private LocalTime horaFin;
    private String salon;

    public HorarioClase() {
    }

    public HorarioClase(String diaSemana, LocalTime horaFin, LocalTime horaInicio, Long idGrado, Long idHorario, Long idMateria, String salon) {
        this.diaSemana = diaSemana;
        this.horaFin = horaFin;
        this.horaInicio = horaInicio;
        this.idGrado = idGrado;
        this.idHorario = idHorario;
        this.idMateria = idMateria;
        this.salon = salon;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Long getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(Long idGrado) {
        this.idGrado = idGrado;
    }

    public Long getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Long idHorario) {
        this.idHorario = idHorario;
    }

    public Long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Long idMateria) {
        this.idMateria = idMateria;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }
}
