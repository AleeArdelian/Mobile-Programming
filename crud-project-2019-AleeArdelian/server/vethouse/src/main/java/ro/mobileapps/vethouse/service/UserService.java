package ro.mobileapps.vethouse.service;

import ro.mobileapps.vethouse.dto.UserCreationDto;
import ro.mobileapps.vethouse.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
    UserDto addUser(UserCreationDto userCreationDto);
    void removeUser(String email);
    UserDto updateUser(UserCreationDto userCreationDto, String email);
}
