package service;

import models.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TheatreServiceImpl implements TheatreService {

    LockService lockService;
    int count=1;

    public TheatreServiceImpl(LockService lockService) {
        this.lockService = lockService;
    }

    Theatre theatre;
    Map<Integer, Show> showMap = new HashMap<>();
    Map<Integer, Booking> bookingMap = new HashMap<>();

    @Override
    public List<Show> getAllShow() {
        return showMap.entrySet().stream().map(item->item.getValue()).collect(Collectors.toList());
    }

    @Override
    public List<Booking> getAllBooking() {
        return bookingMap.values().stream().toList();
    }

    @Override
    public Booking bookShow(BookingRequest bookingRequest) {
//        System.out.println("Thread used in Book show is  = "+ Thread.currentThread().getId());

        System.out.println("Trying to book a showId = " + bookingRequest.getShowId() +
                " seats = "+ bookingRequest.getSeats());

        Show show = showMap.get(bookingRequest.getShowId());

        boolean conflict = bookingRequest.getSeats().stream().anyMatch(item -> show.getBookedSeat().contains(item));

        if (conflict) {
            throw new RuntimeException("Occupied seats can not be booked.");
        } else {
            // lock the seats for some time;

            boolean lockFlag = lockService.lockSeats(bookingRequest.getSeats(), bookingRequest.getShowId(),
                    bookingRequest.getUserId());

            if (!lockFlag) {
                System.out.println("Unable to lock for seats with booking request = "+ bookingRequest);
                throw new RuntimeException("Unable to lock for seats with booking request = "+ bookingRequest);
            }

            show.getAvaiableSeats().removeAll(bookingRequest.getSeats());
            show.getBookedSeat().addAll(bookingRequest.getSeats());

            Booking booking = new Booking();
            booking.setShowId(bookingRequest.getShowId());
            booking.setStatus(Booking.Status.PENDING);
            booking.setId(count++);
            booking.setUserId(bookingRequest.getUserId());
            booking.setSeats(bookingRequest.getSeats());

            bookingMap.put(booking.getId(), booking);

            return booking;
        }

//        System.out.println("Remaining avaiable seats are "+ show.getAvaiableSeats()+" for screenId= "+show.getId());
//        System.out.println("Booked seats are "+show.getBookedSeat() +" for screenId= "+show.getId());

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
        show1.setAvaiableSeats(screen1.getSeatList());


        Show show2 = new Show();
        show2.setId(2);
        show2.setScreenId(2);
        show2.setStartTime(LocalDateTime.now().plusMinutes(60));
        show2.setDuration(BigDecimal.valueOf(2));
        show2.setMovie(movie);
        show2.setAvaiableSeats(screen2.getSeatList());

        showMap.putIfAbsent(show1.getId(), show1);
        showMap.put(show2.getId(), show2);
    }


    private Screen initiliazeScreen(int i) {
        Screen screen = new Screen();
        screen.setId(i);
        screen.setSeatList(getSeats());
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
