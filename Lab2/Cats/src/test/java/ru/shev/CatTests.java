package ru.shev;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.shev.daoServices.CatDAOServiceImpl;
import ru.shev.entities.*;
import ru.shev.services.CatServiceImpl;
import ru.shev.services.OwnerServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CatTests {
    @Test
    public void createTest() {
        OwnerServiceImpl ownerService = new OwnerServiceImpl();
        CatServiceImpl catService = new CatServiceImpl();

        Cat cat = new Cat();
        Owner owner = new Owner();
        owner.setId(100);
        owner.setName("owner1");
        owner.setBirthday(new Date());

        cat.setId(101);
        cat.setName("cat1");
        cat.setBirthday(new Date());
        cat.setColor(Color.GINGER.toString());
        cat.setOwner(owner);

        owner.addNewCat(cat);

        ownerService.createOwner(owner);
        catService.createCat(cat);
    }

    @Test
    public void getTest() {
        CatDAOServiceImpl catDAOService = new CatDAOServiceImpl();
        Optional<Cat> cat = Optional.ofNullable(catDAOService.getCat(101));
        List<Cat> cats = catDAOService.getAllCats();
        Assertions.assertNotEquals(null, cat.get());
        Assertions.assertEquals(1, cats.size());
        Assertions.assertTrue(cats.contains(cat.get()));
    }

    @Test
    public void updateTest() {
        Owner owner2 = new Owner();
        owner2.setId(101);
        owner2.setName("owner2");
        owner2.setBirthday(new Date());

        OwnerServiceImpl ownerService = new OwnerServiceImpl();
        ownerService.createOwner(owner2);

        CatServiceImpl catService = new CatServiceImpl();
        catService.updateCat(101, owner2);

        Assertions.assertEquals(owner2.getId(), catService.getCatById(101).getOwner().getId());
    }

    @Test
    public void deleteTest() {
        OwnerServiceImpl ownerService = new OwnerServiceImpl();
        CatServiceImpl catService = new CatServiceImpl();
        ownerService.deleteOwner(101);
        Assertions.assertNull(catService.getCatById(101).getOwner());
    }
}
