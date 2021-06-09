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
public class LocationDto {

    private Integer id;

    @Pattern(regexp = "[A-Za-zА-Яа-яёЁ]{1,200}", message = "Address name inCorrect")

    private String locationName;

}
