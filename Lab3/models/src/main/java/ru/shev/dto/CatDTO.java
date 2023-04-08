package ru.shev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatDTO {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Integer id;
    private String name;
    private String color;
    private List<CatDTO> friends;
}
