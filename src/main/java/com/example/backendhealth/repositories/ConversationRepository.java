package com.example.backendhealth.repositories;

import com.example.backendhealth.entities.Conversation;
import com.example.backendhealth.entities.Conversation.ConversationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    List<Conversation> findByPatientId(Long patientId);

    List<Conversation> findByNutritionistId(Long nutritionistId);

    Optional<Conversation> findByPatientIdAndNutritionistIdAndStatus(
            Long patientId, Long nutritionistId, ConversationStatus status);
}