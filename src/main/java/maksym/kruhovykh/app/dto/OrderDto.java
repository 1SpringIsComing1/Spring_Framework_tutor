package maksym.kruhovykh.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import maksym.kruhovykh.app.repository.entity.Address;
import maksym.kruhovykh.app.repository.entity.Client;
import maksym.kruhovykh.app.repository.entity.Driver;

import javax.persistence.*;
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
}
