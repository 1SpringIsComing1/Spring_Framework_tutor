package maksym.kruhovykh.app.controller.rest;

import lombok.AllArgsConstructor;
import maksym.kruhovykh.app.dto.TripDto;
import maksym.kruhovykh.app.service.TripService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping("/api/trip")
public class TripController {

    private static final int DEFAULT_SIZE_PAGE = 10;
    private final TripService tripService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TripDto findById(@PathVariable Integer id) {
        return tripService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<TripDto> findAll(@PageableDefault(size = DEFAULT_SIZE_PAGE) Pageable pageable) {
        return tripService.findAll(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TripDto create(TripDto tripDto) {
        return tripService.create(tripDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public TripDto update(TripDto tripDto) {
        return tripService.update(tripDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(TripDto tripDto) {
        tripService.delete(tripDto);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public TripDto register(Principal principal, TripDto tripDto) {
        return tripService.registerForTrip(tripDto, principal.getName());
    }
}
