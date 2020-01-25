package ro.mobileapps.vethouse.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ro.mobileapps.vethouse.dto.UserCreationDto;
import ro.mobileapps.vethouse.dto.UserDto;

import java.util.List;

@Api(description = "User API", tags = {"User"})
public interface UserControllerAPI {
    @ApiOperation(value = "Get a list of all users")
    List<UserDto> getUsers();

    @ApiOperation(value = "Add a user")
    UserDto addUser(UserCreationDto userCreationDto);

    @ApiOperation(value = "Remove an existing user")
    void removeUser(String email);

    @ApiOperation(value = "Update an existing user")
    UserDto updateUser(UserCreationDto userDto, String email);
}
