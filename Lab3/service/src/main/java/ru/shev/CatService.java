package ru.shev;

import ru.shev.entity.Cat;
import ru.shev.entity.Color;

import java.util.List;

public interface CatService {
    Cat add(Cat cat);
    void delete(int id);
    Cat getById(int id);
    Cat update(Cat cat);
    List<Cat> getAll();
    List<Cat> findByColor(String color);
}
