package com.ua.travel_mate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.travel_mate.controllers.InvitationController;
import com.ua.travel_mate.entities.Invitation;
import com.ua.travel_mate.services.InvitationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InvitationController.class)
public class InvitationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InvitationService invitationService;

    private Invitation invitation;

    @BeforeEach
    void setUp() {

        invitation = new Invitation();
        invitation.setId(1);
    }

    @Test
    public void testGetAllInvitations() throws Exception {

        Invitation invitation2 = new Invitation();
        invitation2.setId(2);

        given(invitationService.getAllInvitations()).willReturn(Arrays.asList(invitation, invitation2));

        var result = mockMvc.perform(get("/api/invitations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        var json = result.getResponse().getContentAsString();
        var objects = objectMapper.readValue(json, new TypeReference<List<Invitation>>() {});
        assertEquals(1, objects.get(0).getId());
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    public void testGetInvitationById() throws Exception {

        given(invitationService.getInvitationById(1)).willReturn(Optional.of(invitation));

        mockMvc.perform(get("/api/invitations/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }


    @Test
    public void testSaveInvitation() throws Exception {

        invitation.setStatus("pending");

        when(invitationService.saveInvitation(any(Invitation.class))).thenReturn(invitation);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/api/invitations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invitation)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testDeleteInvitation() throws Exception {
        mockMvc.perform(delete("/api/invitations/1"))
                .andExpect(status().isNoContent());
    }
}
