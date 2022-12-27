package com.raphaelduartesoares.ecore.hiringexercise.roles.e2e;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.RepositoryMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.services.roles.infrastructure.repositories.entities.EntityMembership;
import com.raphaelduartesoares.ecore.hiringexercise.roles.shared.exceptions.RepositoryException;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("tests-e2e")
public class LookUpMembershipIntegrationTest {

    private static final String PATH_MEMBERSHIP = "/roles/memberships";

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private RepositoryMembership repositoryMembership;

    @BeforeEach
    void setup() throws Exception {
        repositoryMembership.deleteAll();
    }

    @Test
    void shouldReturnUnprocessableEntityErrorWhenLookUpMembershipWithoutPassAnyParameters() {
        webClient.get().uri(PATH_MEMBERSHIP)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    void shouldReturnBadRequestWhenRoleCodeIsEmpty() {
        String lookUpPath = String.format("%s?roleCode=%s", PATH_MEMBERSHIP, "");
        webClient.get().uri(lookUpPath)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void shouldReturnBadRequestWhenTeamIdIsEmpty() {
        String lookUpPath = String.format("%s?teamId=%s", PATH_MEMBERSHIP, "");
        webClient.get().uri(lookUpPath)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void shouldReturnBadRequestWhenUserIdIsEmpty() {
        String lookUpPath = String.format("%s?userId=%s", PATH_MEMBERSHIP, "");
        webClient.get().uri(lookUpPath)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void shouldLookUpMembershipSuccessfullyWhenRequestOnlyHasRoleCode() throws RepositoryException {

        String userId = UUID.randomUUID().toString();
        String teamId = UUID.randomUUID().toString();
        repositoryMembership
                .save(new EntityMembership("dev", userId, teamId));
        repositoryMembership
                .save(new EntityMembership("qa", userId, teamId));

        String lookUpPath = String.format("%s?roleCode=%s", PATH_MEMBERSHIP, "dev");
        webClient.get().uri(lookUpPath)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(String.format("[{\"roleCode\": \"dev\",\"userId\": \"%s\",\"teamId\": \"%s\"}]", userId, teamId));
    }

    @Test
    void shouldLookUpMembershipSuccessfullyWhenRequestOnlyHasTeamId() throws RepositoryException {
        String userId = UUID.randomUUID().toString();
        String teamId = UUID.randomUUID().toString();
        repositoryMembership
                .save(new EntityMembership("dev", userId, teamId));
        repositoryMembership
                .save(new EntityMembership("dev", userId, UUID.randomUUID().toString()));

        String lookUpPath = String.format("%s?teamId=%s", PATH_MEMBERSHIP, teamId);
        webClient.get().uri(lookUpPath)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(String.format("[{\"roleCode\": \"dev\",\"userId\": \"%s\",\"teamId\": \"%s\"}]", userId, teamId));
    }

    @Test
    void shouldLookUpMembershipSuccessfullyWhenRequestOnlyHasUserId() throws RepositoryException {
        String userId = UUID.randomUUID().toString();
        String teamId = UUID.randomUUID().toString();
        repositoryMembership
                .save(new EntityMembership("dev", userId, teamId));
        repositoryMembership
                .save(new EntityMembership("dev", UUID.randomUUID().toString(), teamId));

        String lookUpPath = String.format("%s?userId=%s", PATH_MEMBERSHIP, userId);
        webClient.get().uri(lookUpPath)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(String.format("[{\"roleCode\": \"dev\",\"userId\": \"%s\",\"teamId\": \"%s\"}]", userId, teamId));
    }

    @Test
    void shouldLookUpMembershipSuccessfullyWhenRequestOnlyHasRoleCodeAndTeamId() throws RepositoryException {
        String userId = UUID.randomUUID().toString();
        String teamId = UUID.randomUUID().toString();
        String roleCode = "dev";
        repositoryMembership
                .save(new EntityMembership(roleCode, userId, teamId));
        repositoryMembership
                .save(new EntityMembership(roleCode, userId, UUID.randomUUID().toString()));
        repositoryMembership
                .save(new EntityMembership("qa", userId, teamId));

        String lookUpPath = String.format("%s?roleCode=%s&teamId=%s", PATH_MEMBERSHIP, roleCode, teamId);
        webClient.get().uri(lookUpPath)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(String.format("[{\"roleCode\": \"dev\",\"userId\": \"%s\",\"teamId\": \"%s\"}]", userId, teamId));
    }

    @Test
    void shouldLookUpMembershipSuccessfullyWhenRequestOnlyHasRoleCodeAndUserId() throws RepositoryException {
        String userId = UUID.randomUUID().toString();
        String teamId = UUID.randomUUID().toString();
        String roleCode = "dev";
        repositoryMembership
                .save(new EntityMembership(roleCode, userId, teamId));
        repositoryMembership
                .save(new EntityMembership("qa", userId, teamId));
        repositoryMembership
                .save(new EntityMembership(roleCode, UUID.randomUUID().toString(), teamId));

        String lookUpPath = String.format("%s?roleCode=%s&userId=%s", PATH_MEMBERSHIP, roleCode, userId);
        webClient.get().uri(lookUpPath)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(String.format("[{\"roleCode\": \"dev\",\"userId\": \"%s\",\"teamId\": \"%s\"}]", userId, teamId));
    }

    @Test
    void shouldLookUpMembershipSuccessfullyWhenRequestOnlyHasTeamIdAndUserId() throws RepositoryException {
        String userId = UUID.randomUUID().toString();
        String teamId = UUID.randomUUID().toString();
        String roleCode = "dev";
        repositoryMembership
                .save(new EntityMembership(roleCode, userId, teamId));
        repositoryMembership
                .save(new EntityMembership(roleCode, userId, UUID.randomUUID().toString()));
        repositoryMembership
                .save(new EntityMembership(roleCode, UUID.randomUUID().toString(), teamId));

        String lookUpPath = String.format("%s?teamId=%s&userId=%s", PATH_MEMBERSHIP, teamId, userId);
        webClient.get().uri(lookUpPath)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(String.format("[{\"roleCode\": \"dev\",\"userId\": \"%s\",\"teamId\": \"%s\"}]", userId, teamId));
    }

    @Test
    void shouldLookUpMembershipSuccessfullyWhenRequestHasRoleCodeAndTeamIdAndUserId() throws RepositoryException {
        String userId = UUID.randomUUID().toString();
        String teamId = UUID.randomUUID().toString();
        String roleCode = "dev";
        repositoryMembership
                .save(new EntityMembership(roleCode, userId, teamId));
        repositoryMembership
                .save(new EntityMembership("qa", userId, teamId));
        repositoryMembership
                .save(new EntityMembership(roleCode, userId, UUID.randomUUID().toString()));
        repositoryMembership
                .save(new EntityMembership(roleCode, UUID.randomUUID().toString(), teamId));

        String lookUpPath = String.format("%s?roleCode=%s&teamId=%s&userId=%s", PATH_MEMBERSHIP, roleCode, teamId,
                userId);
        webClient.get().uri(lookUpPath)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(String.format("[{\"roleCode\": \"dev\",\"userId\": \"%s\",\"teamId\": \"%s\"}]", userId, teamId));
    }

}
