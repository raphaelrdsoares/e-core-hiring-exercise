package com.raphaelduartesoares.ecore.hiringexercise.roles.e2e;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("tests-e2e")
public class LookUpMembershipIntegrationTest {

    private static final String PATH_MEMBERSHIP = "/roles/membership";

    @Autowired
    private WebTestClient webClient;

    @Test
    void shouldReturnUnprocessableEntityErrorWhenLookUpMembershipWithoutPassAnyParameters() {
        webClient.get().uri(PATH_MEMBERSHIP)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    void shouldLookUpMembershipSuccessfullyWhenRequestOnlyHasRoleCode() {
        String lookUpPath = String.format("%s?roleCode=%s", PATH_MEMBERSHIP, "dev");
        webClient.get().uri(lookUpPath)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void shouldReturnBadRequestWhenRoleCodeIsEmpty() {
        String lookUpPath = String.format("%s?roleCode=%s", PATH_MEMBERSHIP, "");
        webClient.get().uri(lookUpPath)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    // TODO - deve dar erro ao buscar uma membership sem informar nenhum parametro
    // TODO - deve buscar todas as memberships com sucesso informando apenas a role
    // TODO - deve buscar todas as memberships com sucesso informando apenas o
    // teamId
    // TODO - deve buscar todas as memberships com sucesso informando apenas o
    // userId
    // TODO - deve buscar todas as memberships com sucesso informando apenas a role
    // e teamId
    // TODO - deve buscar todas as memberships com sucesso informando apenas a role
    // e userId
    // TODO - deve buscar todas as memberships com sucesso informando apenas o
    // teamId e userId
    // TODO - deve buscar todas as memberships com sucesso informando apenas a role,
    // teamId e userId
}
