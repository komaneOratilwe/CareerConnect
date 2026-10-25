package za.ac.cput.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import za.ac.cput.domain.Profile;
import za.ac.cput.factory.ProfileFactory;
import za.ac.cput.repository.StudentRepository;
import za.ac.cput.security.CustomUserDetailsService;
import za.ac.cput.service.IProfileService;
import za.ac.cput.service.JwtService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * ProfileControllerTest.java
 * Profile controller test class
 * Author: Oratilwe Komane (230716873)
 * Date: 25 October 2026
 */
@WebMvcTest(ProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(roles = "ADMIN")
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IProfileService profileService;

    // Needed because ProfileController now takes StudentRepository,
    // and the JWT security filter needs these two beans to start.
    @MockitoBean
    private StudentRepository studentRepository;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private Profile profile;
    private Authentication admin;

    @BeforeEach
    void setUp() {
        profile = ProfileFactory.buildProfile(
                "230716873",
                "Aspiring software developer",
                Collections.singletonList("Java"),
                "https://example.com/resume.pdf"
        );
        admin = new UsernamePasswordAuthenticationToken(
                "admin@gmail.com", "pass123",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    void create_Success() throws Exception {
        when(profileService.create(any(Profile.class))).thenReturn(profile);
        mockMvc.perform(post("/api/profile/create")
                        .principal(admin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profile)))
                .andExpect(status().isOk());

        verify(profileService, times(1)).create(any(Profile.class));
    }

    @Test
    void read_Success() throws Exception {
        when(profileService.read(anyString())).thenReturn(profile);
        mockMvc.perform(get("/api/profile/read/" + profile.getProfileId())
                        .principal(admin))
                .andExpect(status().isOk());
        verify(profileService, times(1)).read(anyString());
    }

    @Test
    void update_Success() throws Exception {
        when(profileService.update(any(Profile.class))).thenReturn(profile);
        mockMvc.perform(put("/api/profile/update")
                        .principal(admin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profile)))
                .andExpect(status().isOk());
        verify(profileService, times(1)).update(any(Profile.class));
    }

    @Test
    void delete_Success() throws Exception {
        when(profileService.delete(anyString())).thenReturn(true);
        mockMvc.perform(delete("/api/profile/delete/" + profile.getProfileId())
                        .principal(admin))
                .andExpect(status().isOk());
        verify(profileService, times(1)).delete(anyString());
    }

    @Test
    void getAll_Success() throws Exception {
        mockMvc.perform(get("/api/profile/getAll")
                        .principal(admin))
                .andExpect(status().isOk());
        verify(profileService, times(1)).getAll();
    }

    @Test
    void getByStudentNumber_Success() throws Exception {
        when(profileService.getProfileByStudentNumber(anyString())).thenReturn(Optional.of(profile));
        mockMvc.perform(get("/api/profile/student/" + profile.getStudentNumber())
                        .principal(admin))
                .andExpect(status().isOk());
        verify(profileService, times(1)).getProfileByStudentNumber(anyString());
    }
}