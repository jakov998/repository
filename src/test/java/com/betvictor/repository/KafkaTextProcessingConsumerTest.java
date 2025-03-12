package com.betvictor.repository;

import com.betvictor.repository.entity.WordStatsEntity;
import com.betvictor.repository.repository.WordStatsRepository;
import com.betvictor.repository.service.KafkaTextProcessingConsumer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@SpringBootTest
@EmbeddedKafka(partitions = 4, topics = { "words.processed" })
public class KafkaTextProcessingConsumerTest {

    private static final String MOST_FREQUENT_WORD= "word1";
    private static final BigDecimal AVG_PARAGRAPH_SIZE = BigDecimal.valueOf(460.50).setScale(2, RoundingMode.HALF_UP);
    private static final BigDecimal AVG_PROCESSING_TIME = BigDecimal.valueOf(181.55).setScale(2, RoundingMode.HALF_UP);
    private static final BigDecimal TOTAL_PROCESSING_TIME = BigDecimal.valueOf(1810.00).setScale(2, RoundingMode.HALF_UP);

    @Autowired
    private KafkaTextProcessingConsumer kafkaTextProcessingConsumer;

    @MockitoBean
    private WordStatsRepository wordStatsRepository;

    @MockitoBean
    private Clock clock;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testConsume() throws JsonProcessingException {
        // Arrange
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("freq_word", MOST_FREQUENT_WORD);
        messageMap.put("avg_paragraph_size", AVG_PARAGRAPH_SIZE);
        messageMap.put("avg_paragraph_processing_time", AVG_PROCESSING_TIME);
        messageMap.put("total_processing_time", TOTAL_PROCESSING_TIME);

        String message = objectMapper.writeValueAsString(messageMap);

        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        when(clock.instant()).thenReturn(Instant.now());

        // Act
        kafkaTextProcessingConsumer.consume(message);

        // Assert
        WordStatsEntity expectedWordStats = WordStatsEntity.builder()
                .mostFrequentWord(MOST_FREQUENT_WORD)
                .avgParagraphSize(AVG_PARAGRAPH_SIZE)
                .avgParagraphProcessingTime(AVG_PROCESSING_TIME)
                .totalProcessingTime(TOTAL_PROCESSING_TIME)
                .createdAt(LocalDateTime.now(clock))
                .build();

        verify(wordStatsRepository, times(1)).save(expectedWordStats);
    }
}