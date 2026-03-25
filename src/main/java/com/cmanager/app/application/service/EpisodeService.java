package com.cmanager.app.application.service;

import com.cmanager.app.application.data.EpisodeAverageDTO;
import com.cmanager.app.application.repository.EpisodeRepository;
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
                        r[1] != null
                                ? new BigDecimal(r[1].toString())
                                : BigDecimal.ZERO
                ))
                .toList();
    }
}
