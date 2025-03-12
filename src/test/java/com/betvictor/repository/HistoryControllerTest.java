package com.betvictor.repository;

import com.betvictor.repository.entity.WordStatsEntity;
import com.betvictor.repository.mapper.WordStatsMapper;
import com.betvictor.repository.model.WordStats;
import com.betvictor.repository.repository.WordStatsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class HistoryControllerTest {

    private static final String MOST_FREQUENT_WORD = "word1";
    private static final BigDecimal AVG_PARAGRAPH_SIZE = BigDecimal.valueOf(460.50).setScale(2, RoundingMode.HALF_UP);
    private static final BigDecimal AVG_PROCESSING_TIME = BigDecimal.valueOf(181.55).setScale(2, RoundingMode.HALF_UP);
    private static final BigDecimal TOTAL_PROCESSING_TIME = BigDecimal.valueOf(1810.00).setScale(2, RoundingMode.HALF_UP);
    private static final LocalDateTime CREATED_AT = LocalDateTime.now();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WordStatsMapper wordStatsMapper;

    @MockitoBean
    private WordStatsRepository repository;

    @Test
    public void testGetHistory() throws Exception {
        // Arrange
        WordStatsEntity wordStatsEntity = WordStatsEntity.builder()
                .mostFrequentWord(MOST_FREQUENT_WORD)
                .avgParagraphSize(AVG_PARAGRAPH_SIZE)
                .avgParagraphProcessingTime(AVG_PROCESSING_TIME)
                .totalProcessingTime(TOTAL_PROCESSING_TIME)
                .createdAt(CREATED_AT)
                .build();

        WordStats wordStats = new WordStats(MOST_FREQUENT_WORD, AVG_PARAGRAPH_SIZE, AVG_PROCESSING_TIME, TOTAL_PROCESSING_TIME);
        when(repository.findTop10ByOrderByCreatedAtDesc()).thenReturn(List.of(wordStatsEntity));
        when(wordStatsMapper.toModel(wordStatsEntity)).thenReturn(wordStats);

        // Act and assert
        mockMvc.perform(get("/betvictor/history"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("""
                    [
                        {
                                "freq_word": "%s",
                                "avg_paragraph_size": %f,
                                "avg_paragraph_processing_time": %f,
                                "total_processing_time": %f
                        }
                    ]""".formatted(MOST_FREQUENT_WORD, AVG_PARAGRAPH_SIZE, AVG_PROCESSING_TIME, TOTAL_PROCESSING_TIME)));
    }
}
