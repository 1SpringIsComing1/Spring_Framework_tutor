package maksym.kruhovykh.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

}
