package com.cmanager.app.application.data.mapper;

import com.cmanager.app.application.domain.Show;

import java.math.BigDecimal;
import java.util.Map;

public class ShowMapper {

    public static Show mapToEntity(Map data) {

        Show show = new Show();

        show.setIdIntegration((Integer) data.get("id"));
        show.setName((String) data.get("name"));
        show.setType((String) data.get("type"));
        show.setLanguage((String) data.get("language"));
        show.setStatus((String) data.get("status"));
        show.setRuntime((Integer) data.get("runtime"));
        show.setAverageRuntime((Integer) data.get("averageRuntime"));
        show.setOfficialSite((String) data.get("officialSite"));
        show.setSummary((String) data.get("summary"));

        Map ratingMap = (Map) data.get("rating");
        if (ratingMap != null && ratingMap.get("average") != null) {
            show.setRating(
                    BigDecimal.valueOf(((Number) ratingMap.get("average")).doubleValue())
            );
        }

        return show;
    }
}