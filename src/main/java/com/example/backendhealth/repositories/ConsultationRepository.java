
package repositories;

import entities.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    // Consultation liée à un rendez-vous (1:1)
    Optional<Consultation> findByRendezVousId(Long rendezVousId);

    // Toutes les consultations d'un patient
    List<Consultation> findByUserId(Long userId);

    // Consultations d'un nutritionniste
    List<Consultation> findByNutritionnisteId(Long nutritionnisteId);

    // Consultations d'un coach
    List<Consultation> findByCoachId(Long coachId);

    // Consultations avec un plan alimentaire spécifique
    List<Consultation> findByPlanAlimentaireId(Long planAlimentaireId);
}