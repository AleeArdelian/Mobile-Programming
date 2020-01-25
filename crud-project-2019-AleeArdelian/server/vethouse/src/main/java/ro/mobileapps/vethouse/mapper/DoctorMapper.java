package ro.mobileapps.vethouse.mapper;

import org.springframework.stereotype.Component;
import ro.mobileapps.vethouse.dto.DoctorCreationDto;
import ro.mobileapps.vethouse.dto.DoctorDto;
import ro.mobileapps.vethouse.model.Doctor;

@Component
public class DoctorMapper {

    public Doctor dtoToModel(DoctorCreationDto doctorCreationDto){
        return Doctor.builder()
                .firstName(doctorCreationDto.getFirstName())
                .lastName(doctorCreationDto.getLastName())
                .email(doctorCreationDto.getEmail())
                .password(doctorCreationDto.getPassword())
                .speciality(doctorCreationDto.getSpeciality())
                .build();
    }


    public DoctorDto modelToDto(Doctor doctor){
        return DoctorDto.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .email(doctor.getEmail())
                .password(doctor.getPassword())
                .speciality(doctor.getSpeciality())
                .build();
    }
}
