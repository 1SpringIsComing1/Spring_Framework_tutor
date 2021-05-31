package maksym.kruhovykh.app.dto;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@Builder
public class SignUpDto {

    private static final String REGEX_EMAIL = "^[\\w-]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String EMAIL_MESSAGE = "Email should be valid, abc@abc.abc";
    private static final String PASSWORD_REGEX = "^[\\w]{4,}$";
    private static final String PASSWORD_MESSAGE = "Allowed A-Z, a-z, 0-9, _";
    private static final String NAME_REGEX = "[A-Za-zА-Яа-яёЁ]{2,200}";

    @Pattern(regexp = NAME_REGEX, message = "First name inCorrect")
    private String firstName;

    @Pattern(regexp = NAME_REGEX, message = "Last name inCorrect")
    private String lastName;

    @Email(message = EMAIL_MESSAGE, regexp = REGEX_EMAIL)
    private String email;

    @Pattern(regexp = PASSWORD_REGEX, message = PASSWORD_MESSAGE)
    private String password;
    private String repeatPassword;

}
