package maksym.kruhovykh.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maksym.kruhovykh.app.dto.CarDto;
import maksym.kruhovykh.app.dto.ClientDto;
import maksym.kruhovykh.app.dto.TripDto;
import maksym.kruhovykh.app.repository.TripRepository;
import maksym.kruhovykh.app.repository.entity.Trip;
import maksym.kruhovykh.app.service.mapper.TripMapper;
import maksym.kruhovykh.app.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.function.Function;


@RequiredArgsConstructor
@Slf4j
@Service
public class TripService {

    private static final String TRIP_IS_EMPTY = "Trip is empty";
    private static final Integer averageSpeed = 80;
    private static final int HOURS_FOR_REST = 2;

    private final ClientService clientService;
    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    public TripDto findById(Integer id) {
        return tripRepository.findById(id)
                .map(tripMapper::tripToTripDto)
                .orElseThrow(() -> new EntityNotFoundException("Trip  [" + id + "] doesn't exist"));
    }

    public TripDto create(TripDto tripDto) {
        Utils.isNull(tripDto, TRIP_IS_EMPTY);
        Trip trip = tripMapper.tripDtoToTrip(tripDto);
        Integer distance = trip.getDistance();
        calculateTimeDeparture(trip, distance);

        checkPossibilityOfCreatingOrder(trip);

        calculatePrice(trip, distance);

        return tripMapper.tripToTripDto(tripRepository.save(trip));

    }

    public TripDto registerForTrip(TripDto tripDto, String clientEmail) {
        TripDto trip = tripRepository.findById(tripDto.getId())
                .map(tripMapper::tripToTripDto)
                .orElseThrow(EntityNotFoundException::new);
        ClientDto client = clientService.findClientByUserEmail(clientEmail);
        CarDto carDto = trip.getDriverDto().getCarDto();

        if (carDto.getMaxCountOfPlaces() - carDto.getCurrentCountOfPlaces() < 1) {
            log.error("Car is Full");
            throw new RuntimeException("Car is Full");
        }
        trip.setClientDto(client);

        return tripMapper.tripToTripDto(tripRepository.save(tripMapper.tripDtoToTrip(trip)));
    }

    public TripDto update(TripDto tripDto) {
        Utils.isNull(tripDto, TRIP_IS_EMPTY);
        return tripRepository
                .findById(tripDto.getId())
                .map(updateOrder(tripDto))
                .map(tripMapper::tripToTripDto)
                .orElseThrow(() -> new EntityNotFoundException("Trip with Id [" + tripDto.getId() + "] doesn't exist"));

    }

    public Page<TripDto> findAll(Pageable pageable) {
        Page<Trip> tripDtos = tripRepository.findAll(pageable);
        return tripDtos.map(tripMapper::tripToTripDto);
    }

    public void delete(TripDto tripDto) {
        Utils.isNull(tripDto, TRIP_IS_EMPTY);
        tripRepository
                .findById(tripDto.getId())
                .map(deleteOrder(tripDto))
                .map(tripMapper::tripDtoToTrip)
                .orElseThrow(() -> new EntityNotFoundException("Trip with Id [" + tripDto.getId() + "] doesn't exist"));

    }

    private Function<Trip, TripDto> deleteOrder(TripDto tripDto) {
        return tripDto1 -> {
            tripRepository.delete(tripDto1);
            return tripDto;
        };
    }

    private void calculateTimeDeparture(Trip trip, Integer distance) {
        int timeTrip = distance / averageSpeed;
        LocalDateTime departureTime = trip.getDepartureTime();
        trip.setDepartureTime(departureTime.plusHours(timeTrip + HOURS_FOR_REST));
    }

    private void calculatePrice(Trip trip, Integer distance) {
        Double transportationPricePerKm = trip.getDriver().getCar().getTypeOfCar().getTransportationPricePerKm();
        trip.setPrice(transportationPricePerKm * distance);
    }

    private Function<Trip, Trip> updateOrder(TripDto tripDto) {
        return trip -> {
            Trip save = tripMapper.tripDtoToTrip(tripDto);
            return tripRepository.save(save);

        };
    }

    private void checkPossibilityOfCreatingOrder(Trip trip) {
        boolean periodIsEmpty = tripRepository.tripBetween(
                trip.getArrivalTime(),
                trip.getDepartureTime(),
                trip.getDriver().getId())
                .isEmpty();
        if (!periodIsEmpty) {
            log.warn("Cannot create trip.Driver " + trip.getDriver() + " is busy");
            throw new RuntimeException("Cannot create trip.Driver "
                    + trip.getDriver().getUser().getFirstName() + " "
                    + trip.getDriver().getUser().getLastName()
                    + " is busy");
        }

    }


}
