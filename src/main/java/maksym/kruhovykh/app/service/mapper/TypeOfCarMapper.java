package maksym.kruhovykh.app.service.mapper;

import maksym.kruhovykh.app.dto.TypeOfCarDto;
import maksym.kruhovykh.app.repository.entity.TypeOfCar;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface TypeOfCarMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "transportationPricePerKm", source = "transportationPricePerKm"),
            @Mapping(target = "name", source = "name"),

    })
    TypeOfCarDto typeOfCarToTypeOfCarDto(TypeOfCar typeOfCar);

    @InheritInverseConfiguration
    TypeOfCar typeOfCarDtoToTypeOfCar(TypeOfCarDto typeOfCarDto);
}
