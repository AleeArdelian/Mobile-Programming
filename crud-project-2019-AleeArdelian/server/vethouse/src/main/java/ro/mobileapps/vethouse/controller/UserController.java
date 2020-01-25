package ro.mobileapps.vethouse.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.mobileapps.vethouse.dto.UserCreationDto;
import ro.mobileapps.vethouse.dto.UserDto;
import ro.mobileapps.vethouse.service.UserService;

import java.util.List;
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController implements UserControllerAPI {

    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers() {

        return userService.getUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody UserCreationDto userCreationDto) {

        return userService.addUser(userCreationDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void removeUser(@RequestParam String email) {
        userService.removeUser(email);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@RequestBody UserCreationDto userCreationDto, @RequestParam String email) {
        return userService.updateUser(userCreationDto,email);
    }
}
