package ru.shev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shev.services.OwnerService;
import ru.shev.dto.OwnerDTO;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {
    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public OwnerDTO create(@RequestBody OwnerDTO ownerDTO) {
        return ownerService.add(ownerDTO);
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
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
        return ownerService.update(ownerDTO, id);
    }

    @GetMapping("/")
    @ResponseBody
    public List<OwnerDTO> getAll() {
        return ownerService.getAll();
    }
}
