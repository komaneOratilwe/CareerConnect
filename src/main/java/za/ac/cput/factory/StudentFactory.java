package za.ac.cput.factory;

import za.ac.cput.domain.Student;
import za.ac.cput.util.Helper;

/*
 * StudentFactory.java
 * Student factory class
 * Author: Ebenezer Kouakou (230480152)
 * Date: 10 June 2026
 */
public class StudentFactory {
    public static Student buildStudent(String studentNumber, String name,
                                       String email, String password) {

        if (Helper.isEmptyOrNull(studentNumber) || Helper.isEmptyOrNull(name)
        || Helper.isEmptyOrNull(password)) {
            return null;
        }

        if (!Helper.isValidEmail(email)) {
            return null;
        }

        return new Student.Builder()
                .setStudentNumber(studentNumber)
                .setName(name)
                .setEmail(email)
                .setPassword(password)
                .build();
    }
}
