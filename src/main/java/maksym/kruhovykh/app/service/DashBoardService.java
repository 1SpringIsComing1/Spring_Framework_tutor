package maksym.kruhovykh.app.service;

import liquibase.pro.packaged.D;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maksym.kruhovykh.app.dto.DashBoardDto;
import maksym.kruhovykh.app.dto.TripDto;
import maksym.kruhovykh.app.dto.UserDto;
import maksym.kruhovykh.app.repository.ClientRepository;
import maksym.kruhovykh.app.repository.TripRepository;
import maksym.kruhovykh.app.repository.UserRepository;
import maksym.kruhovykh.app.repository.entity.Client;
import maksym.kruhovykh.app.repository.entity.Location;
import maksym.kruhovykh.app.repository.entity.Trip;
import maksym.kruhovykh.app.repository.entity.User;
import maksym.kruhovykh.app.service.mapper.TripMapper;
import maksym.kruhovykh.app.utils.Status;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class DashBoardService {
    private final TripRepository tripRepository;
    private final ClientRepository clientRepository;
    private final TripMapper tripMapper;

    public DashBoardDto download(String email) {
        Client client = clientRepository.findClientByUserEmail(email).orElseThrow(EntityNotFoundException::new);

        Integer countFinishedTrips = ifNullSetDefaultValue(tripRepository.countTripsByStatusAndClient(Status.CLOSED, client));
        Double moneySpent = ifNullSetDefaultValue(tripRepository.moneySpentByClient(Status.CLOSED, client));
        Integer distancePassed = ifNullSetDefaultValue(tripRepository.distancePassed(Status.CLOSED, client));

        List<Status> pendingAndApprovedStatuses = Arrays.asList(Status.PENDING, Status.APPROVED);

        List<TripDto> openTrips = tripMapper.mapTripsToTripsDto(tripRepository
                .findTripsByStatusAndClient(pendingAndApprovedStatuses, client));

        List<TripDto> completedTrips = tripMapper.mapTripsToTripsDto(tripRepository
                .findTripsByStatusAndClientAndIsShowed(Status.CLOSED, client, true));

        return DashBoardDto.builder()
                .countFinishedTrips(countFinishedTrips)
                .moneySpent(moneySpent)
                .distancePassed(distancePassed)
                .completedTrips(completedTrips)
                .openTrip(openTrips)
                .build();
    }

    private Integer ifNullSetDefaultValue(Integer value) {
        return value == null ? 0 : value;
    }

    private Double ifNullSetDefaultValue(Double value) {
        return value == null ? 0.0 : value;
    }

    @Transactional
    public void hideTrip(Integer id, String userEmail) {
        tripRepository.findById(id)
                .map(trip -> {
                    tripRepository.hideTrip(id, userEmail);
                    return trip;
                })
                .orElseThrow(() -> new EntityNotFoundException("Trip with Id [" + id + "] doesn't exist"));

    }
}
