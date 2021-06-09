package maksym.kruhovykh.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maksym.kruhovykh.app.dto.TypeOfCarDto;
import maksym.kruhovykh.app.repository.TypeOfCarRepository;
import maksym.kruhovykh.app.repository.entity.TypeOfCar;
import maksym.kruhovykh.app.service.mapper.TypeOfCarMapper;
import maksym.kruhovykh.app.utils.Utils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
@Slf4j
public class TypeOfCarService {
    private static final String TYPE_OF_CAR_IS_EMPTY = "Type of car is Empty";

    private final TypeOfCarRepository typeOfCarRepository;
    private final TypeOfCarMapper typeOfCarMapper;

    public TypeOfCarDto findById(Integer id) {
        return typeOfCarRepository
                .findById(id)
                .map(typeOfCarMapper::typeOfCarToTypeOfCarDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public TypeOfCarDto create(TypeOfCarDto typeOfCarDto) {
        Utils.isNull(typeOfCarDto, TYPE_OF_CAR_IS_EMPTY);

        if (typeOfCarRepository.findByName(typeOfCarDto.getName()).isPresent()) {
            log.error("Type of car already exist");
            throw new EntityExistsException("Type of car already exist");
        }

        TypeOfCar typeOfCar = typeOfCarMapper.typeOfCarDtoToTypeOfCar(typeOfCarDto);

        return typeOfCarMapper.typeOfCarToTypeOfCarDto(typeOfCarRepository.save(typeOfCar));
    }

    public TypeOfCarDto update(TypeOfCarDto typeOfCarDto) {
        Utils.isNull(typeOfCarDto, TYPE_OF_CAR_IS_EMPTY);

        return typeOfCarRepository
                .findById(typeOfCarDto.getId())
                .map(typeOfCar -> {
                    typeOfCar = typeOfCarMapper.typeOfCarDtoToTypeOfCar(typeOfCarDto);
                    typeOfCarRepository.save(typeOfCar);
                    return typeOfCarDto;
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    public void delete(TypeOfCarDto typeOfCarDto) {
        Utils.isNull(typeOfCarDto, TYPE_OF_CAR_IS_EMPTY);

        typeOfCarRepository.findById(typeOfCarDto.getId())
                .map(typeOfCar -> {
                    typeOfCarRepository.delete(typeOfCar);
                    log.info("Type of Car  " + typeOfCar + " deleted");
                    return typeOfCarDto;
                })
                .orElseThrow(EntityNotFoundException::new);
    }

}
