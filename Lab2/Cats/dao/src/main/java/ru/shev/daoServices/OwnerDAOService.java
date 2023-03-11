package ru.shev.daoServices;

import ru.shev.entities.Owner;

import java.util.List;

public interface OwnerDAOService {
    public Integer addOwner(Owner newOwner);
    public List<Owner> getAllOwners();
    public Owner getOwner(Integer id);
    public void updateOwner(Integer id, String name);
    public void deleteOwner(Integer id);
}
