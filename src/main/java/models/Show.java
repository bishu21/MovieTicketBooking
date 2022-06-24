package models;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Show {
    Integer screenId;
    Movie movie;
    Integer id;
    LocalDateTime startTime;
    BigDecimal duration;
}
