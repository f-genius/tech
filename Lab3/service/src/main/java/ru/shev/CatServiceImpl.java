package ru.shev;

import org.springframework.stereotype.Service;
import ru.shev.entity.Cat;
import ru.shev.entity.Color;

import java.util.List;

@Service
public class CatServiceImpl implements CatService{
    private final CatRepository catRepository;

    public CatServiceImpl(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Override
    public Cat add(Cat cat) {
        return catRepository.saveAndFlush(cat);
    }

    @Override
    public void delete(int id) {
        Cat deletedCat = catRepository.getById(id);
        for (Cat cat : deletedCat.getFriends()) {
            cat.getFriends().remove(deletedCat);
        }
        deletedCat.getOwner().getCats().remove(deletedCat);
        catRepository.deleteById(id);
    }

    @Override
    public Cat getById(int id) {
        return catRepository.getById(id);
    }

    @Override
    public Cat update(Cat cat) {
        return catRepository.saveAndFlush(cat);
    }

    @Override
    public List<Cat> getAll() {
        return catRepository.findAll();
    }

    @Override
    public List<Cat> findByColor(String color) {
        return catRepository.findCatByColor(color.toString());
    }
}
