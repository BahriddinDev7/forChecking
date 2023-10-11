package uz.pdp.springexam.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springexam.dto.car.CarCreateDto;
import uz.pdp.springexam.dto.car.CarResponseDto;
import uz.pdp.springexam.dto.customer.CustomerCreateDto;
import uz.pdp.springexam.dto.customer.CustomerResponseDto;
import uz.pdp.springexam.entity.Car;
import uz.pdp.springexam.entity.Customer;
import uz.pdp.springexam.enums.CarBrand;
import uz.pdp.springexam.repository.CarRepository;
import uz.pdp.springexam.service.CustomerService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/car")
@Slf4j
public class CarController {

    private final CarRepository carRepository;

    @GetMapping("/{id}")
    public HttpEntity<CarResponseDto> getCustomer(@PathVariable("id") UUID id) {
        Car car = carRepository.findById(id).get();
        return ResponseEntity.ok(map(car));
    }

    @GetMapping("/list")
    public HttpEntity<List<CarResponseDto>> getAll() {
        List<Car> all = carRepository.findAll();
        return ResponseEntity.ok(all.stream().map(this::map).toList());
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable("id") UUID id) {
        carRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    public HttpEntity<CarResponseDto> create(@RequestBody CarCreateDto createDto) {
        Car car = Car.builder()
                .brand(CarBrand.valueOf(createDto.getBrand()))
                .number(createDto.getNumber())
                .manufacturedDate(createDto.getManufactured())
                .build();
        carRepository.save(car);

        return ResponseEntity.ok(map(car));
    }

    @PutMapping("/{id}")
    @Transactional
    public HttpEntity<CarResponseDto> update(@PathVariable("id") UUID id,
                                                  @RequestBody CarCreateDto createDto) {
        Car car= carRepository.findById(id).get();

        car.setNumber(createDto.getNumber());
        car.setBrand(CarBrand.valueOf(createDto.getBrand()));

        carRepository.save(car);

        CarResponseDto res = CarResponseDto.builder()
                .id(car.getId())
                .number(car.getNumber())
                .brand(car.getBrand().toString())
                .manufactured(car.getManufacturedDate())
                .build();
        return ResponseEntity.ok(res);

    }

    private CarResponseDto map(Car car) {

        return CarResponseDto.builder()
                .id(car.getId())
                .brand(car.getBrand().toString())
                .number(car.getNumber())
                .manufactured(car.getManufacturedDate())
                .build();
    }

}
