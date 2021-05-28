package maksym.kruhovykh.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maksym.kruhovykh.app.Utils;
import maksym.kruhovykh.app.dto.LocationDto;
import maksym.kruhovykh.app.repository.LocationRepository;
import maksym.kruhovykh.app.repository.entity.Location;
import maksym.kruhovykh.app.service.mapper.LocationMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class LocationService {

    private static final String ADDRESS_IS_EMPTY = "Location is empty";

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;


    public LocationDto findById(Integer id) {
        return locationRepository
                .findById(id)
                .map(locationMapper::locationToLocationDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<LocationDto> findAll() {
        List<Location> all = locationRepository.findAll();
        return all.stream()
                .map(locationMapper::locationToLocationDto)
                .sorted(Comparator.comparing(LocationDto::getLocationName))
                .collect(Collectors.toList());

    }

    public LocationDto create(LocationDto locationDto) {
        Utils.isNull(locationDto, ADDRESS_IS_EMPTY);
        Location location = locationMapper.locationDtoToLocation(locationDto);

        if (locationRepository.findByLocationName(location.getLocationName()).isPresent()) {
            log.error("Location  [" + location.getLocationName() + "] already exist");
            throw new EntityExistsException("Location [" + location.getLocationName() + "] already exist");
        }

        return locationMapper.locationToLocationDto(locationRepository.save(location));
    }

    public LocationDto update(LocationDto locationDto) {
        Utils.isNull(locationDto, ADDRESS_IS_EMPTY);

        return locationRepository.findById(locationDto.getId())
                .map(location -> {
                    location = locationMapper.locationDtoToLocation(locationDto);
                    locationRepository.save(location);
                    return locationDto;
                }).orElseThrow(EntityNotFoundException::new);
    }

    public void delete(LocationDto locationDto) {
        Utils.isNull(locationDto, ADDRESS_IS_EMPTY);
        locationRepository.findById(locationDto.getId())
                .map(location -> {
                    locationRepository.delete(location);
                    log.info("Location  " + location.getLocationName() + " deleted");
                    return locationDto;
                })
                .orElseThrow(EntityNotFoundException::new);
    }
}
