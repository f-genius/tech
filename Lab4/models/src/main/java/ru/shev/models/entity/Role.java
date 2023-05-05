package ru.shev.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="roles_table")
public class Role {
    @Id
    @Column(name="role_id")
    private Long id;
    @Column(name="role_name")
    private String name;

    @ManyToMany(mappedBy = "roles", cascade=CascadeType.MERGE)
    private List<User> users;
}
