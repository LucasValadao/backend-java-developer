package com.cmanager.app.authentication.domain.mapper;

import com.cmanager.app.application.domain.Episode;
import com.cmanager.app.application.domain.Show;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Map;

public class EpisodeMapper {

    public static Episode mapToEntity(Map data, Show show) {

        Episode episode = new Episode();

        episode.setIdIntegration(((Number) data.get("id")).intValue());
        episode.setShow(show);

        episode.setName((String) data.get("name"));
        episode.setSeason((Integer) data.get("season"));
        episode.setNumber((Integer) data.get("number"));
        episode.setType((String) data.get("type"));

        if (data.get("airdate") != null) {
            episode.setAirdate(LocalDate.parse((String) data.get("airdate")));
        }

        if (data.get("airtime") != null) {
            episode.setAirtime(LocalTime.parse((String) data.get("airtime")));
        }

        if (data.get("airstamp") != null) {
            episode.setAirstamp(
                    OffsetDateTime.parse((String) data.get("airstamp"))
                            .toLocalDateTime()
            );
        }

        episode.setRuntime(
                data.get("runtime") != null
                        ? ((Number) data.get("runtime")).intValue()
                        : null
        );

        Map ratingMap = (Map) data.get("rating");
        if (ratingMap != null && ratingMap.get("average") != null) {
            episode.setRating(
                    BigDecimal.valueOf(((Number) ratingMap.get("average")).doubleValue())
            );
        }

        episode.setSummary((String) data.get("summary"));

        return episode;
    }
}
