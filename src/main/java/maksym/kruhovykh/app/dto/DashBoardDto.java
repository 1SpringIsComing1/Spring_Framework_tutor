package maksym.kruhovykh.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class DashBoardDto {
    private Integer countFinishedTrips;
    private Double moneySpent;
    private Integer distancePassed;
    private List<TripDto> openTrip;
    private List<TripDto> completedTrips;
}
