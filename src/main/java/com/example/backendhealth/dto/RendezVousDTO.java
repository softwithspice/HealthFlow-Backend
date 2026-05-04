package com.example.backendhealth.dto;

import com.example.backendhealth.entities.RendezVous.StatutRendezVous;
import com.example.backendhealth.entities.RendezVous.TypeIntervenant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RendezVousDTO {
    private Long id;

    private LocalDateTime dateHeure;
    private StatutRendezVous statut;
    private TypeIntervenant typeIntervenant;
    private String motif;
    private String notes;
    private Integer dureeMinutes;

    // ✅ String car UUID
    private String userId;
    private String nutritionnisteId;
    private String coachId;

    // Infos affichage (read-only)
    private String nutritionnisteNom;
    private String nutritionnistePrenom;
    private String coachNom;
    private String coachPrenom;
    private String patientNom;

    public void setNutritionnistPrenom(String prenom) {
    }

    public void setNutritionnistNom(String nom) {
    }
}