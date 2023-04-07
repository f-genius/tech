package ru.shev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shev.services.CatService;
import ru.shev.dto.CatDTO;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/cats")
public class CatController {
    private final CatService catService;

    @Autowired
    CatController(CatService catService) {
        this.catService = catService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CatDTO create(@RequestBody CatDTO catDTO) {
        return catService.add(catDTO);
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        catService.delete(id);
    }

    @GetMapping("/color/{color}")
    @ResponseBody
    public List<CatDTO> getCats(@PathVariable String color) {
        return catService.findByColor(color);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CatDTO getById(@PathVariable int id) {
        return catService.getById(id);
    }

    @PostMapping("/{id}")
    @ResponseBody
    public CatDTO update(@RequestBody CatDTO catDTO, @PathVariable int id) throws ParseException {
        return catService.update(catDTO, id);
    }

    @GetMapping("/")
    @ResponseBody
    public List<CatDTO> getAll() {
        return catService.getAll();
    }
}
