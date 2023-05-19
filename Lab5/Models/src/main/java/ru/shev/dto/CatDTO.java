package ru.shev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class CatDTO implements Serializable {
    private Integer id;
    private String name;
    private Date birthday;
    private String color;
    private Integer ownerId;
    private List<CatDTO> friends;
}
