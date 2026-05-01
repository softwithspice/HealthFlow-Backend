package entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rendez_vous_id", unique = true)
    private Long rendezVousId;

    // Patient
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "nutritionniste_id")
    private Long nutritionnisteId;

    // Coach qui a fait la consultation
    @Column(name = "coach_id")
    private Long coachId;

    private Double poids;       // en kg
    private Double taille;      // en cm
    private Double imc;         // calculé automatiquement
    private String objectif;    // perte de poids, maintien, prise de masse

    // ── Plan alimentaire recommandé ───────────────────────────────
    @Column(name = "plan_alimentaire_id")
    private Long planAlimentaireId;

    // ── Diagnostic et recommandations ────────────────────────────
    @Column(columnDefinition = "TEXT")
    private String diagnostic;

    @Column(columnDefinition = "TEXT")
    private String recommandations;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "date_consultation")
    private LocalDateTime dateConsultation;

    // Prochain RDV recommandé
    @Column(name = "prochain_rdv")
    private LocalDateTime prochainRdv;

    @PrePersist
    public void prePersist() {
        this.dateConsultation = LocalDateTime.now();
        // Calcul automatique de l'IMC si poids et taille disponibles
        if (this.poids != null && this.taille != null && this.taille > 0) {
            double tailleEnMetres = this.taille / 100.0;
            this.imc = Math.round((this.poids / (tailleEnMetres * tailleEnMetres)) * 100.0) / 100.0;
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (this.poids != null && this.taille != null && this.taille > 0) {
            double tailleEnMetres = this.taille / 100.0;
            this.imc = Math.round((this.poids / (tailleEnMetres * tailleEnMetres)) * 100.0) / 100.0;
        }
    }
}