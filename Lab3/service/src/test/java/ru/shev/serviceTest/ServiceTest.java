package ru.shev.serviceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.shev.dto.CatDTO;
import ru.shev.entity.Cat;
import ru.shev.entity.Owner;
import ru.shev.repositories.CatRepository;
import ru.shev.repositories.OwnerRepository;
import ru.shev.services.CatService;
import ru.shev.services.OwnerService;
import ru.shev.util.Mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class ServiceTest {
    @Mock
    private CatRepository catRepository;
    @Mock
    private OwnerRepository ownerRepository;
    private CatService catService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        catService = new CatService(catRepository, ownerRepository);
    }

    @Test
    void createCatTest() {
        Cat cat = new Cat(1, "grafa", new Date(), "black", null, new ArrayList<>());
        given(catRepository.saveAndFlush(cat)).willReturn(cat);
        CatDTO createdCat = catService.add(Mapper.catConvertToDto(cat));
        Assertions.assertEquals(createdCat.getName(), cat.getName());
    }

    @Test
    void getAllTest() {
        Owner owner = new Owner(3, "owner1", new Date(), new ArrayList<>());
        List<Cat> cats = Arrays.asList(
                new Cat(5, "cat5", new Date(), "black", owner, new ArrayList<>()),
                new Cat(6, "cat6", new Date(), "black", owner, new ArrayList<>()),
                new Cat(7, "cat7", new Date(), "black", owner, new ArrayList<>())
        );
        when(catRepository.findAll()).thenReturn(cats);
        List<CatDTO> catDTOS = catService.getAll();
        Assertions.assertEquals(3, catDTOS.size());
    }

    @Test
    void deleteCatTest() {
        int id = 1;
        doNothing().when(catRepository).deleteById(any(Integer.class));
        catService.delete(id);
        verify(catRepository).deleteById(any(Integer.class));
    }
}
