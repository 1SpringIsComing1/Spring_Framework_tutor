package maksym.kruhovykh.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CarDto {

    private Integer id;

    Integer maxCountOfPlaces;

    Integer currentCountOfPlaces;

    private BrandDto brandDto;

    private TypeOfCarDto typeOfCarDto;

}
