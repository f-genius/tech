package ru.shev.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="owners_table")
public class Owner {
    @Id
    @Column(name="owner_id")
    private Integer id;

    @Column(name="owner_name")
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name="owner_date")
    private Date birthday;

    @OneToMany(mappedBy = "owner")
    private final List<Cat> cats = new ArrayList<>();

    public void addNewCat(Cat newCat) {
        if (!cats.contains(newCat))
            cats.add(newCat);
    }
}
