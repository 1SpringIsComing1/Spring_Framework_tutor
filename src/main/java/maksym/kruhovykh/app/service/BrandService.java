package maksym.kruhovykh.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maksym.kruhovykh.app.utils.Utils;
import maksym.kruhovykh.app.dto.BrandDto;
import maksym.kruhovykh.app.repository.BrandRepository;
import maksym.kruhovykh.app.repository.entity.Brand;
import maksym.kruhovykh.app.service.mapper.BrandMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
@Slf4j
public class BrandService {
    private static final String BRAND_IS_EMPTY = "Brand is Empty";

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public BrandDto findById(Integer id) {
        return brandRepository
                .findById(id)
                .map(brandMapper::brandToBrandDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    public BrandDto create(BrandDto brandDto) {
        Utils.isNull(brandDto, BRAND_IS_EMPTY);

        Brand brand = brandMapper.brandDtoToBrand(brandDto);
        if (brandRepository.findByNameContainingIgnoreCase(brand.getName()).isPresent()) {
            log.error("Brand [" + brand.getName() + "] already exist");
            throw new EntityExistsException("Brand [" + brand.getName() + "] already exist");
        }

        return brandMapper.brandToBrandDto(brandRepository.save(brand));
    }

    public BrandDto update(BrandDto brandDto) {
        Utils.isNull(brandDto, BRAND_IS_EMPTY);
        return brandRepository
                .findById(brandDto.getId())
                .map(updateBrand(brandDto))
                .orElseThrow(EntityNotFoundException::new);

    }

    public void delete(BrandDto brandDto) {
        Utils.isNull(brandDto, BRAND_IS_EMPTY);

        brandRepository
                .findById(brandDto.getId())
                .map(deleteBrand())
                .orElseThrow(EntityNotFoundException::new);
    }

    private Function<Brand, Brand> deleteBrand() {
        return brand -> {
            brandRepository.delete(brand);
            log.info("Brand " + brand + " deleted");
            return brand;
        };
    }

    private Function<Brand, BrandDto> updateBrand(BrandDto brandDto) {
        return brand -> {
            brand.setId(brandDto.getId());
            brand.setName(brandDto.getName());
            brandRepository.save(brand);
            log.info("Brand [" + brand + "]  saved");
            return brandDto;
        };
    }

}
