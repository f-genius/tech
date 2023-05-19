package ru.shev.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.shev.entity.Cat;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Integer> {
    @Query(value = "select * from cats_table where lower(concat(cat_color))" +
            "like lower(?1)",
    nativeQuery = true)
    List<Cat> findCatByColor(String color);
}
