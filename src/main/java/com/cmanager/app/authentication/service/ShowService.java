package com.cmanager.app.authentication.service;

import com.cmanager.app.application.domain.Episode;
import com.cmanager.app.application.domain.Show;
import com.cmanager.app.authentication.domain.mapper.EpisodeMapper;
import com.cmanager.app.authentication.domain.mapper.ShowMapper;
import com.cmanager.app.authentication.repository.EpisodeRepository;
import com.cmanager.app.authentication.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ShowService {

    private final ShowRepository showRepository;
    private final EpisodeRepository episodeRepository;
    private final RestTemplate restTemplate;

    public ShowService(ShowRepository showRepository, EpisodeRepository episodeRepository, RestTemplate restTemplate) {
        this.showRepository = showRepository;
        this.episodeRepository = episodeRepository;
        this.restTemplate = restTemplate;
    }

    public Show syncShow(String name) {
        String url = "https://api.tvmaze.com/singlesearch/shows?q="
                + name + "&embed=episodes";

        Map response = restTemplate.getForObject(url, Map.class);

        Integer idIntegration = (Integer) response.get("id");

        Optional<Show> existing = showRepository.findByIdIntegration(idIntegration);
        if (existing.isPresent()) {
            return existing.get();
        }

        Show show = ShowMapper.mapToEntity(response);

        showRepository.save(show);

        Map embedded = (Map) response.get("_embedded");
        List<Map> episodes = (List<Map>) embedded.get("episodes");

        for (Map ep : episodes) {
            Integer epIntegrationId = ((Number) ep.get("id")).intValue();

            if (episodeRepository.existsByIdIntegration(epIntegrationId)) {
                continue;
            }
            Episode episode = EpisodeMapper.mapToEntity(ep, show);
            episodeRepository.save(episode);
        }

        return show;
    }

    public Page<Show> listShows(String name, Pageable pageable) {
        if (name != null && !name.isBlank()) {
            return showRepository.findByNameContainingIgnoreCase(name, pageable);
        }

        return showRepository.findAll(pageable);
    }
}
