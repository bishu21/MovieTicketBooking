package service;

import models.Booking;
import models.BookingRequest;
import models.Show;

import java.util.Collection;
import java.util.List;

public interface TheatreService {

    List<Show> getAllShow();

    List<Booking> getAllBooking();

    Booking bookShow(BookingRequest bookingRequest);

    void initialize();

}
