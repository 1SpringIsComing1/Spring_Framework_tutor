package maksym.kruhovykh.app.controller.rest;


import lombok.AllArgsConstructor;
import maksym.kruhovykh.app.configuration.security.JwtProvider;
import maksym.kruhovykh.app.dto.LoginDto;
import maksym.kruhovykh.app.dto.SignUpDto;
import maksym.kruhovykh.app.dto.UserDto;
import maksym.kruhovykh.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        response.setHeader(JwtProvider.TOKEN_HEADER,
                JwtProvider.TOKEN_PREFIX + userService.createUserToken(loginDto));
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto signUp(@Valid @RequestBody SignUpDto signUpDto) {
        return userService.createUser(signUpDto);
    }

}
