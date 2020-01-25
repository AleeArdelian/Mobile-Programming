package ro.mobileapps.vethouse.service;

import ro.mobileapps.vethouse.dto.DoctorCreationDto;
import ro.mobileapps.vethouse.dto.DoctorDto;

import java.util.List;

public interface DoctorService {
    List<DoctorDto> getDoctors();
    DoctorDto addDoctor(DoctorCreationDto doctorCreationDto);
    void removeDoctor(String email);
    DoctorDto updateDoctor(DoctorCreationDto doctorDto, int doctorId);


}
