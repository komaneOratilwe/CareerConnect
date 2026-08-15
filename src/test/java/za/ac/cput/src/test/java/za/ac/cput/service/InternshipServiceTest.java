package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ac.cput.domain.Internship;
import za.ac.cput.repository.InternshipRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/*

* InternshipServiceTest.java
* Internship service test class
* Author: Refilwe Mabena (231013051)
* Date: 8 August 2026
  */

@ExtendWith(MockitoExtension.class)
public class InternshipServiceTest {

@Mock
private InternshipRepository internshipRepository;

@InjectMocks
private InternshipService internshipService;

private Internship internship;

@BeforeEach
void setUp() {
    internship = new Internship.Builder()
            .setInternshipId("INT001")
            .setTitle("Software Developer Intern")
            .setDescription("Java development internship")
            .setLocation("Cape Town")
            .setDeadline(LocalDateTime.now().plusDays(30))
            .build();
}

@Test
void create_Success() {
    when(internshipRepository.existsById(anyString())).thenReturn(false);
    when(internshipRepository.save(any(Internship.class))).thenReturn(internship);

    Internship createdInternship = internshipService.create(internship);

    assertNotNull(createdInternship);
    assertEquals("INT001", createdInternship.getInternshipId());
    assertEquals("Software Developer Intern", createdInternship.getTitle());

    verify(internshipRepository, times(1)).save(internship);
}

@Test
void create_FailsWhenDeadlineIsInThePast() {
    Internship expiredInternship = new Internship.Builder()
            .setInternshipId("INT002")
            .setTitle("Expired Internship")
            .setDescription("Internship with expired deadline")
            .setLocation("Cape Town")
            .setDeadline(LocalDateTime.now().minusDays(1))
            .build();

    Internship result = internshipService.create(expiredInternship);

    assertNull(result);

    verify(internshipRepository, never()).save(any(Internship.class));
}

@Test
void create_FailsWhenInternshipIdAlreadyExists() {
    when(internshipRepository.existsById(anyString())).thenReturn(true);

    Internship result = internshipService.create(internship);

    assertNull(result);

    verify(internshipRepository, never()).save(any(Internship.class));
}

@Test
void read_Success() {
    when(internshipRepository.findById(anyString()))
            .thenReturn(Optional.of(internship));

    Internship read = internshipService.read(internship.getInternshipId());

    assertNotNull(read);
    assertEquals("INT001", read.getInternshipId());
    assertEquals("Software Developer Intern", read.getTitle());
}

@Test
void read_NotFound() {
    when(internshipRepository.findById(anyString()))
            .thenReturn(Optional.empty());

    Internship read = internshipService.read("INVALID");

    assertNull(read);
}

@Test
void update_Success() {
    Internship updatedInternship = new Internship.Builder()
            .copy(internship)
            .setTitle("Senior Software Developer Intern")
            .build();

    when(internshipRepository.existsById(anyString())).thenReturn(true);
    when(internshipRepository.save(any(Internship.class)))
            .thenReturn(updatedInternship);

    Internship updatedResult = internshipService.update(updatedInternship);

    assertNotNull(updatedResult);
    assertEquals("Senior Software Developer Intern",
            updatedResult.getTitle());
}

@Test
void update_NotFound() {
    when(internshipRepository.existsById(anyString())).thenReturn(false);

    Internship result = internshipService.update(internship);

    assertNull(result);

    verify(internshipRepository, never()).save(any(Internship.class));
}

@Test
void delete_Success() {
    when(internshipRepository.existsById(anyString())).thenReturn(true);

    boolean deleted =
            internshipService.delete(internship.getInternshipId());

    assertTrue(deleted);

    verify(internshipRepository, times(1))
            .deleteById(internship.getInternshipId());
}

@Test
void delete_NotFound() {
    when(internshipRepository.existsById(anyString())).thenReturn(false);

    boolean deleted = internshipService.delete("INVALID");

    assertFalse(deleted);

    verify(internshipRepository, never()).deleteById(anyString());
}

@Test
void getAll_Success() {
    List<Internship> mockList = Arrays.asList(internship, internship);

    when(internshipRepository.findAll()).thenReturn(mockList);

    List<Internship> all = internshipService.getAll();

    assertNotNull(all);
    assertEquals(2, all.size());
}

@Test
void getInternshipsByLocation_Success() {
    List<Internship> mockList = Arrays.asList(internship);

    when(internshipRepository.findByLocation(anyString()))
            .thenReturn(mockList);

    List<Internship> internships =
            internshipService.getInternshipsByLocation("Cape Town");

    assertNotNull(internships);
    assertEquals(1, internships.size());
    assertEquals("Cape Town",
            internships.get(0).getLocation());
}

@Test
void getInternshipsByTitle_Success() {
    List<Internship> mockList = Arrays.asList(internship);

    when(internshipRepository.findByTitleContainingIgnoreCase(anyString()))
            .thenReturn(mockList);

    List<Internship> internships =
            internshipService.getInternshipsByTitle("Software");

    assertNotNull(internships);
    assertEquals(1, internships.size());
    assertEquals("Software Developer Intern",
            internships.get(0).getTitle());
}

@Test
void getUpcomingInternships_Success() {
    List<Internship> mockList = Arrays.asList(internship);

    when(internshipRepository.findByDeadlineAfter(any(LocalDateTime.class)))
            .thenReturn(mockList);

    List<Internship> internships =
            internshipService.getUpcomingInternships(LocalDateTime.now());

    assertNotNull(internships);
    assertEquals(1, internships.size());
    assertEquals("INT001",
            internships.get(0).getInternshipId());
}

}
