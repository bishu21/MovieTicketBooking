package models;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class Show {
    Integer screenId;
    Set<Seat> avaiableSeats;
    Set<Seat> bookedSeat = new HashSet<>();
    Movie movie;
    Integer id;
    LocalDateTime startTime;
    BigDecimal duration;
}
