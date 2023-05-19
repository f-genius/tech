package ru.shev.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shev.dto.CatDTO;
import ru.shev.sendServices.CatSendingService;

import java.util.List;

@RestController
@RequestMapping("/cats")
public class CatController {
    private final CatSendingService catSendingService;
    private static final Logger logger = LoggerFactory.getLogger(CatController.class);

    @Autowired
    CatController(CatSendingService catSendingService) {
        this.catSendingService = catSendingService;
    }

//    @PostMapping("/create")
//    @ResponseStatus(HttpStatus.CREATED)
//    @ResponseBody
//    public CatDTO create(@RequestBody CatDTO catDTO) {
//        return catService.add(catDTO);
//    }
//
//    @GetMapping("/delete/{id}")
//    public void delete(@PathVariable int id) {
//        CatDTO catDTO = getById(id);
//        catService.delete(id);
//    }
//
//    @GetMapping("/color/all_{color}_cats")
//    @ResponseBody
//    public List<CatDTO> getCats(@PathVariable String color) {
//        return catService.findByColor(color);
//    }

    @GetMapping("/{id}")
    @ResponseBody
    public CatDTO getById(@PathVariable int id) {
        CatDTO cat = catSendingService.getCatById(id);
        logger.info("cat sent" + id);
        return cat;
    }

//    @PostMapping("/{id}")
//    @ResponseBody
//    public CatDTO update(@RequestBody CatDTO catDTO, @PathVariable int id) {
//        return catService.update(catDTO);
//    }
//
//    @GetMapping("/")
//    @ResponseBody
//    public List<CatDTO> getAll() {
//        return catService.getAll();
//    }
}
