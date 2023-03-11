package ru.shev.services;

import ru.shev.entities.Cat;
import ru.shev.entities.Owner;

import java.util.List;

public interface OwnerService {
    public void createOwner(Owner newOwner);
    public Owner getOwnerById(Integer id);
    public void addNewCat(Integer id, Cat newCat);
    public void deleteOwner(Integer id);
    public void updateOwner(Integer id, String name);
    public List getAllOwners();
}
