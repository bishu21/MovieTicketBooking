import models.BookingRequest;
import models.Seat;
import models.Show;
import service.TheatreService;
import service.TheatreServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class MovieTicketApp {
    public static void main(String[] args) {


        TheatreService theatreService = new TheatreServiceImpl();
        theatreService.initialize();

        List<Show> showList = theatreService.getAllShow();
        System.out.println("Available Show now: ");
        System.out.println(showList);

        List<Seat> seatList = new ArrayList<>();
        seatList.add(new Seat('A', 2));
        seatList.add(new Seat('A', 3));
        seatList.add(new Seat('A', 4));

        BookingRequest bookingRequest = new BookingRequest(showList.get(0).getId(), seatList, "chobis");
        theatreService.bookShow(bookingRequest);

        System.out.println("Show is booked for seats " + seatList);

        BookingRequest bookingRequest1 = new BookingRequest(showList.get(1).getId(), seatList, "chobis");
        theatreService.bookShow(bookingRequest1);

    }


}