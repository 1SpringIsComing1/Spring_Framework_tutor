package maksym.kruhovykh.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maksym.kruhovykh.app.Utils;
import maksym.kruhovykh.app.dto.UserDto;
import maksym.kruhovykh.app.repository.UserRepository;
import maksym.kruhovykh.app.repository.entity.User;
import maksym.kruhovykh.app.service.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private static final String USER_IS_EMPTY = "User is Empty";
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto findByUserId(Integer id) {
        return userRepository
                .findById(id)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new RuntimeException("User id = [" + id +
                        "] not found"));
    }

    public UserDto update(UserDto userDto) {
        Utils.isNull(userDto, USER_IS_EMPTY);
        return userRepository
                .findById(userDto.getId())
                .map(updateUser(userDto))
                .orElseThrow(() -> new EntityNotFoundException("User with Id [" + userDto.getId() + "] doesn't exist"));
    }

    public void register(UserDto userDto) {
        Utils.isNull(userDto, USER_IS_EMPTY);
        isExist(userDto);

        userRepository.save(userMapper.userDtoToUser(userDto));
    }

    public void delete(UserDto userDto) {
        Utils.isNull(userDto, USER_IS_EMPTY);
        isNotExist(userDto);

        userRepository.delete(userMapper.userDtoToUser(userDto));
    }

    private void isNotExist(UserDto userDto) {
        if (!userRepository.findById(userDto.getId()).isPresent()) {
            log.error("User with Id [" + userDto.getId() + "] doesn't exist");
            throw new EntityNotFoundException("User with Id [" + userDto.getId() + "] doesn't exist");
        }
    }

    private void isExist(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            log.error("User with email [" + userDto.getEmail() + "] already exist");
            throw new RuntimeException("User with email [" + userDto.getEmail() + "] already exist");
        }
    }

    private Function<User, UserDto> updateUser(UserDto userDto) {
        return user -> {
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            User save = userRepository.save(user);

            return userMapper.userToUserDto(save);
        };
    }
}
