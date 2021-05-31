package maksym.kruhovykh.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maksym.kruhovykh.app.Utils;
import maksym.kruhovykh.app.dto.ClientDto;
import maksym.kruhovykh.app.repository.ClientRepository;
import maksym.kruhovykh.app.repository.entity.Client;
import maksym.kruhovykh.app.service.mapper.ClientMapper;
import maksym.kruhovykh.app.service.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ClientService {

    public static final String CLIENT_IS_EMPTY = "Client is Empty";
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final UserMapper userMapper;

    public ClientDto findById(Integer id) {
        return clientRepository
                .findById(id)
                .map(clientMapper::clientToClientDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Page<ClientDto> findAll(Pageable pageable) {
        Page<Client> clientPage = clientRepository.findAll(pageable);
        return clientPage.map(clientMapper::clientToClientDto);
    }

    public ClientDto update(ClientDto clientDto) {
        Utils.isNull(clientDto, CLIENT_IS_EMPTY);

        return clientRepository
                .findById(clientDto.getId())
                .map(updateUser(clientDto))
                .orElseThrow(EntityNotFoundException::new);
    }

    public ClientDto create(ClientDto clientDto) {
        Utils.isNull(clientDto, CLIENT_IS_EMPTY);

        Client client = clientMapper.clientDtoToClient(clientDto);

        if (clientRepository.findByUser(client.getUser()).isPresent()) {
            log.error("Client  Already exist");
            throw new EntityExistsException("Client  Already exist");
        }

        return clientMapper.clientToClientDto(clientRepository.save(client));

    }

    public void delete(ClientDto clientDto) {
        Utils.isNull(clientDto, CLIENT_IS_EMPTY);
        clientRepository.findById(clientDto.getId())
                .map(client -> {
                    clientRepository.delete(client);
                    log.info("Client " + client + " deleted");
                    return clientDto;
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    private Function<Client, ClientDto> updateUser(ClientDto clientDto) {
        return client -> {
            client.setId(clientDto.getId());
            client.setUser(userMapper.userDtoToUser(clientDto.getUserDto()));
            clientRepository.save(client);
            log.info("Client" + client + " updated");
            return clientDto;

        };
    }
}
