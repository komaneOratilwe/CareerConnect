package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ac.cput.domain.Student;
import za.ac.cput.factory.StudentFactory;
import za.ac.cput.repository.StudentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/*
 * StudentServiceTest.java
 * Student service test class
 * Author: Ebenezer Kouakou (230480152)
 * Date: 19 June 2026
 */
@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    private Student student;

    @InjectMocks StudentService studentService;


    @BeforeEach
    void setUp() {
        student = StudentFactory.buildStudent("230480152", "xholisa", "xholisa@gmail.com",
                "pass123");
    }

    @Test
    void create_Success() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        Student createdStudent = studentService.create(student);
        assertNotNull(createdStudent);
        assertEquals("xholisa", createdStudent.getName());
    }

    @Test
    void read_Success() {
        when(studentRepository.findById(anyString())).thenReturn(Optional.of(student));
        Student read = studentService.read(student.getStudentNumber());
        assertNotNull(read);
        assertEquals("xholisa", read.getName());
    }

    @Test
    void update_Success() {
        Student updatedInfo = new Student.Builder()
                .copy(student)
                .setName("Peter")
                .setEmail("peter@gmail.com")
                .build();


        when(studentRepository.existsById(anyString())).thenReturn(true);
        when(studentRepository.save(any(Student.class))).thenReturn(updatedInfo);

        Student updatedResult = studentService.update(updatedInfo);

        assertNotNull(updatedResult);
        assertEquals("Peter", updatedResult.getName());
    }

    @Test
    void delete_Success() {
        when(studentRepository.existsById(anyString())).thenReturn(true);
        boolean deleted = studentService.delete(student.getStudentNumber());
        assertTrue(deleted);
        verify(studentRepository, times(1)).deleteById(student.getStudentNumber());
    }

    @Test
    void getAll_Success() {
        List<Student> mockList = Arrays.asList(student, student);
        when(studentRepository.findAll()).thenReturn(mockList);

        List<Student> all = studentService.getAll();
        assertNotNull(all);
        assertEquals(2, all.size());
    }

    @Test
    void getStudentByEmail_Success() {
        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.of(student));
        Optional<Student> email = studentService.getStudentByEmail(student.getEmail());
        assertTrue(email.isPresent());
        assertEquals("xholisa@gmail.com", email.get().getEmail());
    }

    @Test
    void getStudentsByName_Success() {
        Student student1 = student;
        Student student2 = new Student.Builder()
                .copy(student)
                .setStudentNumber("230480999")
                .setEmail("another.xholisa@gmail.com")
                .build();

        List<Student> mockList = Arrays.asList(student1, student2);

        when(studentRepository.findByNameContainingIgnoreCase(anyString())).thenReturn(mockList);

        List<Student> students = studentService.getStudentsByName("xholisa");

        assertNotNull(students);
        assertEquals(2, students.size());
        assertEquals("xholisa", students.get(0).getName());
        assertEquals("xholisa", students.get(1).getName());
    }

}
