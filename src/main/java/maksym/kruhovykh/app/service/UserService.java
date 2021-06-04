package maksym.kruhovykh.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maksym.kruhovykh.app.utils.Utils;
import maksym.kruhovykh.app.configuration.security.JwtProvider;
import maksym.kruhovykh.app.dto.LoginDto;
import maksym.kruhovykh.app.dto.SignUpDto;
import maksym.kruhovykh.app.dto.UserDto;
import maksym.kruhovykh.app.repository.UserRepository;
import maksym.kruhovykh.app.repository.entity.User;
import maksym.kruhovykh.app.service.exception.AuthException;
import maksym.kruhovykh.app.service.mapper.UserMapper;
import maksym.kruhovykh.app.service.enumeration.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private static final String USER_IS_EMPTY = "User is Empty";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public String createUserToken(LoginDto loginDto) {
        String email = loginDto.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            try {
                return getAuthenticationToken(loginDto);
            } catch (BadCredentialsException e) {
                throw new AuthException("Wrong Password");
            }
        } else {
            throw new AuthException("User [" + loginDto.getEmail() + "] doesn't exist");
        }
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
            throw new AuthException("Passwords must match");
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

    private String getAuthenticationToken(LoginDto loginDto) {
        String email = loginDto.getEmail();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, loginDto.getPassword()));

        Collection<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        return jwtProvider.createToken(email, roles);
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
