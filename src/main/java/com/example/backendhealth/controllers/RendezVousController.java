package controllers;

import dto.RendezVousDTO;
import entities.RendezVous.StatutRendezVous;
import services.RendezVousService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/rendez-vous")
@CrossOrigin(origins = "*")
public class RendezVousController {

    private final RendezVousService rdvService;
    git status
    public RendezVousController(RendezVousService rdvService) {
        this.rdvService = rdvService;
    }

    // GET /api/rendez-vous
    @GetMapping
    public ResponseEntity<List<RendezVousDTO>> getAllRendezVous() {
        return ResponseEntity.ok(rdvService.getAllRendezVous());
    }

    // GET /api/rendez-vous/{id}
    @GetMapping("/{id}")
    public ResponseEntity<RendezVousDTO> getRendezVousById(@PathVariable Long id) {
        return ResponseEntity.ok(rdvService.getRendezVousById(id));
    }

    // GET /api/rendez-vous/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RendezVousDTO>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(rdvService.getRendezVousByUserId(userId));
    }

    // GET /api/rendez-vous/nutritionniste/{id}
    @GetMapping("/nutritionniste/{nutritionnisteId}")
    public ResponseEntity<List<RendezVousDTO>> getByNutritionniste(
            @PathVariable Long nutritionnisteId) {
        return ResponseEntity.ok(rdvService.getRendezVousByNutritionnisteId(nutritionnisteId));
    }

    // GET /api/rendez-vous/coach/{coachId}
    @GetMapping("/coach/{coachId}")
    public ResponseEntity<List<RendezVousDTO>> getByCoach(@PathVariable Long coachId) {
        return ResponseEntity.ok(rdvService.getRendezVousByCoachId(coachId));
    }

    // GET /api/rendez-vous/statut/{statut}
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<RendezVousDTO>> getByStatut(@PathVariable StatutRendezVous statut) {
        return ResponseEntity.ok(rdvService.getRendezVousByStatut(statut));
    }

    // GET /api/rendez-vous/user/{userId}/statut/{statut}
    @GetMapping("/user/{userId}/statut/{statut}")
    public ResponseEntity<List<RendezVousDTO>> getByUserAndStatut(
            @PathVariable Long userId,
            @PathVariable StatutRendezVous statut) {
        return ResponseEntity.ok(rdvService.getRendezVousByUserIdAndStatut(userId, statut));
    }

    @GetMapping("/calendrier/nutritionniste/{nutritionnisteId}")
    public ResponseEntity<List<RendezVousDTO>> getCalendrierNutritionniste(
            @PathVariable Long nutritionnisteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(rdvService.getCalendrierNutritionniste(nutritionnisteId, debut, fin));
    }

    @GetMapping("/calendrier/coach/{coachId}")
    public ResponseEntity<List<RendezVousDTO>> getCalendrierCoach(
            @PathVariable Long coachId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(rdvService.getCalendrierCoach(coachId, debut, fin));
    }

    // POST /api/rendez-vous
    @PostMapping
    public ResponseEntity<RendezVousDTO> createRendezVous(@RequestBody RendezVousDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rdvService.createRendezVous(dto));
    }

    // PUT /api/rendez-vous/{id}
    @PutMapping("/{id}")
    public ResponseEntity<RendezVousDTO> updateRendezVous(
            @PathVariable Long id,
            @RequestBody RendezVousDTO dto) {
        return ResponseEntity.ok(rdvService.updateRendezVous(id, dto));
    }

    // PATCH /api/rendez-vous/{id}/statut/{statut}
    @PatchMapping("/{id}/statut/{statut}")
    public ResponseEntity<RendezVousDTO> updateStatut(
            @PathVariable Long id,
            @PathVariable StatutRendezVous statut) {
        return ResponseEntity.ok(rdvService.updateStatut(id, statut));
    }

    // DELETE /api/rendez-vous/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRendezVous(@PathVariable Long id) {
        rdvService.deleteRendezVous(id);
        return ResponseEntity.noContent().build();
    }
}