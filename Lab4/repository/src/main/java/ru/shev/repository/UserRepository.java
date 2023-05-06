package ru.shev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shev.models.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByLogin(String login);
}
