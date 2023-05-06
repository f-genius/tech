package ru.shev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.shev.models.dto.RoleDTO;
import ru.shev.models.dto.UserDTO;
import ru.shev.models.entity.Owner;
import ru.shev.models.entity.Role;
import ru.shev.models.entity.User;
import ru.shev.models.util.Mapper;
import ru.shev.models.util.UserDetailImpl;
import ru.shev.repository.RoleRepository;
import ru.shev.repository.UserRepository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final OwnerService ownerService;

    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, OwnerService ownerService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.ownerService = ownerService;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findByLogin(login));
        if (user.isEmpty()) {
            throw new NullPointerException();
        }
        return new UserDetailImpl(user.get());
    }

    public UserDTO add(UserDTO userDTO) {
        User user = Mapper.dtoConvertToUser(userDTO);
        user.getRoles().clear();
        List<RoleDTO> rolesDTO = new ArrayList<>();
        for (RoleDTO roleDTO : userDTO.getRoles()) {
            Optional<Role> cur = roleRepository.findById(roleDTO.getId());
            Role curRole = cur.get();
            user.getRoles().add(curRole);
            curRole.getUsers().add(user);
            rolesDTO.add(Mapper.roleConvertToDto(cur.get()));
        }
        userRepository.saveAndFlush(user);
        userDTO = Mapper.userConvertToDto(user);
        userDTO.setRoles(rolesDTO);
        return userDTO;
    }

    public void delete(long id) throws ParseException {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isEmpty())
            return;
        User deletedUser = optUser.get();
        Owner owner = deletedUser.getOwner();
        ownerService.delete(owner.getId());
        userRepository.deleteById(id);
    }

    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(Mapper::userConvertToDto)
                .collect(Collectors.toList());
    }

    public User getCurrentUser() {
        UserDetailImpl userDetail = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetail.getUser();
    }
}
