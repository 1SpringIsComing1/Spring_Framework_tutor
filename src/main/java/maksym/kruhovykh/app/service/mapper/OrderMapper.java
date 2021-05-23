package maksym.kruhovykh.app.service.mapper;

import maksym.kruhovykh.app.dto.OrderDto;
import maksym.kruhovykh.app.repository.entity.Order;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface OrderMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "distance", source = "distance"),
            @Mapping(target = "departures.id", source = "departures.id"),
            @Mapping(target = "departures.name", source = "departures.name"),
            @Mapping(target = "departures.latitude", source = "departures.latitude"),
            @Mapping(target = "departures.longitude", source = "departures.longitude"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "dateCreation", source = "dateCreation"),
            @Mapping(target = "clientDto.id",source = "client.id"),
            @Mapping(target = "clientDto.userDto",source = "client.user"),
            @Mapping(target = "driverDto",source = "driver"),
            @Mapping(target = "status",source = "status")

    })
    OrderDto orderToOrderDto(Order order);

    @InheritInverseConfiguration
    Order orderDtoToOrder(OrderDto user);
}
