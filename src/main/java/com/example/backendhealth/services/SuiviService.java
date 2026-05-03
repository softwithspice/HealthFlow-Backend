package com.example.backendhealth.services;

import com.example.backendhealth.entities.Suivi_quotidien;
import com.example.backendhealth.entities.user;
import com.example.backendhealth.repositories.SuiviRepository;
import com.example.backendhealth.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class SuiviService {

    private final SuiviRepository repository;
    private final UserRepository userRepository; // ← ajoute

    public SuiviService(SuiviRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    // Récupère ou crée le suivi du jour pour ce user
    public Suivi_quotidien getSuiviDuJour(String userId) {
        LocalDate today = LocalDate.now();
        return repository.findByUserIdAndDate(userId, today)
                .orElseGet(() -> createSuivi(userId));
    }

    private Suivi_quotidien createSuivi(String userId) {
        user u = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User non trouvé"));

        Suivi_quotidien suivi = new Suivi_quotidien();
        suivi.setUser(u);
        suivi.setDate(LocalDate.now());
        suivi.setNb_coupes_bues(0);
        suivi.setNb_exercices_faites(0);
        suivi.setNb_heures_sommeil(0);
        suivi.setCalories_consommes(0);
        suivi.setProteines_consommes(0);
        return repository.save(suivi);
    }

    public Suivi_quotidien incrementEau(String userId) {
        Suivi_quotidien suivi = getSuiviDuJour(userId);
        suivi.setNb_coupes_bues(suivi.getNb_coupes_bues() + 1);
        return repository.save(suivi);
    }

    public Suivi_quotidien incrementExercice(String userId) {
        Suivi_quotidien suivi = getSuiviDuJour(userId);
        suivi.setNb_exercices_faites(suivi.getNb_exercices_faites() + 1);
        return repository.save(suivi);
    }

    public Suivi_quotidien updateSommeil(String userId, Integer heures) {
        Suivi_quotidien suivi = getSuiviDuJour(userId);
        suivi.setNb_heures_sommeil(heures);
        return repository.save(suivi);
    }

    public Suivi_quotidien updateCalories(String userId, Integer cal) {
        Suivi_quotidien suivi = getSuiviDuJour(userId);
        suivi.setCalories_consommes(cal);
        return repository.save(suivi);
    }

    public Suivi_quotidien updateProteines(String userId, Integer prot) {
        Suivi_quotidien suivi = getSuiviDuJour(userId);
        suivi.setProteines_consommes(prot);
        return repository.save(suivi);
    }

}