package ru.shev.services;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.shev.repositories.CatRepository;
import ru.shev.repositories.OwnerRepository;
import ru.shev.dto.CatDTO;
import ru.shev.entity.Cat;
import ru.shev.util.Mapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CatService {
    private final CatRepository catRepository;
    private final OwnerRepository ownerRepository;

    public CatService(CatRepository catRepository, OwnerRepository ownerRepository) {
        this.catRepository = catRepository;
        this.ownerRepository = ownerRepository;
    }

    public CatDTO add(CatDTO catDTO) {
        Cat cat = Mapper.dtoConvertToCat(catDTO);
        return Mapper.catConvertToDto(catRepository.saveAndFlush(cat));
    }

    public void delete(int id) {
        Optional<Cat> optCat = catRepository.findById(id);
        catRepository.deleteById(id);
        if (optCat.isEmpty())
            return;
        Cat deletedCat = optCat.get();
        for (Cat cat : deletedCat.getFriends()) {
            cat.getFriends().remove(deletedCat);
        }
        deletedCat.getOwner().getCats().remove(deletedCat);
    }

    public CatDTO getById(int id) {
        Optional<Cat> optCat = catRepository.findById(id);
        return Mapper.catConvertToDto(optCat.orElse(null));
    }

    public CatDTO update(CatDTO catDTO, int id) throws ParseException {
        Optional<Cat> updateCat = catRepository.findById(id);
        if (updateCat.isEmpty()) {
            return null;
        }
        Cat oldCat = updateCat.get();
        oldCat.setName(catDTO.getName());
        oldCat.setColor(catDTO.getColor());
        List<Cat> friends = new ArrayList<>();
        if (!CollectionUtils.isEmpty(catDTO.getFriends())) {
            for (CatDTO cat : catDTO.getFriends()) {
                friends.add(catRepository.findById(cat.getId()).get());
            }
        }
        oldCat.setFriends(friends);
        return Mapper.catConvertToDto(catRepository.saveAndFlush(oldCat));
    }

    public List<CatDTO> getAll() {
        return catRepository.findAll().stream()
                .map(Mapper::catConvertToDto)
                .collect(Collectors.toList());
    }

    public List<CatDTO> findByColor(String color) {
        return catRepository.findCatByColor(color).stream()
                .map(Mapper::catConvertToDto)
                .collect(Collectors.toList());
    }
}
