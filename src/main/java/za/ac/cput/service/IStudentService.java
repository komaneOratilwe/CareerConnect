package za.ac.cput.service;

import za.ac.cput.domain.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentService extends IService<Student, String> {
    Optional<Student> getStudentByEmail(String email);
    List<Student> getStudentsByName(String name);
}
