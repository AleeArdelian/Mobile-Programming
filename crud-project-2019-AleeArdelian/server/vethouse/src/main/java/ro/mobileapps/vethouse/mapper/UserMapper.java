package ro.mobileapps.vethouse.mapper;

import org.springframework.stereotype.Component;
import ro.mobileapps.vethouse.dto.UserCreationDto;
import ro.mobileapps.vethouse.dto.UserDto;
import ro.mobileapps.vethouse.model.User;
@Component
public class UserMapper {
    public User dtoToModel(UserCreationDto userCreationDto){
        return User.builder()
                .firstName(userCreationDto.getFirstName())
                .lastName(userCreationDto.getLastName())
                .email(userCreationDto.getEmail())
                .password(userCreationDto.getPassword())
                .build();
    }


    public UserDto modelToDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
