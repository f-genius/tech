package ru.shev.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatDTO {
    private Integer id;
    private String name;
    private Date birthday;
    private String color;
    private Integer ownerId;
    private List<CatDTO> friends;
}
