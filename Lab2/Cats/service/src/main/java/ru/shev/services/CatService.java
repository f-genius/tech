package ru.shev.services;

import ru.shev.entities.Cat;
import ru.shev.entities.Owner;

import java.util.List;

public interface CatService {
    public void createCat(Cat newCat);
    public Cat getCatById(Integer id);
    public void updateCat(Integer id, Owner owner);
    public void deleteCat(Integer id);
    public List getAllCats();
    public void addNewFriend(Integer id, Cat newCat);
}
