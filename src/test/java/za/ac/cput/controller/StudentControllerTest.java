package za.ac.cput.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import za.ac.cput.domain.Student;
import za.ac.cput.factory.StudentFactory;
import za.ac.cput.service.IStudentService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * StudentControllerTest.java
 * Student controller test class
 * Author: Ebenezer Kouakou (230480152)
 * Date: 19 June 2026
 */
@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private IStudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student student;

    @BeforeEach
    void setUp() {
        student = StudentFactory.buildStudent("230480152", "xholisa", "xholisa@gmail.com",
                "pass123");
    }

    @Test
    void create_Success()  throws Exception {
        when(studentService.create(any(Student.class))).thenReturn(student);
        mockMvc.perform(post("/api/student/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk());

        verify(studentService, times(1)).create(any(Student.class));
    }

    @Test
    void read_Success()  throws Exception {
        when(studentService.read(anyString())).thenReturn(student);
        mockMvc.perform(get("/api/student/read/" + student.getStudentNumber()))
                .andExpect(status().isOk());
        verify(studentService, times(1)).read(anyString());
    }

    @Test
    void update_Success()  throws Exception {
        when(studentService.update(any(Student.class))).thenReturn(student);
        mockMvc.perform(put("/api/student/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk());
        verify(studentService, times(1)).update(any(Student.class));
    }

    @Test
    void delete_Success()  throws Exception {
        when(studentService.delete(anyString())).thenReturn(true);
        mockMvc.perform(delete("/api/student/delete/" + student.getStudentNumber()))
                .andExpect(status().isOk());
        verify(studentService, times(1)).delete(anyString());
    }

    @Test
    void getAll_Success()  throws Exception {
        mockMvc.perform(get("/api/student/getAll"))
                .andExpect(status().isOk());
        verify(studentService, times(1)).getAll();
    }

    @Test
    void getStudentByEmail_Success()  throws Exception {
        when(studentService.getStudentByEmail(anyString())).thenReturn(Optional.of(student));
        mockMvc.perform(get("/api/student/getByEmail")
                        .param("email", student.getEmail()))
                .andExpect(status().isOk());
        verify(studentService, times(1)).getStudentByEmail(anyString());
    }

    @Test
    void getStudentsByName_Success()  throws Exception {
        when(studentService.getStudentsByName(anyString())).thenReturn(List.of(student));
        mockMvc.perform(get("/api/student/getByName")
                .param("name", student.getName()))
                .andExpect(status().isOk());
        verify(studentService, times(1)).getStudentsByName(anyString());
    }
}
