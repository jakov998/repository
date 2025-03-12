package com.betvictor.repository.service;

import com.betvictor.repository.entity.WordStatsEntity;
import com.betvictor.repository.mapper.WordStatsMapper;
import com.betvictor.repository.model.WordStats;
import com.betvictor.repository.repository.WordStatsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KafkaTextProcessingConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaTextProcessingConsumer.class);

    private final ObjectMapper objectMapper;
    private final WordStatsRepository repository;
    private final WordStatsMapper wordStatsMapper;
    private final Clock clock;

    @KafkaListener(
            topics = "${spring.kafka.consumer.topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            concurrency = "${spring.kafka.consumer.concurrency}"
    )
    public void consume(String message) {
        logger.info("Consumed Kafka message: {}", message);

        try {
            WordStats wordStats = objectMapper.readValue(message, WordStats.class);

            WordStatsEntity wordStatsEntity = wordStatsMapper.toEntity(wordStats);
            wordStatsEntity.setCreatedAt(LocalDateTime.now(clock));

            repository.save(wordStatsEntity);
            logger.info("Record saved to database: {}", wordStats);

        } catch (JsonProcessingException e) {
            logger.error("Failed to deserialize Kafka message: {}", message, e);
        } catch (DataAccessException e) {
            logger.error("Database error while saving WordStats: {}", message, e);
        } catch (Exception e) {
            logger.error("Unexpected error processing Kafka message: {}", message, e);
        }
    }
}