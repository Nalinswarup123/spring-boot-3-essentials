package com.example.roomclr;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@SpringBootApplication
public class RoomClrApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomClrApplication.class, args);
    }

    public static final String QUEUE_NAME = "room-cleaner";
    public static final String EXCHANGE_NAME = "operations";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("landon.#");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate, RabbitTemplate template, ObjectMapper mapper) {
        return args -> {
            ResponseEntity<List<Room>> rooms = restTemplate.exchange("http://localhost:8080/api/rooms",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Room>>() {
                    });
            rooms.getBody().forEach(r -> {
                AsyncPayload payload = new AsyncPayload();
                payload.setId(r.getId());
                payload.setModel("ROOM");
                try {
                    template.convertAndSend(EXCHANGE_NAME, "landon.room.cleaner", mapper.writeValueAsString(payload));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        };
    }

}
