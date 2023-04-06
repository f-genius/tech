package ru.shev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shev.dto.CatDTO;
import ru.shev.entity.Cat;
import ru.shev.util.Mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        Cat cat = Mapper.dtoConvertToCat(catDTO);
        return Mapper.catConvertToDto(catService.add(cat));
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        catService.delete(id);
    }

    @GetMapping
    @ResponseBody
    public List<CatDTO> getCats(String color) {
        return catService.findByColor(color).stream()
                .map(Mapper::catConvertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CatDTO getById(@PathVariable int id) {
        return Mapper.catConvertToDto(catService.getById(id));
    }

    @PostMapping("/{id}")
    @ResponseBody
    public CatDTO update(@RequestBody CatDTO catDTO, @PathVariable int id) {
        if (!Objects.equals(id, catDTO.getId())) {
            throw new IllegalArgumentException("IDs are not equal");
        }
        Cat cat = Mapper.dtoConvertToCat(catDTO);
        return Mapper.catConvertToDto(catService.update(cat));
    }
}
