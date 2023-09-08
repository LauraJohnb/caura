package com.bonav.caura.cars;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@Slf4j
@AllArgsConstructor
//@RequestMapping("/car")
public class CarController {
    private final CarService carService;

    @PostMapping(value = "/cars/register")
    public String processRegistration(Car carregistration, Model model, HttpServletRequest request) {
        try {
            carregistration = carService.saveCar(carregistration);
            if (carregistration == null) {
                model.addAttribute("message", "Error registering car");
                return "car_rental";
            }
            request.getSession().setAttribute("id", carregistration.id);
            return "redirect:/cars";
        } catch (Exception e) {
            model.addAttribute("message", "Error registering user");
            return "car_rental";
        }
    }

    @GetMapping("/cars/register")
    public String register(HttpServletRequest request, Model model) {
        //long id= (Long)request.getSession() .getAttribute("id");
        //model.addAttribute("user",userService.getUserById(id));
        return "car_rental";
    }

    @GetMapping("/cars")
    public String cars(Model model) {
        model.addAttribute("cars", carService.getAllCars());
        return "cars_available";
    }

    @GetMapping("cars/delete")
    public String deleteCar(
            Long id) {
        try {
            boolean result = carService.deleteCar(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e.getClass().getSimpleName(), e);
        }
        return "redirect:/cars";
    }

    @GetMapping("cars/edit")
    public String editCar(Long id,Model model) {
        try {
            Optional<Car>car=carService.findById(id);
            car.ifPresent(value -> model.addAttribute("car", value));
        } catch (Exception e) {
            log.error(e.getMessage(), e.getClass(), e.getLocalizedMessage(), e);
        }
        return "car_rental";
    }


}