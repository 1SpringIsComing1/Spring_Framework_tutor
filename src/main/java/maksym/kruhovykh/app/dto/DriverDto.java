package maksym.kruhovykh.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class DriverDto {

    private Integer id;

    private CarDto carDto;

    private UserDto userDto;

}
