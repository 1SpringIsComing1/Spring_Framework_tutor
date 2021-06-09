package maksym.kruhovykh.app.controller.rest;

import lombok.AllArgsConstructor;
import maksym.kruhovykh.app.dto.CarDto;
import maksym.kruhovykh.app.service.CarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/car")
public class CarController {
    private static final int DEFAULT_SIZE_PAGE = 10;
    private final CarService carService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarDto findById(@PathVariable Integer id) {
        return carService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CarDto> findAll(@PageableDefault(size = DEFAULT_SIZE_PAGE) Pageable pageable) {
        return carService.findAll(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDto create(CarDto carDto) {
        return carService.create(carDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CarDto updateDriver(CarDto carDto) {
        return carService.update(carDto);
    }


    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(CarDto carDto) {
        carService.delete(carDto);
    }
}
