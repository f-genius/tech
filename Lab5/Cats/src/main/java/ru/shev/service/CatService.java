package ru.shev.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.shev.dto.CatDTO;
import ru.shev.dto.OwnerDTO;
import ru.shev.entity.Cat;
import ru.shev.entity.Owner;
import ru.shev.repo.CatRepository;
import ru.shev.util.Mapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Component
public class CatService {
    private final CatRepository catRepository;
    private static final Logger logger = LoggerFactory.getLogger(CatService.class);

    @Autowired
    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public CatDTO add(CatDTO catDTO, OwnerDTO ownerDTO) {
        Cat cat = Mapper.dtoConvertToCat(catDTO);
        Owner owner = Mapper.dtoConvertToOwner(ownerDTO);
        cat.setOwner(owner);
        return Mapper.catConvertToDto(catRepository.saveAndFlush(cat));
    }

    public void delete(int id) {
        Optional<Cat> optCat = catRepository.findById(id);
        catRepository.deleteById(id);
        if (optCat.isEmpty())
            return;
        Cat deletedCat = optCat.get();
        for (Cat cat : deletedCat.getFriends()) {
            cat.getFriends().remove(deletedCat);
        }
        deletedCat.getOwner().getCats().remove(deletedCat);
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public CatDTO receiveMessage(int id) {
        Optional<Cat> optCat = catRepository.findById(id);
        logger.info("get cat" + id);
        return Mapper.catConvertToDto(optCat.orElse(null));
    }

    public CatDTO update(CatDTO catDTO) throws ParseException {
        Optional<Cat> updateCat = catRepository.findById(catDTO.getId());
        if (updateCat.isEmpty()) {
            return null;
        }
        Cat oldCat = updateCat.get();
        oldCat.setName(catDTO.getName());
        oldCat.setColor(catDTO.getColor());
        List<Cat> friends = new ArrayList<>();
        if (!CollectionUtils.isEmpty(catDTO.getFriends())) {
            for (CatDTO cat : catDTO.getFriends()) {
                friends.add(catRepository.findById(cat.getId()).get());
            }
        }
        oldCat.setFriends(friends);
        return Mapper.catConvertToDto(catRepository.saveAndFlush(oldCat));
    }

    public List<CatDTO> getAll() {
        return catRepository.findAll().stream()
                .map(Mapper::catConvertToDto)
                .collect(Collectors.toList());
    }

    public List<CatDTO> findByColor(String color) {
        return catRepository.findCatByColor(color).stream()
                .map(Mapper::catConvertToDto)
                .collect(Collectors.toList());
    }
}
