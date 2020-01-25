package ro.mobileapps.vethouse.service;

import ro.mobileapps.vethouse.dto.AppointCreationDto;
import ro.mobileapps.vethouse.dto.AppointmentDto;

import java.util.List;

public interface AppointmentService {
    List<AppointmentDto> getAppointments();
    AppointmentDto addAppointment(AppointCreationDto appCreationDto);
    void removeAppointment(int appId);
    AppointmentDto updateAppointment(AppointCreationDto appCreationDto, int appId);
}
