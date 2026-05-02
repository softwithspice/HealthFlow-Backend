package com.example.backendhealth.repositories;

import com.example.backendhealth.entities.RendezVous;
import com.example.backendhealth.entities.RendezVous.StatutRendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findByUserId(Long userId);
    List<RendezVous> findByNutritionnisteId(Long nutritionnisteId);
    List<RendezVous> findByCoachId(Long coachId);
    List<RendezVous> findByStatut(StatutRendezVous statut);
    List<RendezVous> findByUserIdAndStatut(Long userId, StatutRendezVous statut);
    List<RendezVous> findByDateHeureBetween(LocalDateTime debut, LocalDateTime fin);
    List<RendezVous> findByNutritionnisteIdAndDateHeureBetween(Long nutritionnisteId, LocalDateTime debut, LocalDateTime fin);
    List<RendezVous> findByCoachIdAndDateHeureBetween(Long coachId, LocalDateTime debut, LocalDateTime fin);
}