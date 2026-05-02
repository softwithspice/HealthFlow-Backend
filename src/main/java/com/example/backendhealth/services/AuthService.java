package com.example.backendhealth.services;

import com.example.backendhealth.dto.AbonnementDTO;
import com.example.backendhealth.dto.LoginDTO;
import com.example.backendhealth.dto.RegisterDTO;
import com.example.backendhealth.entities.Abonnement;
import com.example.backendhealth.entities.user;
import com.example.backendhealth.repositories.AbonnementRepository;
import com.example.backendhealth.repositories.UserRepository;
import com.example.backendhealth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AbonnementRepository abonnementRepository;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private PasswordEncoder passwordEncoder;

  // ─── REGISTER SIMPLE ──────────────────────────────────────────────────────
  public Map<String, String> register(RegisterDTO dto) {
    if (userRepository.existsByEmail(dto.getEmail())) {
      throw new RuntimeException("Email déjà utilisé !");
    }

    user newUser = new user();
    newUser.setNom(dto.getNom());
    newUser.setPrenom(dto.getPrenom());
    newUser.setEmail(dto.getEmail());
    newUser.setTel(dto.getTel());
    newUser.setRole(dto.getRole() != null ? dto.getRole() : "NUTRITIONIST");
    newUser.setPwd(passwordEncoder.encode(dto.getPwd()));
    newUser.setActive(true);
    userRepository.save(newUser);

    String token = jwtUtil.generateToken(newUser.getEmail(), newUser.getRole());

    Map<String, String> response = new HashMap<>();
    response.put("token", token);
    response.put("email", newUser.getEmail());
    response.put("role", newUser.getRole());
    response.put("typeAbonnement",
      dto.getTypeAbonnement() != null ? dto.getTypeAbonnement() : "MOIS_1");
    response.put("message", "Inscription réussie !");
    return response;
  }

  // ─── REGISTER + PAIEMENT EN UNE SEULE TRANSACTION ────────────────────────
  @Transactional
  public Map<String, String> registerWithPayment(RegisterDTO registerDTO,
                                                 AbonnementDTO.PaymentRequest paymentRequest) {

    if (userRepository.existsByEmail(registerDTO.getEmail())) {
      throw new RuntimeException("Email déjà utilisé !");
    }

    // 1. Créer l'utilisateur
    user newUser = new user();
    newUser.setNom(registerDTO.getNom());
    newUser.setPrenom(registerDTO.getPrenom());
    newUser.setEmail(registerDTO.getEmail());
    newUser.setTel(registerDTO.getTel());
    newUser.setRole(registerDTO.getRole() != null ? registerDTO.getRole() : "NUTRITIONIST");
    newUser.setPwd(passwordEncoder.encode(registerDTO.getPwd()));
    newUser.setActive(true);
    userRepository.save(newUser);

    // 2. Créer l'abonnement directement (sans appel HTTP)
    Abonnement.TypeAbonnement type =
      Abonnement.TypeAbonnement.valueOf(paymentRequest.getTypeAbonnement());

    Abonnement abonnement = new Abonnement();
    abonnement.setType_abonnement(type);
    abonnement.setMontant(type.prix);
    abonnement.setDateDebut(LocalDate.now());
    abonnement.setDateFin(LocalDate.now().plusMonths(type.dureeEnMois));
    abonnement.setStatut(Abonnement.StatutAbonnement.ACTIF);
    abonnement.setStripeSessionId(UUID.randomUUID().toString());
    abonnement.setUserEntity(newUser);
    abonnementRepository.save(abonnement);

    // 3. Générer token
    String token = jwtUtil.generateToken(newUser.getEmail(), newUser.getRole());

    Map<String, String> response = new HashMap<>();
    response.put("token", token);
    response.put("email", newUser.getEmail());
    response.put("role", newUser.getRole());
    response.put("message", "Inscription et paiement réussis !");
    return response;
  }

  // ─── LOGIN ────────────────────────────────────────────────────────────────
  public Map<String, String> login(LoginDTO dto) {
    user u = userRepository.findByEmail(dto.getEmail())
      .orElseThrow(() -> new RuntimeException("Email introuvable !"));

    if (!passwordEncoder.matches(dto.getPwd(), u.getPwd())) {
      throw new RuntimeException("Mot de passe incorrect !");
    }
    if (!u.isActive()) {
      throw new RuntimeException("Compte désactivé !");
    }

    String token = jwtUtil.generateToken(u.getEmail(), u.getRole());

    Map<String, String> response = new HashMap<>();
    response.put("token", token);
    response.put("role", u.getRole());
    response.put("nom", u.getNom());
    response.put("prenom", u.getPrenom());
    response.put("message", "Connexion réussie !");
    return response;
  }
}
