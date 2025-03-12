package com.betvictor.repository.controller;

import com.betvictor.repository.entity.WordStatsEntity;
import com.betvictor.repository.repository.WordStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/betvictor")
@RequiredArgsConstructor
public class HistoryController {

    private final WordStatsRepository repository;

    @GetMapping("/history")
    public List<WordStatsEntity> getHistory() {

        return repository.findTop10ByOrderByCreatedAtDesc();
    }
}