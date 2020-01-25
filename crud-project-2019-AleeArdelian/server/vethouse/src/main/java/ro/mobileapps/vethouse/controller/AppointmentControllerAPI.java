package ro.mobileapps.vethouse.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ro.mobileapps.vethouse.dto.AppointCreationDto;
import ro.mobileapps.vethouse.dto.AppointmentDto;
import ro.mobileapps.vethouse.model.Appointment;

import java.util.List;

@Api(description = "Appointment API", tags = {"Appointment"})

public interface AppointmentControllerAPI {

    @ApiOperation(value = "Get a list of all appointments")
    List<AppointmentDto> getAppointments();

    @ApiOperation(value = "Add an appointment")
    AppointmentDto addAppointment(AppointCreationDto appCreationDto);

    @ApiOperation(value = "Remove an existing appointment")
    void removeAppointment(int appId);

    @ApiOperation(value = "Update an existing appointment")
    AppointmentDto updateApp(AppointCreationDto appCreationDto, int appId);
}
