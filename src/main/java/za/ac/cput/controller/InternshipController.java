package za.ac.cput.controller;

/*
InternshipController.java
Internship REST Controller
Author: Refilwe Mabena (231013051)
Date: 8 August 2026
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Internship;
import za.ac.cput.service.IInternshipService;

import java.util.List;

@RestController
@RequestMapping("/api/internship")
@CrossOrigin(origins = "http://localhost:5173")
public class InternshipController {

    private final IInternshipService internshipService;

    @Autowired
    public InternshipController(IInternshipService internshipService) {
        this.internshipService = internshipService;
    }

    @PostMapping("/create")
    public Internship create(@RequestBody Internship internship) {
        return internshipService.create(internship);
    }

    @GetMapping("/read/{internshipId}")
    public Internship read(@PathVariable String internshipId) {
        return internshipService.read(internshipId);
    }

    @PutMapping("/update")
    public Internship update(@RequestBody Internship internship) {
        return internshipService.update(internship);
    }

    @DeleteMapping("/delete/{internshipId}")
    public boolean delete(@PathVariable String internshipId) {
        return internshipService.delete(internshipId);
    }

    @GetMapping("/getAll")
    public List<Internship> getAll() {
        return internshipService.getAll();
    }

    @GetMapping("/getByLocation")
    public List<Internship> getByLocation(
            @RequestParam String location) {
        return internshipService.getInternshipsByLocation(location);
    }

    @GetMapping("/getByTitle")
    public List<Internship> getByTitle(
            @RequestParam String title) {
        return internshipService.getInternshipsByTitle(title);
    }
}
