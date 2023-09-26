package com.bonav.caura.booking;

import com.bonav.caura.cars.CarService;
import com.bonav.caura.users.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@AllArgsConstructor
@Slf4j
public class BookingController {
    private UserService userService;
    private CarService carService;
    private BookingService bookingService;

    @GetMapping("book")
    public String book(Model model) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("cars", carService.getAllCars());
        return "booking";
    }

    @PostMapping("/booking")
    public String processBooking(@ModelAttribute Booking booking, Model model, HttpServletRequest request) {
        try {
            booking = bookingService.save(booking);
            if (booking != null ) {
                request.getSession().setAttribute("id", booking.id);
                return "redirect:/bookings";
            } else {
                model.addAttribute("message", "Error booking car");
                return "booking";
            }
        } catch (Exception e) {
            model.addAttribute("message", "An error occurred during booking: ");
            return "error";
        }
    }
@GetMapping("/bookings")
    public String bookings ( Model model){
    model.addAttribute("bookings", bookingService.getbookings());
    return "/booking_list";
    }
    @GetMapping("/bookings/edit")
    public String editBooking ( Model model,long id) {
        try {
            Optional<Booking> booking = bookingService.getBooking(id);
            booking.ifPresent(value -> model.addAttribute("booking", value));
            model.addAttribute("users", userService.getUsers());
            model.addAttribute("cars", carService.getAllCars());
            return "booking";
        } catch (Exception e) {
            log.error(e.getMessage(), e.getClass().getSimpleName(), e);
            return "booking";
        }
    }
    @GetMapping("bookings/delete")
    public String deleteBooking(
            Long id) {
        try {
            boolean result = bookingService.deleteBooking(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e.getClass().getSimpleName(), e);
        }
        return "redirect:/booking";
    }

}


