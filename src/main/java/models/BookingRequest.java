package models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookingRequest {
    Integer showId;
    List<Seat> seats;
    String userId;
}
