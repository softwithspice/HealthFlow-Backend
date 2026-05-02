package com.example.backendhealth.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "rendez_vous")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_heure", nullable = false)
    private LocalDateTime dateHeure;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutRendezVous statut = StatutRendezVous.EN_ATTENTE;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_intervenant")
    private TypeIntervenant typeIntervenant;

    private String motif;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "duree_minutes")
    private Integer dureeMinutes = 60;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "nutritionniste_id")
    private Long nutritionnisteId;

    @Column(name = "coach_id")
    private Long coachId;

    public enum StatutRendezVous {
        EN_ATTENTE,
        CONFIRME,
        ANNULE,
        TERMINE
    }

    public enum TypeIntervenant {
        NUTRITIONNISTE,
        COACH,
        LES_DEUX
    }
}