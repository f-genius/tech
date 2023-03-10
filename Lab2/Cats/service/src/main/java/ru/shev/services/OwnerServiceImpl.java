package ru.shev.services;

import ru.shev.daoServices.OwnerDAOServiceImpl;
import ru.shev.entities.Cat;
import ru.shev.entities.Owner;

import java.util.List;

public class OwnerServiceImpl implements OwnerService {
    @Override
    public void createOwner(Owner newOwner) {
        new OwnerDAOServiceImpl().addOwner(newOwner);
    }
    @Override
    public Owner getOwnerById(Integer id) {
        return new OwnerDAOServiceImpl().getOwner(id);
    }

    @Override
    public void addNewCat(Integer id, Cat newCat) {
        Owner owner = new OwnerDAOServiceImpl().getOwner(id);
        owner.addNewCat(newCat);
    }
    @Override
    public void deleteOwner(Integer id) {
        new OwnerDAOServiceImpl().deleteOwner(id);
    }

    @Override
    public void updateOwner(Integer id, String name) {
        new OwnerDAOServiceImpl().updateOwner(id, name);
    }

    @Override
    public List getAllOwners() {
        return new OwnerDAOServiceImpl().getAllOwners();
    }
}
