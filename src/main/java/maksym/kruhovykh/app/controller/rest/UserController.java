package maksym.kruhovykh.app.controller.rest;

import lombok.AllArgsConstructor;
import maksym.kruhovykh.app.dto.ClientDto;
import maksym.kruhovykh.app.dto.UserDto;
import maksym.kruhovykh.app.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final int DEFAULT_SIZE_PAGE = 10;
    private final UserService userService;


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findById(@PathVariable Integer id) {
        return userService.findByUserId(id);
    }

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDto> findAll(@PageableDefault(size = DEFAULT_SIZE_PAGE) Pageable pageable) {
        return userService.findAll(pageable);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto update(UserDto userDto) {
        return userService.update(userDto);
    }


    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(UserDto userDto) {
        userService.delete(userDto);
    }

}
