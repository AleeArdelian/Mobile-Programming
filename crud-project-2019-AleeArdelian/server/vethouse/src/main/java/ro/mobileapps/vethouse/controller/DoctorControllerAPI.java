package ro.mobileapps.vethouse.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ro.mobileapps.vethouse.dto.DoctorCreationDto;
import ro.mobileapps.vethouse.dto.DoctorDto;

import java.util.List;

@Api(description = "Doctor API", tags = {"Doctor"})
public interface DoctorControllerAPI {

    @ApiOperation(value = "Get a list of all doctors")
    List<DoctorDto> getDoctors();

    @ApiOperation(value = "Add a doctor")
    DoctorDto addDoctor(DoctorCreationDto doctorCreationDto);

    @ApiOperation(value = "Remove an existing doctor")
    void removeDoctor(String email);

    @ApiOperation(value = "Update an existing doctor")
    DoctorDto updateDoctor(DoctorCreationDto doctorDto, int doctorId);


}
