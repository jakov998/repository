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

    @Mapping(source = "mostFrequentWord", target = "freq_word")
    @Mapping(source = "avgParagraphSize", target = "avg_paragraph_size")
    @Mapping(source = "avgParagraphProcessingTime", target = "avg_paragraph_processing_time")
    @Mapping(source = "totalProcessingTime", target = "total_processing_time")
    WordStats toModel(WordStatsEntity wordStatsEntity);
}
