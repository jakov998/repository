package com.betvictor.repository.controller;

import com.betvictor.repository.mapper.WordStatsMapper;
import com.betvictor.repository.model.WordStats;
import com.betvictor.repository.repository.WordStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/betvictor")
@RequiredArgsConstructor
public class HistoryController {

    private final WordStatsRepository repository;

    private final WordStatsMapper wordStatsMapper;

    @GetMapping("/history")
    public List<WordStats> getHistory() {

        return repository.findTop10ByOrderByCreatedAtDesc().stream().map(wordStatsMapper::toModel).collect(Collectors.toList());
    }
}