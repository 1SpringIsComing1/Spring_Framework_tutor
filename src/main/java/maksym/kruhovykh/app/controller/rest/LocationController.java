package maksym.kruhovykh.app.controller.rest;

import lombok.AllArgsConstructor;
import maksym.kruhovykh.app.dto.CarDto;
import maksym.kruhovykh.app.dto.LocationDto;
import maksym.kruhovykh.app.service.LocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/location")
public class LocationController {

    private static final int DEFAULT_SIZE_PAGE = 10;
    private final LocationService locationService;

    @PreAuthorize("hasRole('ROLE_CLIENT')" + " && hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LocationDto findById(@PathVariable Integer id) {

        return locationService.findById(id);
    }
    @PreAuthorize("hasRole('ROLE_CLIENT')" + " && hasRole('ROLE_ADMIN')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<LocationDto> findAll(@PageableDefault(size = DEFAULT_SIZE_PAGE) Pageable pageable) {
        return locationService.findAll(pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDto createLocation(LocationDto locationDto) {
        return locationService.create(locationDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public LocationDto updateDriver(LocationDto locationDto) {
        return locationService.update(locationDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(LocationDto locationDto) {
        locationService.delete(locationDto);
    }
}
