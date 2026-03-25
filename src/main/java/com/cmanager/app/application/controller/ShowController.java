package com.cmanager.app.application.controller;

import com.cmanager.app.application.data.ShowDTO;
import com.cmanager.app.application.domain.Show;
import com.cmanager.app.application.service.ShowService;
import com.cmanager.app.core.data.PageResultResponse;
import com.cmanager.app.core.utils.Util;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shows")
@Tag(
        name = "ShowController",
        description = "API de sincronizacao de shows"
)
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ShowDTO> sync(@RequestParam String name) {
        Show show = showService.syncShow(name);

        return ResponseEntity.ok(ShowDTO.convertEntity(show));
    }

    @GetMapping
    public ResponseEntity<PageResultResponse<ShowDTO>> list(
            @Parameter(description = "Nome do show para filtro", example = "game")
            @RequestParam(name = "name", required = false, defaultValue = "") String name,

            @Parameter(description = "Número da página (inicia em 0)", example = "0")
            @RequestParam(value = "page", defaultValue = "0") int page,

            @Parameter(description = "Quantidade de registros por página", example = "10")
            @RequestParam(value = "size", defaultValue = "10") int size,

            @Parameter(description = "Campo para ordenação", example = "name")
            @RequestParam(value = "sortField", defaultValue = "id") String sortField,

            @Parameter(description = "Direção da ordenação (ASC ou DESC)", example = "ASC")
            @RequestParam(value = "sortOrder", defaultValue = "ASC") String sortOrder
    ) {

        final var pageable = Util.getPageable(page, size, sortField, sortOrder);

        final var shows = showService.listShows(name, pageable);

        final var pageResponse = new PageImpl<>(
                shows.stream()
                        .map(ShowDTO::convertEntity)
                        .toList(),
                pageable,
                shows.getTotalElements()
        );

        return ResponseEntity.ok(PageResultResponse.from(pageResponse));
    }
}
