package ru.shev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shev.models.dto.UserDTO;
import ru.shev.service.UserServiceImpl;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserDTO create(@RequestBody UserDTO userDTO) {
        return userService.add(userDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) throws ParseException {
        userService.delete(id);
    }

    @GetMapping("/")
    @ResponseBody
    public List<UserDTO> getAll() {
        return userService.getAll();
    }
}
