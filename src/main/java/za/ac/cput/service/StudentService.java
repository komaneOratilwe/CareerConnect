package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Student;
import za.ac.cput.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        if (studentRepository.existsById(student.getStudentNumber())) {
            System.out.println("Signup Failed: Student number already exists");
            return null;
        }

        Optional<Student> existingEmail = studentRepository.findByEmail(student.getEmail());
        if (existingEmail.isPresent()) {
            System.out.println("Signup Failed: Email is already registered");
            return null;
        }

        return studentRepository.save(student);
    }

    @Override
    public Student read(String string) {
        return studentRepository.findById(string).orElse(null);
    }

    @Override
    public Student update(Student student) {
        if (studentRepository.existsById(student.getStudentNumber())) {
            return studentRepository.save(student);
        }
        return null;
    }

    @Override
    public boolean delete(String string) {
        if (studentRepository.existsById(string)) {
            studentRepository.deleteById(string);
            return true;
        }
        return false;
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    @Override
    public List<Student> getStudentsByName(String name) {
        return studentRepository.findByName(name);
    }
}
