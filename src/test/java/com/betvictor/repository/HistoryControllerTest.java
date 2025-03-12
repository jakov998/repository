package com.betvictor.repository;

import com.betvictor.repository.entity.WordStatsEntity;
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
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
    private static final String CREATED_AT_STRING = CREATED_AT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WordStatsRepository repository;

    @Test
    public void testGetHistory() throws Exception {
        // Arrange
        WordStatsEntity wordStats1 = WordStatsEntity.builder()
                .mostFrequentWord(MOST_FREQUENT_WORD)
                .avgParagraphSize(AVG_PARAGRAPH_SIZE)
                .avgParagraphProcessingTime(AVG_PROCESSING_TIME)
                .totalProcessingTime(TOTAL_PROCESSING_TIME)
                .createdAt(CREATED_AT)
                .build();

        List<WordStatsEntity> wordStatsList = Arrays.asList(wordStats1);

        when(repository.findTop10ByOrderByCreatedAtDesc()).thenReturn(wordStatsList);

        // Act and assert
        mockMvc.perform(get("/betvictor/history"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("""
                    [
                        {
                                "mostFrequentWord": "%s",
                                "avgParagraphSize": %f,
                                "avgParagraphProcessingTime": %f,
                                "totalProcessingTime": %f,
                                "createdAt": "%s"
                        }
                    ]""".formatted(MOST_FREQUENT_WORD, AVG_PARAGRAPH_SIZE, AVG_PROCESSING_TIME, TOTAL_PROCESSING_TIME, CREATED_AT_STRING)));
    }
}
