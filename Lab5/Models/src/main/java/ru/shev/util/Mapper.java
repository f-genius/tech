package ru.shev.util;

import org.modelmapper.ModelMapper;
import ru.shev.dto.CatDTO;
import ru.shev.dto.OwnerDTO;
import ru.shev.entity.Cat;
import ru.shev.entity.Owner;

public class Mapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static CatDTO catConvertToDto(Cat cat) {
        return modelMapper.map(cat, CatDTO.class);
    }

    public static Cat dtoConvertToCat(CatDTO catDTO) {
        return modelMapper.map(catDTO, Cat.class);
    }

    public static OwnerDTO ownerConvertToDto(Owner owner) {
        OwnerDTO ownerDTO = new OwnerDTO();
        ownerDTO.setBirthday(owner.getBirthday());
        ownerDTO.setId(owner.getId());
        ownerDTO.setName(owner.getName());
        for (Cat cat : owner.getCats()) {
            CatDTO catDTO = Mapper.catConvertToDto(cat);
            ownerDTO.getCats().add(catDTO);
        }
        return ownerDTO;
    }

    public static Owner dtoConvertToOwner(OwnerDTO ownerDTO) {
        Owner owner = new Owner();
        owner.setId(ownerDTO.getId());
        owner.setName(ownerDTO.getName());
        owner.setBirthday(ownerDTO.getBirthday());
        for (CatDTO catDTO : ownerDTO.getCats()) {
            Cat cat = Mapper.dtoConvertToCat(catDTO);
            owner.getCats().add(cat);
        }
        return owner;
    }
}
