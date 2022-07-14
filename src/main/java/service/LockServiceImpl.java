package service;

import models.Seat;
import models.SeatLock;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LockServiceImpl implements LockService {
    Map<Integer, Map<Seat, SeatLock>> seatLockMap = new HashMap<>();
    @Override
    public Boolean lockSeats(List<Seat> seats, Integer showId, String userId)  {
        synchronized(showId) {
            System.out.println("Trying to lock seats = "+ seats+ " for showId = " + showId );
            try {
                Thread.sleep(5*1000);
            } catch (Exception e) {

            }
            Map<Seat, SeatLock> seatSeatLockMap = seatLockMap.getOrDefault(showId, new HashMap<>());
            boolean flag = seats.stream().anyMatch(item -> seatSeatLockMap.keySet().contains(item) &&
                    isSeatLocked(showId, item));
            if (flag) {
                System.out.println("Seats are already locked = "+ seats);
                return false;
            } else {
                seats.stream().forEach(seat -> seatSeatLockMap.put(seat, createSeatLock(seat, showId,
                        userId)));
                seatLockMap.put(showId, seatSeatLockMap);
                System.out.println("Seats are locked for = "+ seats + " for showId = " + showId );
            }
            return true;
        }

    }

    private SeatLock createSeatLock(Seat seat, Integer showId, String userId) {

        SeatLock seatLock = new SeatLock();
        seatLock.setShowId(showId);
        seatLock.setTimeout(100);
        seatLock.setUserId(userId);
        seatLock.setLockTime(LocalDateTime.now());
        return seatLock;
    }

    @Override
    public Boolean unlockSeats(List<Seat> seats, Integer showId) {
        Map<Seat, SeatLock> hm = seatLockMap.get(showId);
        for (Seat s: seats)
            hm.remove(s);
        return true;
    }

    @Override
    public Boolean checkSeatLock(final Integer showId,  List<Seat> seat) {
        return seat.stream().allMatch(item -> isSeatLocked(showId, item));

    }

    @Override
    public Map<Integer, Map<Seat, SeatLock>> getSeatLockMap() {
        return seatLockMap;
    }

    private boolean isSeatLocked(final Integer show, final Seat seat) {
        return seatLockMap.containsKey(show) && seatLockMap.get(show).containsKey(seat) &&
                !seatLockMap.get(show).get(seat).checkExpiry();
    }
}
