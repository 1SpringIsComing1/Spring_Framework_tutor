package maksym.kruhovykh.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maksym.kruhovykh.app.Utils;
import maksym.kruhovykh.app.dto.DriverDto;
import maksym.kruhovykh.app.repository.DriverRepository;
import maksym.kruhovykh.app.repository.entity.Client;
import maksym.kruhovykh.app.repository.entity.Driver;
import maksym.kruhovykh.app.service.mapper.DriverMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
@Slf4j
public class DriverService {
    private static final String DRIVER_IS_EMPTY = "Driver is Empty";
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    public DriverDto findById(Integer id) {
        return driverRepository
                .findById(id)
                .map(driverMapper::driverToDriverDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public DriverDto update(DriverDto driverDto) {
        Utils.isNull(driverDto, DRIVER_IS_EMPTY);

        return driverRepository.findById(driverDto.getId())
                .map(driver -> {
                    driver = driverMapper.driverDtoToDriver(driverDto);
                    driverRepository.save(driver);
                    return driverDto;
                }).orElseThrow(EntityNotFoundException::new);
    }

    public DriverDto create(DriverDto driverDto) {
        Utils.isNull(driverDto, DRIVER_IS_EMPTY);
        Driver driver = driverMapper.driverDtoToDriver(driverDto);

        if (driverRepository.findByUser(driver.getUser()).isPresent()) {
            log.error("Driver  Already exist");
            throw new EntityExistsException("Driver  Already exist");
        }
        return driverMapper.driverToDriverDto(driverRepository.save(driver));
    }

    public void delete(DriverDto driverDto) {
        Utils.isNull(driverDto, DRIVER_IS_EMPTY);
        driverRepository.findById(driverDto.getId())
                .map(driver -> {
                    driverRepository.delete(driver);
                    log.info("Driver " + driver + " deleted");
                    return driverDto;
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    public Page<DriverDto> findAll(Pageable pageable) {
        Page<Driver> clientPage = driverRepository.findAll(pageable);
        return clientPage.map(driverMapper::driverToDriverDto);
    }
}
