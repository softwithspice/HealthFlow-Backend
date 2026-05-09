package com.example.backendhealth.controllers;

import com.example.backendhealth.entities.user;
import com.example.backendhealth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        return userRepository.findById(id)
                .map(u -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("nom", u.getNom());
                    response.put("prenom", u.getPrenom());
                    response.put("email", u.getEmail());
                    response.put("tel", u.getTel());
                    response.put("role", u.getRole());
                    // Champs Bloomer si applicable
                    if (u instanceof com.example.backendhealth.entities.Bloomer bloomer) {
                        response.put("age", bloomer.getAge());
                        response.put("height", bloomer.getHeight());
                        response.put("weight", bloomer.getWeight());
                        response.put("goal", bloomer.getGoal());
                        response.put("lifestyleLevel", bloomer.getLifestyleLevel());
                    }
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}