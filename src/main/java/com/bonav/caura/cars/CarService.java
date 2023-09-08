package com.bonav.caura.cars;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CarService {
    private final CarRepository carRepository;

    public Optional<Car>findById(long id){
        return carRepository.findById(id);
    }
    public boolean deleteCar(Long carId) throws Exception {
        try {
            boolean exists = carRepository.existsById(carId);
            if (!exists) {
                throw new IllegalStateException(
                        "car with id" + carId + "does not exists");
            }
            carRepository.deleteById(carId);
            log.info("Successfully deleted car " + carId);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getClass().getSimpleName(), e);
            return false;
        }
    }

    public Car saveCar(Car car) throws Exception {
        try {
            car = carRepository.save(car);
            return car;
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new Exception();
        }
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();

    }

    public boolean editCar(Car car) {
        try{
            carRepository.save(car);
            return true;
        }catch(Exception e){
            log.error(e.getMessage(),e.getClass().getSimpleName(),e);
            return false;
        }
    }
}

