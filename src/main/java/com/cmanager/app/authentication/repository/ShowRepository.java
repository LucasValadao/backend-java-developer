package com.cmanager.app.authentication.repository;

import com.cmanager.app.application.domain.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show, String> {

    Optional<Show> findByIdIntegration(Integer idIntegration);

    boolean existsByIdIntegration(Integer idIntegration);
}