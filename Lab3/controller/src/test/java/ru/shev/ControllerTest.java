package ru.shev;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import ru.shev.dto.CatDTO;
import ru.shev.entity.Cat;
import ru.shev.services.CatService;
import ru.shev.util.Mapper;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.BDDMockito.given;

@ContextConfiguration
@SpringBootTest(classes = catApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ControllerTest {
    @MockBean
    private CatService catService;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void canCreate() {
        Cat cat = new Cat(10, "cat", new Date(), "ginger", null, new ArrayList<>());
        given(catService.add(Mapper.catConvertToDto(cat))).willReturn(Mapper.catConvertToDto(cat));
        ResponseEntity<CatDTO> catResponse = restTemplate.postForEntity("http://localhost:8080/cats/create", new CatDTO(10, "cat", "ginger", new ArrayList<>()), CatDTO.class);
        Assertions.assertEquals(catResponse.getStatusCode(), HttpStatus.CREATED);
    }
}
