package com.betvictor.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Table(name="WordStats")
public class WordStatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String mostFrequentWord;
    private BigDecimal avgParagraphSize;
    private BigDecimal avgParagraphProcessingTime;
    private BigDecimal totalProcessingTime;

    private LocalDateTime createdAt;
}
