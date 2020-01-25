package ro.mobileapps.vethouse.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.mobileapps.vethouse.dto.UserCreationDto;
import ro.mobileapps.vethouse.dto.UserDto;
import ro.mobileapps.vethouse.mapper.UserMapper;
import ro.mobileapps.vethouse.model.User;
import ro.mobileapps.vethouse.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::modelToDto)
                .collect(Collectors.toList());
    }

    public UserDto addUser(UserCreationDto userCreationDto) {
        User user = userMapper.dtoToModel(userCreationDto);
        return userMapper.modelToDto(userRepository.save(user));
    }

    public void removeUser(String email) {
        User user = userRepository.findByEmail(email).get();
        userRepository.deleteById(user.getId());
    }

    public UserDto updateUser(UserCreationDto userCreationDto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new RuntimeException("Not found");
        });
        User user1 = userMapper.dtoToModel(userCreationDto);
        user.setFirstName(user1.getFirstName());
        user.setLastName(user1.getLastName());
        user.setPassword(user1.getPassword());
        return userMapper.modelToDto(userRepository.save(user));
    }
}
