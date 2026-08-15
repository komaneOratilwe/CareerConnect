package za.ac.cput.controller;

import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Profile;
import za.ac.cput.service.IProfileService;

import java.util.List;


@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "http://localhost:5173")
public class ProfileController {
    private final IProfileService profileService;

    public ProfileController(IProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/create")
    public Profile create(@RequestBody Profile profile) {
        return profileService.create(profile);
    }

    @GetMapping("/read/{profileId}")
    public Profile read(@PathVariable String profileId) {
        return profileService.read(profileId);
    }

    @PutMapping("/update")
    public Profile update(@RequestBody Profile profile) {
        return profileService.update(profile);
    }

    @DeleteMapping("/delete/{profileId}")
    public boolean delete(@PathVariable String profileId) {
        return profileService.delete(profileId);
    }

    @GetMapping("/getAll")
    public List<Profile> getAll() {
        return profileService.getAll();
    }
}