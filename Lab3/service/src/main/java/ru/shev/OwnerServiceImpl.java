package ru.shev;

import org.springframework.stereotype.Service;
import ru.shev.entity.Cat;
import ru.shev.entity.Owner;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Owner add(Owner owner) {
        return ownerRepository.saveAndFlush(owner);
    }

    @Override
    public void delete(int id) {
        Owner deletedOwner = ownerRepository.getById(id);
        for (Cat cat : deletedOwner.getCats()) {
            cat.setOwner(null);
        }
        ownerRepository.delete(deletedOwner);
    }

    @Override
    public Owner getById(int id) {
        return ownerRepository.getById(id);
    }

    @Override
    public void update(Owner owner) {
        ownerRepository.saveAndFlush(owner);
    }

    @Override
    public List<Owner> getAll() {
        return ownerRepository.findAll();
    }
}
