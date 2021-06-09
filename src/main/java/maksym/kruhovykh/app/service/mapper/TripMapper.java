package maksym.kruhovykh.app.service.mapper;

import maksym.kruhovykh.app.dto.TripDto;
import maksym.kruhovykh.app.repository.entity.Trip;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface TripMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "distance", source = "distance"),
            @Mapping(target = "departure", source = "departure"),
            @Mapping(target = "arrival", source = "arrival"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "departureTime", source = "departureTime"),
            @Mapping(target = "arrivalTime", source = "arrivalTime"),
            @Mapping(target = "clientDto.id", source = "client.id"),
            @Mapping(target = "clientDto.userDto", source = "client.user"),
            @Mapping(target = "driverDto", source = "driver"),
            @Mapping(target = "driverDto.userDto", source = "driver.user"),
            @Mapping(target = "driverDto.carDto", source = "driver.car"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "isShowed", source = "isShowed")

    })
    TripDto tripToTripDto(Trip trip);

    @InheritInverseConfiguration
    Trip tripDtoToTrip(TripDto user);


    default List<TripDto> mapTripsToTripsDto(List<Trip> trips) {
        List<TripDto> tripsDto = new ArrayList<>();
        for (Trip trip : trips) {
            tripsDto.add(tripToTripDto(trip));
        }
        return tripsDto;
    }
}
