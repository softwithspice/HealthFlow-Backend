package com.example.backendhealth.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class PlanAlimentaireDTO {
    private Long id;
    private String nom;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Integer caloriesJournalieres;
    private String objectif;
    private Long userId;
    private Long nutritionnisteId;
    private Long regimeId;
    private Long consultationId;       // ← lien vers la consultation
    private List<RepasDTO> repas;      // ← repas inclus dans le plan
}