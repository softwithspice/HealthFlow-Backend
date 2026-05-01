package dto

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationDTO {

    private Long id;
    private Long rendezVousId;
    private Long userId;
    private Long nutritionnisteId;
    private Long coachId;

    private Double poids;
    private Double taille;
    private Double imc;
    private String objectif;

    // Plan recommandé
    private Long planAlimentaireId;

    private String diagnostic;
    private String recommandations;
    private String notes;

    private LocalDateTime dateConsultation;
    private LocalDateTime prochainRdv;
}