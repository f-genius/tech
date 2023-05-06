package ru.shev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.shev.models.dto.CatDTO;
import ru.shev.models.entity.User;
import ru.shev.service.CatService;
import ru.shev.service.UserServiceImpl;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/cats")
public class CatController {
    private final CatService catService;
    private final UserServiceImpl userService;

    @Autowired
    CatController(CatService catService, UserServiceImpl userService) {
        this.catService = catService;
        this.userService = userService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CatDTO create(@RequestBody CatDTO catDTO) {
        User user = userService.getCurrentUser();
        if (!Objects.equals(user.getOwner().getId(), catDTO.getOwnerId())) {
            return null;
        }
        return catService.add(catDTO);
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        User user = userService.getCurrentUser();
        CatDTO catDTO = getById(id);
        if (Objects.equals(user.getOwner().getId(), catDTO.getOwnerId()))
            catService.delete(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/color/all_{color}_cats")
    @ResponseBody
    public List<CatDTO> getCats(@PathVariable String color) {
        return catService.findByColor(color);
    }

    @GetMapping("/color/{color}")
    @ResponseBody
    public List<CatDTO> getCatsForOwner(@PathVariable String color) {
        User user = userService.getCurrentUser();
        return catService.findByColorAndOwner(color, user.getOwner().getId());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CatDTO getById(@PathVariable int id) {
        return catService.getById(id);
    }

    @PostMapping("/{id}")
    @ResponseBody
    public CatDTO update(@RequestBody CatDTO catDTO, @PathVariable int id) throws ParseException {
        User user = userService.getCurrentUser();
        if (!Objects.equals(user.getOwner().getId(), catDTO.getOwnerId())) {
            return null;
        }
        return catService.update(catDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    @ResponseBody
    public List<CatDTO> getAll() {
        return catService.getAll();
    }
}
