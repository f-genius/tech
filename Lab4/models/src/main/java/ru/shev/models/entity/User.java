package ru.shev.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of = {"id"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "users_table")
public class User {
    @Id
    @Column(name="user_id")
    private Long id;
    @Column(name="user_login")
    private String login;
    @Column(name="user_password")
    private String password;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "role_id")
    )
    private List<Role> roles = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_owner_id", referencedColumnName="owner_id")
    private Owner owner;
}
