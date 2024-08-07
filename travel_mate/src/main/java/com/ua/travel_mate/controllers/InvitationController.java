package com.ua.travel_mate.controllers;

import com.ua.travel_mate.entities.Invitation;
import com.ua.travel_mate.services.InvitationService;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {
    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @GetMapping
    public List<Invitation> getAllInvitations() {
        return invitationService.getAllInvitations();
    }

    @GetMapping("/{id}")
    public Invitation getInvitationById(@PathVariable("id") Integer invitationId) {
        return invitationService.getInvitationById(invitationId)
                .orElseThrow(() -> new RuntimeException("Invitation not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Invitation createInvitation(@RequestBody Invitation invitation) {
        Invitation createdInvitation = invitationService.saveInvitation(invitation);
        return createdInvitation;
    }

    @PutMapping("/{id}")
    public Invitation updateInvitation(@PathVariable("id") Integer invitationId, @RequestBody Invitation invitation) {
        if (!invitationId.equals(invitation.getId())) {
            throw new IllegalArgumentException("Invitation ID in path must match the ID in the request body");
        }
        return invitationService.saveInvitation(invitation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvitation(@PathVariable("id") Integer invitationId) {
        invitationService.deleteInvitation(invitationId);
    }
}
