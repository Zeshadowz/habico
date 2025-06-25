package com.liberis.habico.integration;

import com.liberis.habico.TestDataProvider;
import com.liberis.habico.adapter.in.rest.dto.BuildingRequest;
import com.liberis.habico.adapter.in.rest.dto.BuildingResponse;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BuildingH2IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

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
    }
}
