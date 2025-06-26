package com.liberis.habico.integration;

import com.liberis.habico.TestDataProvider;
import com.liberis.habico.adapter.in.rest.dto.BuildingRequest;
import com.liberis.habico.adapter.in.rest.dto.BuildingResponse;
import com.liberis.habico.adapter.in.rest.error.ErrorResponse;
import com.liberis.habico.app.usecase.CreateBuildingUsecase;
import com.liberis.habico.h2.LiquibaseTestExecutionListener;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestExecutionListeners(
        listeners = LiquibaseTestExecutionListener.class,
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
public class BuildingH2IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CreateBuildingUsecase buildingUsecase;


    @Test
    @Order(1)
    void shouldCreateBuilding() {
        BuildingRequest request = TestDataProvider.generateBuildingRequest();
        request.setAddress(TestDataProvider.generateAddressRequest());
        request.setUnits(List.of(
                TestDataProvider.generateUnitRequestWithParentAddress(),
                TestDataProvider.generateUnitRequestWithOwnAddress()
        ));


        ResponseEntity<BuildingResponse> response = restTemplate.exchange("/building", HttpMethod.POST, new HttpEntity<>(request), new ParameterizedTypeReference<>() {
        });

        //ResponseEntity<BuildingResponse> response = restTemplate.postForEntity("/building", request, BuildingResponse.class);

        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> {
                    assertThat(response.getBody()).isNotNull();
                    assertThat(response.getBody().getId()).isNotNull();
                    assertThat(response.getBody().getAddress()).isNotNull();
                    assertThat(response.getBody().getAddress().getId()).isNotNull();
                    assertThat(response.getBody().getCode()).isEqualTo(request.getCode());
                    assertThat(response.getBody().getUnits()).hasSize(2);
                    assertThat(response.getBody().getUnits()).anySatisfy(unit -> {
                        assertThat(unit).isNotNull();
                        assertThat(unit.getId()).isNotNull();
                    });
                }
        );

        ResponseEntity<ErrorResponse> conflict = restTemplate.postForEntity("/building", request, ErrorResponse.class);

        assertAll(
                () -> assertThat(conflict.getStatusCode()).isEqualTo(HttpStatus.CONFLICT)
        );
    }

    @Test
    @Order(2)
    void shouldReturnConflict() {
        // When
        BuildingRequest request = TestDataProvider.generateBuildingRequest();
        request.setAddress(TestDataProvider.generateAddressRequest());
        request.setUnits(List.of(
                TestDataProvider.generateUnitRequestWithParentAddress(),
                TestDataProvider.generateUnitRequestWithOwnAddress()
        ));

        buildingUsecase.createBuilding(request);

        // When
        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity("/building", request, ErrorResponse.class);

        // Then
        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT)
        );
    }
}
