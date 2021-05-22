package maksym.kruhovykh.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CarDto {

    private Integer id;

    private BrandDto brandDto;

    private TypeOfCarDto typeOfCarDto;

}
