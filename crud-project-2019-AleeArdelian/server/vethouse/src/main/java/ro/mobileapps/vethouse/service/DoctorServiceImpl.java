package ro.mobileapps.vethouse.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.mobileapps.vethouse.dto.DoctorCreationDto;
import ro.mobileapps.vethouse.dto.DoctorDto;
import ro.mobileapps.vethouse.mapper.DoctorMapper;
import ro.mobileapps.vethouse.model.Doctor;
import ro.mobileapps.vethouse.model.User;
import ro.mobileapps.vethouse.repository.DoctorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public List<DoctorDto> getDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::modelToDto)
                .collect(Collectors.toList());
    }


    public DoctorDto addDoctor(DoctorCreationDto doctorCreationDto) {
        Doctor doctor = doctorMapper.dtoToModel(doctorCreationDto);
        return doctorMapper.modelToDto(doctorRepository.save(doctor));
    }


    public void removeDoctor(String email) {
        Doctor user = doctorRepository.findByEmail(email).get();
        doctorRepository.deleteById(user.getId());
    }


    public DoctorDto updateDoctor(DoctorCreationDto doctorDto, int doctorId) {
        Doctor doc = doctorRepository.findById(doctorId).orElseThrow(() -> {
            throw new RuntimeException("Not found");
        });
        Doctor doctor = doctorMapper.dtoToModel(doctorDto);
        doc.setSpeciality(doctor.getSpeciality());
        doc.setFirstName(doctor.getFirstName());
        doc.setLastName(doctor.getLastName());

        doc.setSpeciality(doctor.getSpeciality());
        return doctorMapper.modelToDto(doctorRepository.save(doc));
    }
}
