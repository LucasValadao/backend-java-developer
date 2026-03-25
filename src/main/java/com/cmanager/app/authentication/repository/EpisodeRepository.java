package com.cmanager.app.authentication.repository;

import com.cmanager.app.application.domain.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    boolean existsByIdIntegration(Integer idIntegration);
}