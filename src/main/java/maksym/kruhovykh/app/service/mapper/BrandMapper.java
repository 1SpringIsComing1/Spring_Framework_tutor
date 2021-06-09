package maksym.kruhovykh.app.service.mapper;

import maksym.kruhovykh.app.dto.BrandDto;
import maksym.kruhovykh.app.repository.entity.Brand;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface BrandMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),

    })
    BrandDto brandToBrandDto(Brand brand);

    @InheritInverseConfiguration
    Brand brandDtoToBrand(BrandDto brandDto);
}
