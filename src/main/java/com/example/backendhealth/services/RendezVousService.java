package com.example.backendhealth.services;

import com.example.backendhealth.dto.RendezVousDTO;
import com.example.backendhealth.entities.RendezVous;
import com.example.backendhealth.entities.RendezVous.StatutRendezVous;
import com.example.backendhealth.repositories.RendezVousRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RendezVousService {

    private final RendezVousRepository rdvRepo;

    public RendezVousService(RendezVousRepository rdvRepo) {
        this.rdvRepo = rdvRepo;
    }

    public List<RendezVousDTO> getAllRendezVous() {
        return rdvRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public RendezVousDTO getRendezVousById(Long id) {
        return toDTO(rdvRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RendezVous introuvable : id=" + id)));
    }

    public List<RendezVousDTO> getRendezVousByUserId(Long userId) {
        return rdvRepo.findByUserId(userId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RendezVousDTO> getRendezVousByNutritionnisteId(Long nutritionnisteId) {
        return rdvRepo.findByNutritionnisteId(nutritionnisteId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RendezVousDTO> getRendezVousByCoachId(Long coachId) {
        return rdvRepo.findByCoachId(coachId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RendezVousDTO> getRendezVousByStatut(StatutRendezVous statut) {
        return rdvRepo.findByStatut(statut).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RendezVousDTO> getRendezVousByUserIdAndStatut(Long userId, StatutRendezVous statut) {
        return rdvRepo.findByUserIdAndStatut(userId, statut).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RendezVousDTO> getCalendrierNutritionniste(Long nutritionnisteId, LocalDateTime debut, LocalDateTime fin) {
        return rdvRepo.findByNutritionnisteIdAndDateHeureBetween(nutritionnisteId, debut, fin)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RendezVousDTO> getCalendrierCoach(Long coachId, LocalDateTime debut, LocalDateTime fin) {
        return rdvRepo.findByCoachIdAndDateHeureBetween(coachId, debut, fin)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // ── Patient crée un RDV ────────────────────────────────────────
    public RendezVousDTO createRendezVous(RendezVousDTO dto) {
        RendezVous rdv = toEntity(dto);
        rdv.setStatut(StatutRendezVous.EN_ATTENTE);
        return toDTO(rdvRepo.save(rdv));
    }

    // ── Nutritionniste accepte le RDV ──────────────────────────────
    public RendezVousDTO accepterRendezVous(Long id) {
        return updateStatut(id, StatutRendezVous.CONFIRME);
    }

    // ── Nutritionniste refuse le RDV ───────────────────────────────
    public RendezVousDTO refuserRendezVous(Long id) {
        return updateStatut(id, StatutRendezVous.ANNULE);
    }

    // ── Marquer RDV comme terminé (après consultation) ────────────
    public RendezVousDTO terminerRendezVous(Long id) {
        return updateStatut(id, StatutRendezVous.TERMINE);
    }

    public RendezVousDTO updateStatut(Long id, StatutRendezVous statut) {
        RendezVous rdv = rdvRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RendezVous introuvable : id=" + id));
        rdv.setStatut(statut);
        return toDTO(rdvRepo.save(rdv));
    }

    public RendezVousDTO updateRendezVous(Long id, RendezVousDTO dto) {
        RendezVous existing = rdvRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RendezVous introuvable : id=" + id));
        existing.setDateHeure(dto.getDateHeure());
        existing.setStatut(dto.getStatut());
        existing.setTypeIntervenant(dto.getTypeIntervenant());
        existing.setMotif(dto.getMotif());
        existing.setNotes(dto.getNotes());
        existing.setDureeMinutes(dto.getDureeMinutes());
        existing.setUserId(dto.getUserId());
        existing.setNutritionnisteId(dto.getNutritionnisteId());
        existing.setCoachId(dto.getCoachId());
        return toDTO(rdvRepo.save(existing));
    }

    public void deleteRendezVous(Long id) {
        if (!rdvRepo.existsById(id))
            throw new EntityNotFoundException("RendezVous introuvable : id=" + id);
        rdvRepo.deleteById(id);
    }

    private RendezVousDTO toDTO(RendezVous rdv) {
        RendezVousDTO dto = new RendezVousDTO();
        dto.setId(rdv.getId());
        dto.setDateHeure(rdv.getDateHeure());
        dto.setStatut(rdv.getStatut());
        dto.setTypeIntervenant(rdv.getTypeIntervenant());
        dto.setMotif(rdv.getMotif());
        dto.setNotes(rdv.getNotes());
        dto.setDureeMinutes(rdv.getDureeMinutes());
        dto.setUserId(rdv.getUserId());
        dto.setNutritionnisteId(rdv.getNutritionnisteId());
        dto.setCoachId(rdv.getCoachId());
        return dto;
    }

    private RendezVous toEntity(RendezVousDTO dto) {
        RendezVous rdv = new RendezVous();
        rdv.setDateHeure(dto.getDateHeure());
        rdv.setStatut(dto.getStatut() != null ? dto.getStatut() : StatutRendezVous.EN_ATTENTE);
        rdv.setTypeIntervenant(dto.getTypeIntervenant());
        rdv.setMotif(dto.getMotif());
        rdv.setNotes(dto.getNotes());
        rdv.setDureeMinutes(dto.getDureeMinutes() != null ? dto.getDureeMinutes() : 60);
        rdv.setUserId(dto.getUserId());
        rdv.setNutritionnisteId(dto.getNutritionnisteId());
        rdv.setCoachId(dto.getCoachId());
        return rdv;
    }
}