package maksym.kruhovykh.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maksym.kruhovykh.app.dto.UserDto;
import maksym.kruhovykh.app.repository.UserRepository;
import maksym.kruhovykh.app.repository.entity.User;
import maksym.kruhovykh.app.service.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto findByUserId(Integer id) {
        return userMapper.userToUserDto(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User id = [" + id +
                        "] not found")));
    }

    public UserDto update(UserDto userDto) {

        return userRepository.findById(userDto.getId())
                .map(user -> {
                    user.setEmail(userDto.getEmail());
                    user.setPassword(userDto.getPassword());
                    user.setFirstName(userDto.getFirstName());
                    user.setLastName(userDto.getLastName());
                    User save = userRepository.save(user);

                    return userMapper.userToUserDto(save);
                })
                .orElseThrow(() -> new EntityNotFoundException("User with Id [" + userDto.getId() + "] doesn't exist"));
    }

    public void register(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            log.warn("User with email [" + userDto.getEmail() + "] already exist");
            throw new RuntimeException("User with email [" + userDto.getEmail() + "] already exist");
        }

        userRepository.save(userMapper.userDtoToUser(userDto));
    }

    public void delete(UserDto userDto) {
        if (!userRepository.findById(userDto.getId()).isPresent()) {
            log.warn("User with Id [" + userDto.getId() + "] doesn't exist");
            throw new EntityNotFoundException("User with Id [" + userDto.getId() + "] doesn't exist");
        } else userRepository.delete(userMapper.userDtoToUser(userDto));

    }

}
