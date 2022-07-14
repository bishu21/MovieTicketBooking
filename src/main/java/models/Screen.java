package models;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Screen {
    Set<Seat> seatList;
    Integer id;
}
