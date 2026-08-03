package za.ac.cput.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;

/*
 * Student.java
 * Student entity class
 * Author: Ebenezer Kouakou (230480152)
 * Date: 10 June 2026
 */

@Entity
public class Student {

    @Id
    private String studentNumber;
    private String name;
    private String email;
    private String password;

    protected Student() {
    }

    private Student(Builder builder) {
        this.studentNumber = builder.studentNumber;
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentNumber='" + studentNumber + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentNumber, student.studentNumber) &&
                Objects.equals(name, student.name) &&
                Objects.equals(email, student.email) &&
                Objects.equals(password, student.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNumber, name, email, password);
    }

    public static class Builder {
        private String studentNumber;
        private String name;
        private String email;
        private String password;

        public Builder setStudentNumber(String studentNumber) {
            this.studentNumber = studentNumber;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder copy(Student student) {
            this.studentNumber = student.studentNumber;
            this.name = student.name;
            this.email = student.email;
            this.password = student.password;
            return this;
        }

        public Student build() {
            return new Student( this);
        }
    }
}