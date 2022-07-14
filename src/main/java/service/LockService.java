package service;

import models.Seat;
import models.SeatLock;

import java.util.List;
import java.util.Map;

public interface LockService {

    Boolean lockSeats(List<Seat> seats, Integer showId, String userId);

    Boolean unlockSeats(List<Seat> seats, Integer showId);

    Boolean checkSeatLock(final Integer showId,  List<Seat> seat);

    Map<Integer, Map<Seat, SeatLock>> getSeatLockMap();
}
