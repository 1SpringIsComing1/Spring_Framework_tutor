package maksym.kruhovykh.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maksym.kruhovykh.app.Utils;
import maksym.kruhovykh.app.dto.CarDto;
import maksym.kruhovykh.app.dto.OrderDto;
import maksym.kruhovykh.app.dto.UserDto;
import maksym.kruhovykh.app.repository.CarRepository;
import maksym.kruhovykh.app.repository.entity.Car;
import maksym.kruhovykh.app.repository.entity.Order;
import maksym.kruhovykh.app.service.mapper.CarMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
@Slf4j
public class CarService {
    private static final String CAR_IS_EMPTY = "Car is Empty";
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public CarDto findById(Integer id) {
        return carRepository.findById(id)
                .map(carMapper::carToCarDto)
                .orElseThrow(() -> new EntityNotFoundException("Car with id [" + id +
                        "] not found"));

    }

    public CarDto create(CarDto carDto) {
        Utils.isNull(carDto, CAR_IS_EMPTY);

        Car car = carMapper.carDtoToCar(carDto);

        return carMapper.carToCarDto(carRepository.save(car));

    }

    public void delete(CarDto carDto) {
        Utils.isNull(carDto, CAR_IS_EMPTY);

        carRepository
                .findById(carDto.getId())
                .map(deleteCar(carDto))
                .map(carMapper::carDtoToCar)
                .orElseThrow(() -> new EntityNotFoundException("Car with Id [" + carDto.getId() + "] doesn't exist"));

    }

    public CarDto update(CarDto carDto) {
        Utils.isNull(carDto, CAR_IS_EMPTY);
        isNotExist(carDto);

        Car car = carMapper.carDtoToCar(carDto);

        return carMapper.carToCarDto(carRepository.save(car));
    }


    private void isNotExist(CarDto carDto) {
        if (!carRepository.findById(carDto.getId()).isPresent()) {
            log.error("Car with Id [" + carDto.getId() + "] doesn't exist");
            throw new EntityNotFoundException("Car with Id [" + carDto.getId() + "] doesn't exist");
        }
    }

    private Function<Car, CarDto> deleteCar(CarDto carDto) {
        return order -> {
            carRepository.delete(order);
            return carDto;
        };
    }

}
