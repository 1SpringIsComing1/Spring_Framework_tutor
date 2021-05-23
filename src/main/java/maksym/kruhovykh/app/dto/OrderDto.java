package maksym.kruhovykh.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import maksym.kruhovykh.app.utils.Status;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Integer id;

    private String title;

    private String description;

    private Integer distance;

    private AddressDto departures;

    private AddressDto arrivals;

    private Double price;

    private LocalDateTime dateCreation;

    private DriverDto driverDto;

    private ClientDto clientDto;

    private Status status;
}
