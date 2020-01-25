package ro.mobileapps.vethouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointCreationDto {
    private String doctor;
    private String medical_check;
    private LocalDate local_date;
}
