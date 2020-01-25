package ro.mobileapps.vethouse.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.mobileapps.vethouse.dto.AppointCreationDto;
import ro.mobileapps.vethouse.dto.AppointmentDto;
import ro.mobileapps.vethouse.mapper.AppointmentMapper;
import ro.mobileapps.vethouse.mapper.DoctorMapper;
import ro.mobileapps.vethouse.model.Appointment;
import ro.mobileapps.vethouse.repository.AppointmentRepository;
import ro.mobileapps.vethouse.repository.DoctorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appRepository;
    private final AppointmentMapper appMapper;

    public List<AppointmentDto> getAppointments() {
        return appRepository.findAll()
                .stream()
                .map(appMapper::modelToDto)
                .collect(Collectors.toList());
    }

    public AppointmentDto addAppointment(AppointCreationDto appCreationDto) {
        Appointment appointment = appMapper.dtoToModel(appCreationDto);
        return  appMapper.modelToDto(appRepository.save(appointment));
    }

    public void removeAppointment(int appId) {
        appRepository.deleteById(appId);
    }

    public AppointmentDto updateAppointment(AppointCreationDto appCreationDto, int appId) {
        Appointment app = appRepository.findById(appId).orElseThrow(() -> {
            throw new RuntimeException("Not found");
        });
        Appointment appoint = appMapper.dtoToModel(appCreationDto);
        app.setDoctor(appoint.getDoctor());
        app.setLocal_date(appoint.getLocal_date());
        app.setMedical_check(appoint.getMedical_check());
        return appMapper.modelToDto(appRepository.save(app));
    }
}
