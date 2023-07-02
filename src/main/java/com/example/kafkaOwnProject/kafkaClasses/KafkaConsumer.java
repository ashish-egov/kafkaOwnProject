package com.example.kafkaOwnProject.kafkaClasses;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaConsumer {

    private final List<String> messages = new ArrayList<>();

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @PostConstruct
    public void createTable() {
        // Create the messages table if it doesn't exist
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS messages (id SERIAL, message TEXT NOT NULL)");
    }
    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public void receiveMessage(String message) {
        messages.add(message);
        saveToDatabase(message);
    }

    private void saveToDatabase(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);
            String messageContent = jsonNode.get("message").asText();
            jdbcTemplate.update("INSERT INTO messages (message) VALUES (?)", messageContent);
        } catch (JsonProcessingException e) {
            System.err.println("Error parsing message: " + message);
            e.printStackTrace();
        }
    }

    public List<String> getMessages() {
        return messages;
    }

}