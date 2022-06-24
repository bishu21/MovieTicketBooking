package service;

import models.BookingRequest;
import models.Show;

import java.util.List;

public interface TheatreService {

    List<Show> getAllShow();

    void bookShow(BookingRequest bookingRequest);

    void initialize();

}
