package com.betvictor.repository.mapper;

import com.betvictor.repository.entity.WordStatsEntity;
import com.betvictor.repository.model.WordStats;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WordStatsMapper {

    @Mapping(source = "freq_word", target = "mostFrequentWord")
    @Mapping(source = "avg_paragraph_size", target = "avgParagraphSize")
    @Mapping(source = "avg_paragraph_processing_time", target = "avgParagraphProcessingTime")
    @Mapping(source = "total_processing_time", target = "totalProcessingTime")
    WordStatsEntity toEntity(WordStats wordStats);
}
