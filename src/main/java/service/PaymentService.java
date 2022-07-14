package service;

import models.Booking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PaymentService {
    TheatreService theatreService;
    LockService lockService;
    Map<Integer, Integer> bookingVsFailureMap = new HashMap<>();
    private static final Integer MAX_RETRY = 3;

    public PaymentService(TheatreService theatreService, LockService lockService) {
        this.theatreService = theatreService;
        this.lockService = lockService;
    }

    public Boolean failPayment(Integer bookingId) {
//        System.out.println("Thread used in failure payment is  = "+ Thread.currentThread().getId());
        List<Booking> bookingList = theatreService.getAllBooking();
        Optional<Booking> booking = bookingList.stream().filter(item -> item.getId() == bookingId).findFirst();

        if(booking.isPresent() && lockService.checkSeatLock(booking.get().getShowId(), booking.get().getSeats())) {
            Integer count = bookingVsFailureMap.getOrDefault(bookingId, 0);
            count++;
            bookingVsFailureMap.put(bookingId, count);
            System.out.println("Payment failure happens for bookingId = "+bookingId + " count = "+ count);
            if (count >= MAX_RETRY) {
                lockService.unlockSeats(booking.get().getSeats(), booking.get().getShowId());
                booking.get().setStatus(Booking.Status.FAILED);
            }
            return true;
        } else {
            System.out.println("Invalid request ... ");
            return false;
        }

    }
    public Boolean successPayment(Integer bookingId) {
//        System.out.println("Thread used in success payment is  = "+ Thread.currentThread().getId());
        List<Booking> bookingList = theatreService.getAllBooking();
        Optional<Booking> booking = bookingList.stream().filter(item -> item.getId() == bookingId).findFirst();

        if(booking.isPresent() && lockService.checkSeatLock(booking.get().getShowId(), booking.get().getSeats())) {
            booking.get().setStatus(Booking.Status.COMPLETED);
            return true;
        } else {
            return false;
        }

    }
}
