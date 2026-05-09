package com.example.backendhealth.repositories;

import com.example.backendhealth.entities.Conversation;
import com.example.backendhealth.entities.Conversation.ConversationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    List<Conversation> findByPatientId(String patientId);          // ← String

    List<Conversation> findByNutritionistId(String nutritionistId); // ← String

    List<Conversation> findByCoachId(String coachId);              // ← String

    Optional<Conversation> findByPatientIdAndNutritionistIdAndStatus(
            String patientId, String nutritionistId, ConversationStatus status); // ← String

    Optional<Conversation> findByPatientIdAndCoachIdAndStatus(
            String patientId, String coachId, ConversationStatus status);        // ← String
}