package ro.mobileapps.vethouse.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.mobileapps.vethouse.dto.DoctorCreationDto;
import ro.mobileapps.vethouse.dto.DoctorDto;
import ro.mobileapps.vethouse.service.DoctorService;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@AllArgsConstructor
public class DoctorController implements DoctorControllerAPI{

    private final DoctorService doctorService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DoctorDto> getDoctors() {
        return doctorService.getDoctors();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDto addDoctor(@RequestBody DoctorCreationDto doctorCreationDto) {
        return doctorService.addDoctor(doctorCreationDto);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public void removeDoctor(@RequestParam String email) {
        doctorService.removeDoctor(email);
    }

    @PutMapping("/{doctorId}")
    @ResponseStatus(HttpStatus.OK)
    public DoctorDto updateDoctor(@RequestBody DoctorCreationDto doctorDto, @PathVariable int doctorId) {
        return doctorService.updateDoctor(doctorDto,doctorId);
    }
}
