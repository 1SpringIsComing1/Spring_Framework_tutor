package maksym.kruhovykh.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import maksym.kruhovykh.app.service.enumeration.Status;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class TripDto {

    private Integer id;

    private Integer distance;

    private LocationDto departure;

    private LocationDto arrival;

    private Double price;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private DriverDto driverDto;

    private ClientDto clientDto;

    private Status status;

    private Boolean isShowed;

}
