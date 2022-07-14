package models;

import lombok.Data;

import java.util.List;

@Data
public class Booking {
    Integer id;
    Status status;
    Integer showId;
    String userId;
    List<Seat> seats;
    public enum Status {
        PENDING, COMPLETED, FAILED;
    }
}
