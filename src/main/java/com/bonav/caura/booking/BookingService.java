package com.bonav.caura.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@AllArgsConstructor
@Slf4j
public class BookingService {
    private final BookingRepository bookingRepository;

    public Booking save(Booking booking) throws Exception {
        try {
            return bookingRepository.save(booking);
        } catch (Exception e) {
            log.error("Error saving booking: " + e.getMessage());
            throw new Exception("Error saving booking", e);
        }
    }
    public List<Booking> getbookings() {
        return bookingRepository.findAll();
    }

    Optional<Booking> getBooking(long id){
        return bookingRepository.findById(id);
    }


    public boolean deleteBooking(Long id) throws Exception {
        try {
            boolean exists = bookingRepository.existsById(id);
            if (!exists) {
                throw new IllegalStateException(
                        "booking with id" + getBooking(id) + "does not exists");
            }
            bookingRepository.deleteById(id);
            log.info("Successfully deleted booking " + bookingRepository);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getClass().getSimpleName(), e);
            return false;
        }
    }
}
