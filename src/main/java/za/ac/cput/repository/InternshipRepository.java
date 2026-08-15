package za.ac.cput.repository;

/*
InternshipRepository.java
Spring Data JPA Repository
Author: Refilwe Mabena (231013051)
Date: 5 August 2026
*/

import za.ac.cput.domain.Internship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, String> {

List<Internship> findByLocation(String location);
    
List<Internship> findByTitleContainingIgnoreCase(String title);
    
List<Internship> findByDeadlineAfter(LocalDateTime deadline);
}
