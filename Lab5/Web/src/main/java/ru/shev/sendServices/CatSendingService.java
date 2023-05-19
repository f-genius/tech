package ru.shev.sendServices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.AmqpTemplate;
import ru.shev.dto.CatDTO;

@Service
@Slf4j
@Component
public class CatSendingService {
    private final AmqpTemplate rabbitTemplate;
    @Autowired
    public CatSendingService(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;

    public CatDTO getCatById(int id) {
        return (CatDTO) rabbitTemplate.convertSendAndReceive(exchange, routingkey, id);
    }
}
