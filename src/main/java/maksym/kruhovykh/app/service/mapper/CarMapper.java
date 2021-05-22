package maksym.kruhovykh.app.service.mapper;

import maksym.kruhovykh.app.dto.CarDto;
import maksym.kruhovykh.app.repository.entity.Car;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CarMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "typeOfCarDto", source = "typeOfCar."),
            @Mapping(target = "brandDto", source = "brand."),
    })
    CarDto carToCarDto(Car car);


    @InheritInverseConfiguration
    Car carDtoToCar(CarDto carDto);
}
