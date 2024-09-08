package com.ua.travel_mate.services;

import com.ua.travel_mate.entities.Invitation;
import com.ua.travel_mate.repositories.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvitationService {
    private final InvitationRepository invitationRepository;

    public InvitationService(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    public List<Invitation> getAllInvitations() {
        return invitationRepository.findAll();
    }

    public Optional<Invitation> getInvitationById(Integer invitationId) {
        return invitationRepository.findById(invitationId);
    }

    public Invitation saveInvitation(Invitation invitation) {
        return invitationRepository.save(invitation);
    }

    public void deleteInvitation(Integer invitationId) {
        invitationRepository.deleteById(invitationId);
    }

}
