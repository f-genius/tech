package ru.shev.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.shev.dto.CatDTO;
import ru.shev.dto.OwnerDTO;
import ru.shev.entity.Cat;
import ru.shev.entity.Owner;
import ru.shev.util.Mapper;
import ru.shev.repo.OwnerRepository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public OwnerDTO add(OwnerDTO ownerDTO) {
        Owner owner = Mapper.dtoConvertToOwner(ownerDTO);
        return Mapper.ownerConvertToDto(ownerRepository.saveAndFlush(owner));
    }

    public void delete(int id) throws ParseException {
        Optional<Owner> optOwner = ownerRepository.findById(id);
        if (optOwner.isPresent()) {
            Owner owner = optOwner.get();
            for (Cat cat : owner.getCats()) {
                cat.setOwner(null);
            }
            ownerRepository.deleteById(id);
        }
    }

    public OwnerDTO getById(int id) {
        Optional<Owner> optionalOwner = ownerRepository.findById(id);
        return Mapper.ownerConvertToDto(optionalOwner.orElse(null));
    }

    public OwnerDTO update(OwnerDTO ownerDTO, int id) throws ParseException {
        Optional<Owner> optOwner = ownerRepository.findById(id);
        if (optOwner.isEmpty()) {
            return null;
        }

        Owner oldOwner = optOwner.get();
        oldOwner.setName(ownerDTO.getName());
        List<Cat> cats = new ArrayList<>();
        if (!CollectionUtils.isEmpty(ownerDTO.getCats())) {
            for (CatDTO cat: ownerDTO.getCats()) {
                cats.add(Mapper.dtoConvertToCat(cat));
            }
        }
        oldOwner.setCats(cats);
        return Mapper.ownerConvertToDto(ownerRepository.saveAndFlush(oldOwner));
    }

    public List<OwnerDTO> getAll() {
        return ownerRepository.findAll().stream()
                .map(Mapper::ownerConvertToDto)
                .collect(Collectors.toList());
    }
}
