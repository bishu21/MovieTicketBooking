package models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Theatre {
    List<Screen> screenList;
    Integer id;
    String name;

}
