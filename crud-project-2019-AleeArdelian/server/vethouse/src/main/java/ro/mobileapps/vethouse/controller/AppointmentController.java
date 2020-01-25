package ro.mobileapps.vethouse.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.mobileapps.vethouse.dto.AppointCreationDto;
import ro.mobileapps.vethouse.dto.AppointmentDto;
import ro.mobileapps.vethouse.model.Appointment;
import ro.mobileapps.vethouse.service.AppointmentService;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@AllArgsConstructor
public class AppointmentController  implements AppointmentControllerAPI{

    private final AppointmentService appService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentDto> getAppointments() {
        return appService.getAppointments();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDto addAppointment(@RequestBody AppointCreationDto appCreationDto) {
        return appService.addAppointment(appCreationDto);
    }

    @DeleteMapping("/{appId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeAppointment(@PathVariable int appId) {
        appService.removeAppointment(appId);
    }

    @PutMapping("/{appId}")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDto updateApp(@RequestBody AppointCreationDto appCreationDto, @PathVariable int appId) {
        return appService.updateAppointment(appCreationDto, appId);
    }
}
