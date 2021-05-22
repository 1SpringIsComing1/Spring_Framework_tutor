package maksym.kruhovykh.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer id;
    @Pattern(regexp = "[A-Za-zА-Яа-яёЁ]{2,200}", message = "First name inCorrect")
    private String firstName;

    @Pattern(regexp = "[A-Za-zА-Яа-яёЁ]{2,200}", message = "Last name inCorrect")
    private String lastName;

    @Pattern(regexp = "[a-zA-Z0-9]{1,}[@]{1}[a-z]{3,}[.]{1}+[a-z]{2,}", message = "enter the email in the specified format : name@domain.com")
    private String email;

    @Pattern(regexp = "[A-Za-zA-Яа-яёЁ!_#$%^&*()-=+-]{2,32}", message = "Password inCorrect")
    private String password;

}
