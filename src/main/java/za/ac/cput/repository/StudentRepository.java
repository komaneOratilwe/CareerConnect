package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Student;

import java.util.List;
import java.util.Optional;

/*
 * StudentRepository.java
 * StudentRepository class
 * Author: Ebenezer Kouakou (230480152)
 * Date: 10 June 2026
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByEmail(String email);
    List<Student> findByName(String name);
}
