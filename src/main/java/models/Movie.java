package models;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Movie {
    String movieName;
    List<String> casts;
    LocalDateTime releaseDate;
}
