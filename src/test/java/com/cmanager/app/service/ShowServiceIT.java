package com.cmanager.app.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.cmanager.app.application.domain.Show;
import com.cmanager.app.application.repository.ShowRepository;
import com.cmanager.app.application.service.ShowService;
import com.cmanager.app.core.utils.Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
public class ShowServiceIT {
    @Autowired
    ShowService service;

    @Autowired
    ShowRepository repository;

    @Test
    @DisplayName("syncShow() deve salvar show e evitar duplicados")
    void sync_show_ok_and_no_duplicate() {

        Show show1 = service.syncShow("game of thrones");

        assertThat(show1.getId()).isNotNull();
        assertThat(show1.getName()).isNotBlank();

        long countBefore = repository.count();

        Show show2 = service.syncShow("game of thrones");

        long countAfter = repository.count();

        assertThat(countAfter).isEqualTo(countBefore);
        assertThat(show2.getId()).isEqualTo(show1.getId());
    }

    @Test
    @DisplayName("listShows() deve filtrar por nome com paginação")
    void list_shows_filtered_and_paged() {

        service.syncShow("game of thrones");
        service.syncShow("friends");

        var pageable = Util.getPageable(0, 10, "name", "ASC");

        var page = service.listShows("game", pageable);

        assertThat(page.getTotalElements()).isGreaterThan(0);
        assertThat(page.getContent())
                .extracting(Show::getName)
                .allMatch(name -> name.toLowerCase().contains("game"));
    }

    @Test
    @DisplayName("listShows() deve retornar vazio quando não encontrar")
    void list_shows_empty() {

        var pageable = Util.getPageable(0, 10, "name", "ASC");

        var page = service.listShows("naoexiste123", pageable);

        assertThat(page.getContent()).isEmpty();
    }
}
