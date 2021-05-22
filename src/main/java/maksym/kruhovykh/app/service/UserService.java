package maksym.kruhovykh.app.service;

import lombok.RequiredArgsConstructor;
import maksym.kruhovykh.app.dto.UserDto;
import maksym.kruhovykh.app.repository.UserRepository;
import maksym.kruhovykh.app.service.mapper.UserMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto findByUserId(Integer id) {

        return userMapper.userToUserDto(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User id = [" + id +
                        "] not found")));
    }
    // add another methods.For example: save, update ..

}
