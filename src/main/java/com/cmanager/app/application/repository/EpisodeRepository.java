package com.cmanager.app.application.repository;

import com.cmanager.app.application.domain.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    boolean existsByIdIntegration(Integer idIntegration);

    @Query("""
    SELECT e.season,
           COALESCE(AVG(e.rating), 0)
    FROM Episode e
    GROUP BY e.season
    ORDER BY e.season
""")
    List<Object[]> findAverageRatingBySeason();
}