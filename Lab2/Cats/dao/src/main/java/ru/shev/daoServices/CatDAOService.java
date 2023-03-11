package ru.shev.daoServices;

import ru.shev.entities.Cat;
import ru.shev.entities.Owner;

import java.util.List;

public interface CatDAOService {
    public Integer addCat(Cat newCat);
    public List<Cat> getAllCats();
    public Cat getCat(Integer id);
    public void updateCat(Integer id, Owner owner);
    public void deleteCat(Integer id);
}
