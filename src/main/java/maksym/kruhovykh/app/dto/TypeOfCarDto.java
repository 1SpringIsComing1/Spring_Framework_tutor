package maksym.kruhovykh.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class TypeOfCarDto {
    private Integer id;

    private String name;

    private Double transportationPricePerKm;
}
