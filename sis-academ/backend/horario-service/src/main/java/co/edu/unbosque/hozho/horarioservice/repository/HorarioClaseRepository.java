package co.edu.unbosque.hozho.horarioservice.repository;

import co.edu.unbosque.hozho.horarioservice.model.HorarioClase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface HorarioClaseRepository extends JpaRepository<HorarioClase, Long> {

    // Encontrar horarios clases por materia
    List<HorarioClase> findByIdMateria(Long idMateria);

    // Encontrar horarios clases por grado
    List<HorarioClase> findByIdGrado(Long idGrado);

    // Encontrar horarios clases por dia semana
    List<HorarioClase> findByDiaSemana(String diaSemana);

    // Encontrar horarios clases por salon
    List<HorarioClase> findBySalon(String salon);

    // Existe un horario clase por hora de inicio
    boolean existsByHoraInicio(LocalTime horaInicio);

    // Existe un horario clase por salon
    boolean existsBySalon(String salon);

    // Existe un horario clase por dia semana
    boolean existsByDiaSemana(String diaSemana);
}
