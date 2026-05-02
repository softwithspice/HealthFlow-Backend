package com.example.backendhealth.services;

import com.example.backendhealth.dto.ConversationDTO;
import com.example.backendhealth.dto.MessageDTO;
import com.example.backendhealth.entities.Conversation;
import com.example.backendhealth.entities.Conversation.ConversationStatus;
import com.example.backendhealth.repositories.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public ConversationDTO createConversation(ConversationDTO dto) {
        conversationRepository.findByPatientIdAndNutritionistIdAndStatus(
                        dto.getPatientId(), dto.getNutritionistId(), ConversationStatus.ACTIVE)
                .ifPresent(c -> { throw new RuntimeException("Conversation déjà active entre ces deux utilisateurs"); });

        Conversation conv = Conversation.builder()
                .patientId(dto.getPatientId())
                .nutritionistId(dto.getNutritionistId())
                .status(ConversationStatus.ACTIVE)
                .build();

        return toDTO(conversationRepository.save(conv), false);
    }

    @Transactional(readOnly = true)
    public ConversationDTO getConversationById(Long id) {
        Conversation conv = conversationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conversation introuvable"));
        return toDTO(conv, true);
    }

    @Transactional(readOnly = true)
    public List<ConversationDTO> getConversationsByPatient(Long patientId) {
        return conversationRepository.findByPatientId(patientId)
                .stream().map(c -> toDTO(c, false)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConversationDTO> getConversationsByNutritionist(Long nutritionistId) {
        return conversationRepository.findByNutritionistId(nutritionistId)
                .stream().map(c -> toDTO(c, false)).collect(Collectors.toList());
    }

    public ConversationDTO closeConversation(Long id) {
        Conversation conv = conversationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conversation introuvable"));
        conv.setStatus(ConversationStatus.CLOSED);
        return toDTO(conversationRepository.save(conv), false);
    }

    public void deleteConversation(Long id) {
        conversationRepository.deleteById(id);
    }

    private ConversationDTO toDTO(Conversation c, boolean includeMessages) {
        List<MessageDTO> messages = includeMessages
                ? c.getMessages().stream().map(m -> MessageDTO.builder()
                .id(m.getId())
                .conversationId(c.getId())
                .senderId(m.getSenderId())
                .senderRole(m.getSenderRole())
                .content(m.getContent())
                .isRead(m.isRead())
                .sentAt(m.getSentAt())
                .build()).collect(Collectors.toList())
                : List.of();

        return ConversationDTO.builder()
                .id(c.getId())
                .patientId(c.getPatientId())
                .nutritionistId(c.getNutritionistId())
                .status(c.getStatus())
                .createdAt(c.getCreatedAt())
                .updatedAt(c.getUpdatedAt())
                .messages(messages)
                .build();
    }
}