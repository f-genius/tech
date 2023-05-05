package ru.shev.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.shev.models.dto.CatDTO;
import ru.shev.models.entity.Cat;
import ru.shev.models.entity.Owner;
import ru.shev.repository.CatRepository;
import ru.shev.models.util.Mapper;
import ru.shev.repository.OwnerRepository;

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
        Owner owner = ownerRepository.getReferenceById(catDTO.getOwnerId());
        cat.setOwner(owner);
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

    public CatDTO update(CatDTO catDTO) throws ParseException {
        Optional<Cat> updateCat = catRepository.findById(catDTO.getId());
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

    public List<CatDTO> findByColorAndOwner(String color, Integer id) {
        return catRepository.findCatByColorAndOwner(color, id).stream()
                .map(Mapper::catConvertToDto)
                .collect(Collectors.toList());
    }
}
