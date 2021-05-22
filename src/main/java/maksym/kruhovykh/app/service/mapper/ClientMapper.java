package maksym.kruhovykh.app.service.mapper;

import maksym.kruhovykh.app.dto.ClientDto;
import maksym.kruhovykh.app.repository.entity.Client;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ClientMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "userDto", source = "user"),
//            @Mapping(target = "userDto.id", source = "user.id"),
//            @Mapping(target = "userDto.firstName", source = "user.firstName"),
//            @Mapping(target = "userDto.lastName", source = "user.lastName"),
//            @Mapping(target = "userDto.email", source = "user.email"),
//            @Mapping(target = "userDto.password", source = "user.password"),

    })
    ClientDto clientToClientDto(Client client);

    @InheritInverseConfiguration
    Client clientDtoToClient(ClientDto clientDto);
}
