package com.cmanager.app.application.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record EpisodeAverageDTO(

        @JsonProperty("season")
        @Schema(description = "Temporada")
        Integer season,

        @JsonProperty("average")
        @Schema(description = "Media de rating da temporada")
        BigDecimal average

) {}
