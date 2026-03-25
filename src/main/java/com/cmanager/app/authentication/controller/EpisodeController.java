package com.cmanager.app.authentication.controller;

import com.cmanager.app.application.data.EpisodeAverageDTO;
import com.cmanager.app.authentication.service.EpisodeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/episodes")
@Tag(
        name = "EpisodeController",
        description = "API de episodios"
)
public class EpisodeController {
    private final EpisodeService episodeService;

    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @GetMapping("/average")
    public ResponseEntity<List<EpisodeAverageDTO>> average() {
        return ResponseEntity.ok(episodeService.getAverageBySeason());
    }
}
