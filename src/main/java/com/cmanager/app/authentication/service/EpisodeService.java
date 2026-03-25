package com.cmanager.app.authentication.service;

import com.cmanager.app.application.data.EpisodeAverageDTO;
import com.cmanager.app.authentication.repository.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EpisodeService {
    private final EpisodeRepository episodeRepository;

    public EpisodeService(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    public List<EpisodeAverageDTO> getAverageBySeason() {

        List<Object[]> result = episodeRepository.findAverageRatingBySeason();

        if (result.isEmpty()) {
            throw new RuntimeException("Nao ha episodios cadastrados");
        }

        return result.stream()
                .map(r -> new EpisodeAverageDTO(
                        (Integer) r[0],
                        (BigDecimal) r[1]
                ))
                .toList();
    }
}
