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
        return modelMapper.map(owner, OwnerDTO.class);
    }

    public static Owner dtoConvertToOwner(OwnerDTO ownerDTO) {
        return modelMapper.map(ownerDTO, Owner.class);
    }
}
