
package repositories;

import entities.RendezVous;
import entities.RendezVous.StatutRendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    // Tous les RDV d'un patient
    List<RendezVous> findByUserId(Long userId);

    // Tous les RDV d'un nutritionniste
    List<RendezVous> findByNutritionnisteId(Long nutritionnisteId);

    // Tous les RDV d'un coach
    List<RendezVous> findByCoachId(Long coachId);

    // RDV par statut
    List<RendezVous> findByStatut(StatutRendezVous statut);

    // RDV d'un patient par statut
    List<RendezVous> findByUserIdAndStatut(Long userId, StatutRendezVous statut);

    // RDV entre deux dates
    List<RendezVous> findByDateHeureBetween(LocalDateTime debut, LocalDateTime fin);

    // RDV d'un nutritionniste entre deux dates (pour calendrier)
    List<RendezVous> findByNutritionnisteIdAndDateHeureBetween(
            Long nutritionnisteId, LocalDateTime debut, LocalDateTime fin);

    // RDV d'un coach entre deux dates (pour calendrier)
    List<RendezVous> findByCoachIdAndDateHeureBetween(
            Long coachId, LocalDateTime debut, LocalDateTime fin);
}