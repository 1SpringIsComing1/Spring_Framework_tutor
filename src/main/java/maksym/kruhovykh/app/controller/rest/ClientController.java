package maksym.kruhovykh.app.controller.rest;

import lombok.AllArgsConstructor;
import maksym.kruhovykh.app.dto.ClientDto;
import maksym.kruhovykh.app.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/api/client")
public class ClientController {

    private static final int DEFAULT_SIZE_PAGE = 10;
    private final ClientService clientService;


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDto findById(@PathVariable Integer id) {
        return clientService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ClientDto> findAll(@PageableDefault(size = DEFAULT_SIZE_PAGE) Pageable pageable) {
        return clientService.findAll(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto createClient(ClientDto clientDto) {
        return clientService.create(clientDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ClientDto updateDriver(ClientDto clientDto) {
        return clientService.update(clientDto);
    }


    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(ClientDto clientDto) {
        clientService.delete(clientDto);
    }


}
