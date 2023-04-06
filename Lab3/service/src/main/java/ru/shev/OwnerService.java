package ru.shev;

import ru.shev.entity.Owner;

import java.util.List;

public interface OwnerService {
    Owner add(Owner owner);
    void delete(int id);
    Owner getById(int id);
    void update(Owner cat);
    List<Owner> getAll();
}
