package com.raphaelduartesoares.ecore.hiringexercise.roles.e2e;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.RepositoryRole;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.seed.ServiceSeed;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.RepositoryException;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("tests-e2e")
public class AssignRoleIntegrationTest {

    private static final String PATH_MEMBERSHIP = "/roles/membership";

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private RepositoryRole repositoryRole;

    @Autowired
    private ServiceSeed serviceSeed;

    @BeforeEach
    void setup() throws Exception {
        serviceSeed.seedRoles();
    }

    @ParameterizedTest
    @CsvSource({ "'\"teamId\":\"\",'", "'\"teamId\":\"    \",'", "'\"teamId\":null,'", "''" })
    void shouldReturnBadRequestWhenTeamIdIsMissing(String propertyTeamId) {
        String requestBody = String
                .format("{\"roleCode\": \"dev\",%s\"userId\":\"8c35f835-3d03-4f30-9233-67cf1f9b2662\"}",
                        propertyTeamId);
        webClient.post().uri(PATH_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @ParameterizedTest
    @CsvSource({ "'\"userId\":\"\"'", "'\"userId\":\"    \"'", "'\"userId\":null'", "''" })
    void shouldReturnBadRequestWhenUserIdIsMissing(String propertyUserId) {
        String requestBody = String
                .format("{\"roleCode\": \"dev\",\"teamId\":\"5071b4fc-43f2-47a2-8403-e934dc270606\",%s}",
                        propertyUserId);
        webClient.post().uri(PATH_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void shouldReturnNotFoundWhenRoleDoesNotExists() {
        String requestBody = "{\"roleCode\": \"deva\",\"teamId\":\"5071b4fc-43f2-47a2-8403-e934dc270606\",\"userId\":\"8c35f835-3d03-4f30-9233-67cf1f9b2662\"}";
        webClient.post().uri(PATH_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void shouldReturnNotFoundWhenRoleCodeIsMissingAndDefaultRoleDoesNotExists() throws RepositoryException {
        repositoryRole.deleteAll();
        String requestBody = "{\"teamId\":\"5071b4fc-43f2-47a2-8403-e934dc270606\",\"userId\":\"8c35f835-3d03-4f30-9233-67cf1f9b2662\"}";
        webClient.post().uri(PATH_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void shouldReturnNotFoundWhenUserIsNotInTeam() throws RepositoryException {
        String requestBody = "{\"roleCode\": \"dev\",\"teamId\":\"5071b4fc-43f2-47a2-8403-e934dc270606\",\"userId\":\"35be0fac-e803-5a93-a7c3-fa04023783e1\"}";
        webClient.post().uri(PATH_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void shouldAssignRoleSuccessfully() throws RepositoryException {
        String requestBody = "{\"roleCode\": \"dev\",\"teamId\":\"5071b4fc-43f2-47a2-8403-e934dc270606\",\"userId\":\"8c35f835-3d03-4f30-9233-67cf1f9b2662\"}";
        webClient.post().uri(PATH_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void shouldAssignRoleSuccessfullyWhenUserIsTeamLead() throws RepositoryException {
        String requestBody = "{\"roleCode\": \"dev\",\"teamId\":\"5071b4fc-43f2-47a2-8403-e934dc270606\",\"userId\":\"7ca5f476-4beb-4aae-9a50-2ac5c78be9e9\"}";
        webClient.post().uri(PATH_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void shouldAssignRoleSuccessfullyWhenRoleCodeIsMissingAndDefaultRoleExists() throws RepositoryException {
        String requestBody = "{\"teamId\":\"5071b4fc-43f2-47a2-8403-e934dc270606\",\"userId\":\"8c35f835-3d03-4f30-9233-67cf1f9b2662\"}";
        webClient.post().uri(PATH_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void shouldOverwriteAssignRoleSuccessfully() throws RepositoryException {
        String requestBody = "{\"roleCode\": \"dev\",\"teamId\":\"5071b4fc-43f2-47a2-8403-e934dc270606\",\"userId\":\"8c35f835-3d03-4f30-9233-67cf1f9b2662\"}";
        webClient.post().uri(PATH_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isCreated();
        requestBody = "{\"roleCode\": \"qa\",\"teamId\":\"5071b4fc-43f2-47a2-8403-e934dc270606\",\"userId\":\"8c35f835-3d03-4f30-9233-67cf1f9b2662\"}";
        webClient.post().uri(PATH_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isCreated();
    }

}
