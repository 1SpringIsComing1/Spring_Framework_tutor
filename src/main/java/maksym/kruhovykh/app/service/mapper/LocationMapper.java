package maksym.kruhovykh.app.service.mapper;

import maksym.kruhovykh.app.dto.LocationDto;
import maksym.kruhovykh.app.repository.entity.Location;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface LocationMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "locationName", source = "locationName"),
    })
    LocationDto locationToLocationDto(Location location);

    @InheritInverseConfiguration
    Location locationDtoToLocation(LocationDto locationDto);


}
