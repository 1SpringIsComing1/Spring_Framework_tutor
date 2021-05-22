package maksym.kruhovykh.app.service.mapper;

import maksym.kruhovykh.app.dto.AddressDto;
import maksym.kruhovykh.app.repository.entity.Address;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface AddressMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "latitude", source = "latitude"),
            @Mapping(target = "longitude", source = "longitude"),
    })
    AddressDto addressToAddressDto(Address address);

    @InheritInverseConfiguration
    Address addressDtoToAddress(AddressDto addressDto);
}
