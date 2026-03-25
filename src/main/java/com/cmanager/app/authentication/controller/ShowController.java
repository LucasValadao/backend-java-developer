package com.cmanager.app.authentication.controller;

import com.cmanager.app.authentication.data.UserCreateRequest;
import com.cmanager.app.authentication.data.UserDTO;
import com.cmanager.app.authentication.data.UserUpdateRequest;
import com.cmanager.app.authentication.service.ShowService;
import com.cmanager.app.authentication.service.UserService;
import com.cmanager.app.core.data.PageResultResponse;
import com.cmanager.app.core.utils.Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
    public ResponseEntity<Void> sync(@RequestParam String name) {
        showService.syncShow(name);

        return ResponseEntity.ok().build();
    }
}
