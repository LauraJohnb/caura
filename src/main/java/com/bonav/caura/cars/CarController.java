package com.bonav.caura.cars;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@AllArgsConstructor
//@RequestMapping("/car")

public class CarController {
    private final CarService carService;
    private final Environment env;
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
    public String editCar(Long id, Model model) {
        try {
            Optional<Car> car = carService.findById(id);
            car.ifPresent(value -> model.addAttribute("car", value));
        } catch (Exception e) {
            log.error(e.getMessage(), e.getClass(), e.getLocalizedMessage(), e);
        }
        return "car_rental";
    }
    @RequestMapping(value = "/cars/register",method=RequestMethod.POST,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String saveCar(@ModelAttribute Car car, @RequestPart List<MultipartFile> files) throws Exception {
        files.forEach((file)->{
            try {
                File _file = new File(env.getProperty("files_location") + file.getOriginalFilename());
                log.info(file.getOriginalFilename());
                Files.copy(file.getInputStream(), _file.toPath());
            }catch(IOException e){
                log.error(e.getMessage(),e.getClass().getSimpleName(),e);
            }
        });
        carService.save(car);
        return "redirect:/cars";
    }
}