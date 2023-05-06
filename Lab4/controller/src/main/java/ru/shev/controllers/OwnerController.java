package ru.shev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shev.models.dto.OwnerDTO;
import ru.shev.models.dto.RoleDTO;
import ru.shev.models.entity.User;
import ru.shev.service.OwnerService;
import ru.shev.service.UserServiceImpl;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/owners")
public class OwnerController {
    private final OwnerService ownerService;
    private final UserServiceImpl userService;

    @Autowired
    public OwnerController(OwnerService ownerService, UserServiceImpl userService) {
        this.ownerService = ownerService;
        this.userService = userService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public OwnerDTO create(@RequestBody OwnerDTO ownerDTO) {
        User user = userService.getCurrentUser();
        RoleDTO role = new RoleDTO();
        role.setId(1L);
        role.setName("ROLE_ADMIN");
        if (!(Objects.equals(user.getOwner().getId(), ownerDTO.getId()) ||
                user.getRoles().contains(role))) {
            return null;
        }
        return ownerService.add(ownerDTO);
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable int id) throws ParseException {
        User user = userService.getCurrentUser();
        RoleDTO role = new RoleDTO();
        role.setId(1L);
        role.setName("ROLE_ADMIN");
        if ((!(Objects.equals(user.getOwner().getId(), id) ||
                user.getRoles().contains(role)))) {
            return;
        }
        ownerService.delete(id);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public OwnerDTO getById(@PathVariable int id) {
        return ownerService.getById(id);
    }

    @PostMapping("/{id}")
    @ResponseBody
    public OwnerDTO update(@RequestBody OwnerDTO ownerDTO, @PathVariable int id) throws ParseException {
        User user = userService.getCurrentUser();
        RoleDTO role = new RoleDTO();
        role.setId(1L);
        role.setName("ROLE_ADMIN");
        if ((!(Objects.equals(user.getOwner().getId(), id) ||
                user.getRoles().contains(role)))) {
            return null;
        }
        return ownerService.update(ownerDTO, id);
    }

    @GetMapping("/")
    @ResponseBody
    public List<OwnerDTO> getAll() {
        return ownerService.getAll();
    }
}
