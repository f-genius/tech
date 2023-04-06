package ru.shev.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(of = {"id"})
@Data
@NoArgsConstructor
@Entity
@Table(name= "cats_table", schema = "public")
public class Cat {
    @Id
    @Column(name="cat_id")
    private Integer id;

    @Column(name="cat_name")
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name="cat_date")
    private Date date;

    @Column(name="cat_color")
    private String color;

    @JoinColumn(name="cat_owner")
    @ManyToOne
    private Owner owner;

    @Transient
    private final List<Cat> friends = new ArrayList<>();

    public void addNewFriend(Cat friendCat) {
        if (!friends.contains(friendCat))
            friends.add(friendCat);
    }
}
