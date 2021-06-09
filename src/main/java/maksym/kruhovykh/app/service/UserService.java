package maksym.kruhovykh.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maksym.kruhovykh.app.dto.LoginDto;
import maksym.kruhovykh.app.dto.SignUpDto;
import maksym.kruhovykh.app.dto.UserDto;
import maksym.kruhovykh.app.repository.UserRepository;
import maksym.kruhovykh.app.repository.entity.User;
import maksym.kruhovykh.app.service.enumeration.Role;
import maksym.kruhovykh.app.service.mapper.UserMapper;
import maksym.kruhovykh.app.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private static final String USER_IS_EMPTY = "User is Empty";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public String createUserToken(LoginDto loginDto) {
        throw new UnsupportedOperationException("Will be implements on 6 task");
    }

    public UserDto findByUserId(Integer id) {
        return userRepository
                .findById(id)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new EntityNotFoundException("User id = [" + id +
                        "] not found"));
    }

    public UserDto update(UserDto userDto) {
        Utils.isNull(userDto, USER_IS_EMPTY);
        return userRepository
                .findById(userDto.getId())
                .map(updateUser(userDto))
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new EntityNotFoundException("User with Id [" + userDto.getId() + "] doesn't exist"));
    }

    public UserDto createUser(SignUpDto signUpDto) {
        if (!signUpDto.getPassword().equals(signUpDto.getRepeatPassword())) {
            throw new RuntimeException("Passwords must match");
        }

        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new EntityExistsException("User with email [" + signUpDto.getEmail() + "] already exist");
        }

        User user = User.builder()
                .firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .roles(Collections.singleton(Role.CLIENT))
                .build();

        User save = userRepository.save(user);
        save.setPassword(null);

        return userMapper.userToUserDto(save);
    }

    public Page<UserDto> findAll(Pageable pageable) {
        Page<User> clientPage = userRepository.findAll(pageable);
        return clientPage.map(userMapper::userToUserDto);
    }

    public void delete(UserDto userDto) {
        userRepository.findByEmail(userDto.getEmail())
                .map(user -> {
                    userRepository.delete(user);
                    return user;
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    public UserDto findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::userToUserDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    private Function<User, User> updateUser(UserDto userDto) {
        return user -> {
            User save = userMapper.userDtoToUser(userDto);

            userRepository.save(save);

            log.info("User with id [" + userDto.getId() + "] updated ");
            return save;

        };
    }


}
