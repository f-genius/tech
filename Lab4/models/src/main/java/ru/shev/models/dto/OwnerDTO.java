package ru.shev.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDTO {
    private Integer id;
    private String name;
    private Date birthday;
    private List<CatDTO> cats;
}
