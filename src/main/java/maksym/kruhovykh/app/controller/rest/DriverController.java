package maksym.kruhovykh.app.controller.rest;

import lombok.AllArgsConstructor;
import maksym.kruhovykh.app.dto.DriverDto;
import maksym.kruhovykh.app.service.DriverService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/driver")
public class DriverController {
    private static final int DEFAULT_SIZE_PAGE = 10;
    private final DriverService driverService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DriverDto findById(@PathVariable Integer id) {
        return driverService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<DriverDto> findAll(@PageableDefault(size = DEFAULT_SIZE_PAGE) Pageable pageable) {
        return driverService.findAll(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDto createDriver(DriverDto driverDto) {
        return driverService.create(driverDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public DriverDto updateDriver(DriverDto driverDto) {
        return driverService.update(driverDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDriver(DriverDto driverDto) {
        driverService.delete(driverDto);
    }
}
