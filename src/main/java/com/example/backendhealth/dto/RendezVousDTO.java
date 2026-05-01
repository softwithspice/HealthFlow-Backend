package dto:



import entities.RendezVous.StatutRendezVous;
import entities.RendezVous.TypeIntervenant;
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
    private Long userId;
    private Long nutritionnisteId;
    private Long coachId;
}