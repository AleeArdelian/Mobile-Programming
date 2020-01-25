package ro.mobileapps.vethouse.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String speciality;
}
