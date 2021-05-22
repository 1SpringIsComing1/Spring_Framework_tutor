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
public class AddressDto {

    private Integer id;

    @Pattern(regexp = "[A-Za-zА-Яа-яёЁ]{1,200}", message = "Address name inCorrect")
    private String name;

    private Double latitude;

    private Double longitude;

    // TODO: 5/22/2021 если хватит времени добавить регексы, если нет удалить коментарий :)

}
