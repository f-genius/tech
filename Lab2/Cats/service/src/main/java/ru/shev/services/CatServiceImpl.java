package ru.shev.services;

import ru.shev.daoServices.CatDAOServiceImpl;
import ru.shev.entities.Cat;
import ru.shev.entities.Owner;

import java.util.List;

public class CatServiceImpl implements CatService {

    @Override
    public void createCat(Cat newCat) {
        new CatDAOServiceImpl().addCat(newCat);
    }

    @Override
    public Cat getCatById(Integer id) {
        return new CatDAOServiceImpl().getCat(id);
    }

    @Override
    public void updateCat(Integer id, Owner owner) {
        new CatDAOServiceImpl().updateCat(id, owner);
    }

    @Override
    public void deleteCat(Integer id) {
        new CatDAOServiceImpl().deleteCat(id);
    }

    @Override
    public List getAllCats() {
        return new CatDAOServiceImpl().getAllCats();
    }

    @Override
    public void addNewFriend(Integer id, Cat newCat) {
        Cat cat = new CatDAOServiceImpl().getCat(id);
        cat.addNewFriend(newCat);
    }
}
