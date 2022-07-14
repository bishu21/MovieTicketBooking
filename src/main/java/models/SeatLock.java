package models;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
public class SeatLock {
    Integer bookingId;
    Integer showId;
    String userId;
    Integer timeout;
    private LocalDateTime lockTime;

    public boolean checkExpiry() {
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), lockTime) > timeout;
    }
}
