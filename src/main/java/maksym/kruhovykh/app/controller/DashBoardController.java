package maksym.kruhovykh.app.controller;

import lombok.RequiredArgsConstructor;
import maksym.kruhovykh.app.dto.DashBoardDto;
import maksym.kruhovykh.app.dto.LocationDto;
import maksym.kruhovykh.app.service.DashBoardService;
import maksym.kruhovykh.app.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping({"/", "/dashboard"})
public class DashBoardController {

    private final DashBoardService dashBoardService;
    private final LocationService locationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getDashBoard(Model model, Principal principal) {

        DashBoardDto dashBoard = dashBoardService.download("Test1@gmail.com");
        List<LocationDto> locations = locationService.findAll();
        model.addAttribute("dashBoard", dashBoard);
        model.addAttribute("locations", locations);
        return "dashboard";
    }

    @GetMapping("/hide/{id}")
    public String hideTrip(Model model, Principal principal, @PathVariable("id") Integer tripId) {
        String email = "Test1@gmail.com";
//        String email = principal.getName();

        dashBoardService.hideTrip(tripId, email);

        return "redirect:/dashboard";
    }

    // TODO: 5/28/2021 Добавить возможность создания Тура ( с учётом мест в машине , лучше забитые дизейблить )
}
