package za.ac.cput.controller;

import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Student;
import za.ac.cput.service.IStudentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final IStudentService studentService;

    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @GetMapping("/read/{studentNumber}")
    public Student read(@PathVariable String studentNumber) {
        return studentService.read(studentNumber);
    }

    @PutMapping("/update")
    public Student update(@RequestBody Student student) {
        return studentService.update(student);
    }

    @DeleteMapping("/delete/{studentNumber}")
    public boolean delete(@PathVariable String studentNumber) {
        return studentService.delete(studentNumber);
    }

    @GetMapping("/getAll")
    public List<Student> getAll() {
        return studentService.getAll();
    }

    @GetMapping("/getByEmail")
    public Optional<Student> getStudentByEmail(@RequestParam String email) {
        return studentService.getStudentByEmail(email);
    }

    @GetMapping("/getByName")
    public List<Student> getStudentsByName(@RequestParam String name) {
        return studentService.getStudentsByName(name);
    }
}
