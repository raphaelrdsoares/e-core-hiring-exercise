package com.raphaelduartesoares.ecore.hiringexercise.roles.e2e;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    private static final String PATH_ROLES = "/roles";

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
        webClient.post().uri(PATH_ROLES)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"code\": \"dev\",\"name\": \"User Experience\",\"isDefault\":false}")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT);

        webClient.post().uri(PATH_ROLES)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"code\": \"qa\",\"name\": \"User Experience\",\"isDefault\":false}")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT);

        webClient.post().uri(PATH_ROLES)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"code\": \"po\",\"name\": \"User Experience\",\"isDefault\":false}")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void shouldCreateRoleSuccessfully() {
        webClient.post().uri(PATH_ROLES)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"code\": \"ux\",\"name\": \"User Experience\",\"isDefault\":false}")
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @ParameterizedTest
    @CsvSource({ "'\"code\":\"\",'", "'\"code\":\"    \",'", "'\"code\":null,'", "''" })
    void shouldReturnBadRequestWhenRoleCodeIsMissing(String propertyCode) {
        String requestBody = String.format("{%s\"name\": \"User Experience\",\"isDefault\":false}",
                propertyCode);
        webClient.post().uri(PATH_ROLES)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @ParameterizedTest
    @CsvSource({ "'\"name\":\"\",'", "'\"name\":\"    \",'", "'\"name\":null,'", "''" })
    void shouldReturnBadRequestWhenRoleNameIsMissing(String propertyName) {
        String requestBody = String.format(
                "{\"code\": \"ux\",%s\"isDefault\":false}",
                propertyName);

        webClient.post().uri(PATH_ROLES)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isBadRequest();

    }

}
