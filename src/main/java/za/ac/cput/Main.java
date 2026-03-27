package za.ac.cput;

import za.ac.cput.domain.*;
import za.ac.cput.factory.*;
import za.ac.cput.repository.impl.*;

import java.time.LocalDateTime;

/*
 * Main.java
 * System Demo
 * Author: Ebenezer Kouakou (230480152) - Team Lead
 * Date: 27 March 2026
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println(" Welcome to CareerConnect Start-Up");
        System.out.println("==========================================");

        StudentRepository studentRepo = StudentRepository.getRepository();
        CompanyRepository companyRepo = CompanyRepository.getRepository();
        InternshipRepository internshipRepo = InternshipRepository.getRepository();
        ApplicationRepository applicationRepo = ApplicationRepository.getRepository();

        System.out.println("\n[1] Bootstrapping System Entities...");

        Student student = StudentFactory.buildStudent(
                "267489159", "Keem Sys", "keem@cput.ac.za", "securePass123");

        Company company = CompanyFactory.createCompany(
                "C001", "TechNova Systems", "Software Development", "hr@technova.com");

        Internship internship = InternshipFactory.createInternship(
                "INT-999", "Junior Java Backend Developer", "Spring Boot Internship", "Cape Town", LocalDateTime.now().plusMonths(2));

        studentRepo.create(student);
        companyRepo.create(company);
        internshipRepo.create(internship);
        System.out.println("Entities successfully saved to in-memory repositories.");

        System.out.println("\n[2] Simulating Student Application Process...");

        Application application = ApplicationFactory.createApplication(
                "APP-001", "Pending", LocalDateTime.now(), student, internship);

        applicationRepo.create(application);
        System.out.println("Application submitted successfully!");

        System.out.println("\n==========================================");
        System.out.println("          CURRENT SYSTEM STATE            ");
        System.out.println("==========================================");
        System.out.println("Registered Students:   " + studentRepo.getAll().size());
        System.out.println("Registered Companies:  " + companyRepo.getAll().size());
        System.out.println("Active Internships:    " + internshipRepo.getAll().size());
        System.out.println("Total Applications:    " + applicationRepo.getAll().size());

        System.out.println("\n--- Most Recent Application Details ---");
        System.out.println(applicationRepo.read("APP-001").toString());

        System.out.println("\nSystem integration test completed successfully.");
    }
}