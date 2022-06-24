package service;

import models.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TheatreServiceImpl implements TheatreService {

    Theatre theatre;
    Map<Integer, Show> showMap = new HashMap<>();




    @Override
    public List<Show> getAllShow() {
        return showMap.entrySet().stream().map(item->item.getValue()).collect(Collectors.toList());
    }

    @Override
    public void bookShow(BookingRequest bookingRequest) {

        System.out.println("Trying to book a showId = " + bookingRequest.getShowId() +
                " seats = "+ bookingRequest.getSeats());

        Show show = showMap.get(bookingRequest.getShowId());

        Screen screen = theatre.getScreenList().get(show.getScreenId()-1);

        boolean conflict = bookingRequest.getSeats().stream().anyMatch(item -> screen.getBookedSeat().contains(item));

        if (conflict) {
            throw new RuntimeException("Occupied seats can not be booked.");
        } else {
            screen.getSeatList().removeAll(bookingRequest.getSeats());
            screen.getBookedSeat().addAll(bookingRequest.getSeats());
        }

        System.out.println("Remaining avaiable seats are "+ screen.getSeatList()+" for screenId= "+screen.getId());
        System.out.println("Booked seats are "+screen.getBookedSeat() +" for screenId= "+screen.getId());

    }

    @Override
    public void initialize() {
        Screen screen1 = initiliazeScreen(1);
        Screen screen2 = initiliazeScreen(2);
        theatre = new Theatre(List.of(screen1, screen2), 1, "Angel Theater");

        Movie movie = new Movie();
        movie.setMovieName("Khiladi");

        Show show1 = new Show();
        show1.setId(1);
        show1.setScreenId(1);
        show1.setStartTime(LocalDateTime.now().plusMinutes(60));
        show1.setDuration(BigDecimal.valueOf(2));
        show1.setMovie(movie);

        Show show2 = new Show();
        show2.setId(2);
        show2.setScreenId(2);
        show2.setStartTime(LocalDateTime.now().plusMinutes(60));
        show2.setDuration(BigDecimal.valueOf(2));
        show2.setMovie(movie);

        showMap.putIfAbsent(show1.getId(), show1);
        showMap.put(show2.getId(), show2);
    }


    private Screen initiliazeScreen(int i) {
        Screen screen = new Screen();
        screen.setId(i);
        screen.setSeatList(getSeats());
        screen.setBookedSeat(new HashSet<>());
        return screen;
    }

    private Set<Seat> getSeats() {
        Set<Seat> seatList = new HashSet<>();
        char count = 'A';
        while(count<'C') {
            for(int i=1;i<=5;i++){
                seatList.add(new Seat(count, i));
            }
            count++;
        }

        return seatList;
    }
}
