package com.example.kafkaOwnProject.Controller;

import com.example.kafkaOwnProject.kafkaClasses.KafkaConsumer;
import com.example.kafkaOwnProject.kafkaClasses.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {
    private final KafkaProducer kafkaProducer;

    private final KafkaConsumer kafkaConsumer;

    public MessageController(KafkaProducer kafkaProducer, KafkaConsumer kafkaConsumer) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaConsumer = kafkaConsumer;
    }


    @GetMapping("/messages")
    public ResponseEntity<List<String>> getMessages() {
        List<String> messages = kafkaConsumer.getMessages();
        return ResponseEntity.ok(messages);
    }
    @PostMapping("/messages")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        kafkaProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent successfully");
    }

}
