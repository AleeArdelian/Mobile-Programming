package ro.mobileapps.vethouse.mapper;

import org.springframework.stereotype.Component;
import ro.mobileapps.vethouse.dto.AppointCreationDto;
import ro.mobileapps.vethouse.dto.AppointmentDto;
import ro.mobileapps.vethouse.model.Appointment;

@Component
public class AppointmentMapper {

    public Appointment dtoToModel(AppointCreationDto appCreationDto){
        return Appointment.builder()
                .doctor(appCreationDto.getDoctor())
                .medical_check(appCreationDto.getMedical_check())
                .local_date(appCreationDto.getLocal_date())
                .build();
    }

    public AppointmentDto modelToDto(Appointment app){
        return AppointmentDto.builder()
                .id(app.getId())
                .doctor(app.getDoctor())
                .local_date(app.getLocal_date())
                .medical_check(app.getMedical_check())
                .build();
    }
}
