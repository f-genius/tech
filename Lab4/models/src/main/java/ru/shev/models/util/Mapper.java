package ru.shev.models.util;

import org.modelmapper.ModelMapper;
import ru.shev.models.dto.CatDTO;
import ru.shev.models.dto.OwnerDTO;
import ru.shev.models.dto.RoleDTO;
import ru.shev.models.dto.UserDTO;
import ru.shev.models.entity.Cat;
import ru.shev.models.entity.Owner;
import ru.shev.models.entity.Role;
import ru.shev.models.entity.User;

import java.util.ArrayList;
import java.util.List;


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

    public static UserDTO userConvertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public static User dtoConvertToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        user.setOwner(dtoConvertToOwner(userDTO.getOwner()));
        List<Role> roles = new ArrayList<>();
        for (RoleDTO roleDTO : userDTO.getRoles()) {
            Role role = dtoConvertToRole(roleDTO);
            roles.add(role);
        }
        user.setRoles(roles);
        return user;
    }

    public static RoleDTO roleConvertToDto(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

    public static Role dtoConvertToRole(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, Role.class);
    }
}
