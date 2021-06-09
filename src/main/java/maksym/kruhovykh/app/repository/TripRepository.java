package maksym.kruhovykh.app.repository;

import maksym.kruhovykh.app.repository.entity.Client;
import maksym.kruhovykh.app.repository.entity.Trip;
import maksym.kruhovykh.app.service.enumeration.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
    boolean existsByStatusAndClient(Status status, Client client);

    Integer countTripsByStatusAndClient(Status status, Client client);

    @Query("SELECT SUM(trip.price) FROM Trip trip where trip.status =:status and trip.client =:client")
    Double moneySpentByClient(@Param("status") Status status, @Param("client") Client client);

    @Query("SELECT SUM(trip.distance) FROM Trip trip where trip.status =:status and trip.client =:client")
    Integer distancePassed(@Param("status") Status status, @Param("client") Client client);

    @Query("SELECT trip FROM Trip trip where trip.client =:client and  trip.status in :statuses and trip.isShowed = true ")
    List<Trip> findTripsByStatusAndClient(@Param("statuses") List<Status> status, @Param("client") Client client);

    List<Trip> findTripsByStatusAndClientAndIsShowed(Status status, Client client, Boolean isShowed);

    @Modifying
    @Query("update Trip trip set trip.isShowed = false where trip.id = ?1")
    void hideTrip(Integer tripId, String email);

    @Query(nativeQuery = true, value = "SELECT * FROM trip t LEFT JOIN driver d on d.id = t.driver_id WHERE departure_time >= ?2 and arrival_time <= ?1 and t.id = ?3 ")
    List<Trip> tripBetween(LocalDateTime arrivalTime, LocalDateTime departureTime, Integer driverId);

    @Query("select trip From Trip trip where trip.arrival.id = ?1 and trip.departure.id=?2")
    List<Trip> findAllByCitiesIds(Integer departureId, Integer arrivalId);
}
