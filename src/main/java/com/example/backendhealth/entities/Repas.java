package com.example.backendhealth.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "repas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Petit-déjeuner, Déjeuner, Dîner, Collation matin, Collation après-midi
    @Column(name = "type_repas", nullable = false)
    private String typeRepas;

    @Column(nullable = false)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String aliments;     // liste des aliments ex: "Poulet grillé, riz, salade"

    private Integer calories;
    private Double proteines;    // grammes
    private Double glucides;
    private Double lipides;

    @Column(columnDefinition = "TEXT")
    private String notes;

    // Lié au plan alimentaire
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_alimentaire_id", nullable = false)
    private PlanAlimentaire planAlimentaire;
}