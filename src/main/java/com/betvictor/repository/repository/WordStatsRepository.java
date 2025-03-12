package com.betvictor.repository.repository;

import com.betvictor.repository.entity.WordStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordStatsRepository extends JpaRepository<WordStatsEntity, Long> {

    List<WordStatsEntity> findTop10ByOrderByCreatedAtDesc();

}
