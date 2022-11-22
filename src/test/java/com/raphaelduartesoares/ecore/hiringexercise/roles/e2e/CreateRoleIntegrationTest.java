package com.raphaelduartesoares.ecore.hiringexercise.roles.e2e;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.RepositoryRole;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.RepositoryException;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("tests-e2e")
public class CreateRoleIntegrationTest {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private RepositoryRole repositoryRole;

    @AfterEach
    void deleteEntities() throws RepositoryException {
        repositoryRole.deleteAll();
    }

    @Test
    void shouldHaveThreePredefinedRoles() {
        webClient.post().uri("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"code\": \"dev\",\"name\": \"User Experience\",\"isDefault\":false}")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT);

        webClient.post().uri("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"code\": \"qa\",\"name\": \"User Experience\",\"isDefault\":false}")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT);

        webClient.post().uri("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"code\": \"po\",\"name\": \"User Experience\",\"isDefault\":false}")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void shouldCreateRoleSuccessfully() {
        webClient.post().uri("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"code\": \"ux\",\"name\": \"User Experience\",\"isDefault\":false}")
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void shouldReturnBadRequestWhenRoleCodeIsMissing() {
        webClient.post().uri("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"code\": \"\",\"name\": \"User Experience\",\"isDefault\":false}")
                .exchange()
                .expectStatus()
                .isBadRequest();

        webClient.post().uri("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"code\": \"   \",\"name\": \"User Experience\",\"isDefault\":false}")
                .exchange()
                .expectStatus()
                .isBadRequest();

        webClient.post().uri("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"code\": null,\"name\": \"User Experience\",\"isDefault\":false}")
                .exchange()
                .expectStatus()
                .isBadRequest();

        webClient.post().uri("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"name\": \"User Experience\",\"isDefault\":false}")
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void shouldReturnBadRequestWhenRoleNameIsMissing() {
        webClient.post().uri("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"code\": \"ux\",\"name\": \"\",\"isDefault\":false}")
                .exchange()
                .expectStatus()
                .isBadRequest();

        webClient.post().uri("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"code\": \"ux\",\"name\": \"    \",\"isDefault\":false}")
                .exchange()
                .expectStatus()
                .isBadRequest();

        webClient.post().uri("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"code\": \"ux\",\"name\": null,\"isDefault\":false}")
                .exchange()
                .expectStatus()
                .isBadRequest();

        webClient.post().uri("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"code\": \"ux\",\"isDefault\":false}")
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

}
