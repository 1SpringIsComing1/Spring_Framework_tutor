package maksym.kruhovykh.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maksym.kruhovykh.app.Utils;
import maksym.kruhovykh.app.dto.AddressDto;
import maksym.kruhovykh.app.repository.AddressRepository;
import maksym.kruhovykh.app.repository.entity.Address;
import maksym.kruhovykh.app.service.mapper.AddressMapper;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
@Slf4j
public class AddressService {

    private static final String ADDRESS_IS_EMPTY = "Address is empty";

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;


    public AddressDto findById(Integer id) {
        return addressRepository
                .findById(id)
                .map(addressMapper::addressToAddressDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public AddressDto create(AddressDto addressDto) {
        Utils.isNull(addressDto, ADDRESS_IS_EMPTY);
        Address address = addressMapper.addressDtoToAddress(addressDto);

        if (addressRepository.exists(Example.of(address))) {
            log.error("Address  [" + address + "] already exist");
            throw new EntityExistsException("Address [" + address + "] already exist");
        }

        return addressMapper.addressToAddressDto(addressRepository.save(address));
    }

    public AddressDto update(AddressDto addressDto) {
        Utils.isNull(addressDto, ADDRESS_IS_EMPTY);

        return addressRepository.findById(addressDto.getId())
                .map(driver -> {
                    driver = addressMapper.addressDtoToAddress(addressDto);
                    addressRepository.save(driver);
                    return addressDto;
                }).orElseThrow(EntityNotFoundException::new);
    }

    public void delete(AddressDto addressDto) {
        Utils.isNull(addressDto, ADDRESS_IS_EMPTY);
        addressRepository.findById(addressDto.getId())
                .map(address -> {
                    addressRepository.delete(address);
                    log.info("Address  " + address + " deleted");
                    return addressDto;
                })
                .orElseThrow(EntityNotFoundException::new);
    }
}
