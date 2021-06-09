package maksym.kruhovykh.app.service.mapper;

import maksym.kruhovykh.app.dto.DriverDto;
import maksym.kruhovykh.app.repository.entity.Driver;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface DriverMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "carDto", source = "car"),
            @Mapping(target = "userDto", source = "user"),

    })
    DriverDto driverToDriverDto(Driver driver);

    @InheritInverseConfiguration
    Driver driverDtoToDriver(DriverDto clientDto);
}
