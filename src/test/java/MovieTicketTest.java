import models.Booking;
import models.BookingRequest;
import models.Seat;
import models.Show;
import org.testng.annotations.Test;
import service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MovieTicketTest {
    @Test
    void testMultiThread() throws InterruptedException, ExecutionException {
        LockService lockService = new LockServiceImpl();
        TheatreService theatreService = new TheatreServiceImpl(lockService);
        PaymentService paymentService = new PaymentService(theatreService, lockService);

        theatreService.initialize();

        List<Show> showList = theatreService.getAllShow();
        System.out.println("Available Show now: ");
        System.out.println(showList);

        List<Seat> seatList = new ArrayList<>();
        seatList.add(new Seat('A', 2));
        seatList.add(new Seat('A', 3));
        seatList.add(new Seat('A', 4));

        BookingRequest bookingRequest = new BookingRequest(showList.get(0).getId(), seatList, "chobis");

//        theatreService.bookShow(bookingRequest);

//        System.out.println("Show is booked for seats " + seatList);

        BookingRequest bookingRequest1 = new BookingRequest(showList.get(1).getId(), seatList, "chobis");
//        theatreService.bookShow(bookingRequest1);


        // This test will likely perform differently on different platforms.
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<Booking> result = executor.submit(() -> theatreService.bookShow(bookingRequest));
        Future<Booking> result1 = executor.submit(() -> theatreService.bookShow(bookingRequest1));


//        executor.shutdown();
//        executor.awaitTermination(10, TimeUnit.SECONDS);
//        System.out.println(Thread.currentThread().getId());
        System.out.println(result.get());
        System.out.println(result1.get());
        System.out.println(lockService.getSeatLockMap());
        paymentService.failPayment(result.get().getId());
        paymentService.failPayment(result.get().getId());
        paymentService.failPayment(result.get().getId());

        paymentService.successPayment(result1.get().getId());

        System.out.println(theatreService.getAllShow());
        System.out.println(theatreService.getAllBooking());

        System.out.println(lockService.getSeatLockMap());
    }
}
