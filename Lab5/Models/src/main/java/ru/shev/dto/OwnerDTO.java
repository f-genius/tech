package ru.shev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class OwnerDTO implements Serializable {
    private Integer id;
    private String name;
    private Date birthday;
    private List<CatDTO> cats;
}
