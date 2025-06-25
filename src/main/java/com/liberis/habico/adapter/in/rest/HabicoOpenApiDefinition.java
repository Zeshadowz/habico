package com.liberis.habico.adapter.in.rest;

import com.liberis.habico.adapter.in.rest.dto.AddressRequest;
import com.liberis.habico.adapter.in.rest.dto.BuildingRequest;
import com.liberis.habico.adapter.in.rest.dto.UnitRequest;
import com.liberis.habico.domain.model.CStatus;
import com.liberis.habico.domain.model.SaleStatus;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.io.Resource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OpenAPIDefinition(
        security = {
                @SecurityRequirement(
                        name = "",
                        scopes = {"", ""}
                )
        },
        externalDocs = @ExternalDocumentation(
                url = "",
                description = ""
        )
)
@SecurityScheme(
        name = "",
        type = SecuritySchemeType.HTTP,
        description = "",
        bearerFormat = "",
        scheme = "",
        openIdConnectUrl = ""
)
@Slf4j
@Configuration
public class HabicoOpenApiDefinition {

    @Bean
    public OpenAPI openAPI(
            PropertyResolver propertyResolver,
            @Value("classpath:doc/openapi-description.txt")
            Resource descriptionTemplate
    ) {
        // OpenAPI-components can only be defined using this builder
        Map<String, Example> examples = createExamples();

        return new OpenAPI()
                .components(new Components().examples(examples))
                .info(new Info()
                        .title("Habico API")
                        .version(propertyResolver.getProperty("project.version"))
                        .description(propertyResolver.resolvePlaceholders(read(descriptionTemplate)))
                );
    }

    private Map<String, Example> createExamples() {
        Map<String, Example> examples = new HashMap<>();

        examples.put("example-create-full-building", new Example()
                .description("create building maximal")
                .value(exampleCreateBuilding()));
        return examples;
    }

    private BuildingRequest exampleCreateBuilding() {
        return BuildingRequest.builder()
                .name("Habico API")
                .code("HBCO")
                .status(CStatus.PENDING)
                .active(true)
                .address(AddressRequest.builder()
                        .street("Habico street")
                        .streetNumber("34G")
                        .zipcode("12345")
                        .city("Habico City")
                        .state("Habico State")
                        .country("Habico Country")
                        .build())
                .units(List.of(
                        UnitRequest.builder()
                                .designation("HBCO-A")
                                .status(SaleStatus.AVAILABLE)
                                .saleSart(LocalDate.now())
                                .parentAddress(true)
                                .build(),
                        UnitRequest.builder()
                                .designation("HBCO-B")
                                .status(SaleStatus.AVAILABLE)
                                .saleSart(LocalDate.now())
                                .parentAddress(false)
                                .address(AddressRequest.builder()
                                        .street("Habico-A street")
                                        .streetNumber("25")
                                        .zipcode("12345")
                                        .city("Habico City")
                                        .state("Habico State")
                                        .country("Habico Country")
                                        .build())
                                .build()
                ))
                .build();
    }

    private String read(Resource resource) {
        try (InputStream ins = resource.getInputStream();
             Reader reader = new InputStreamReader(ins, StandardCharsets.UTF_8)) {
            StringWriter writer = new StringWriter();
            reader.transferTo(writer);
            return writer.toString();
        } catch (IOException e) {
            log.warn("Error reading resource {} {}", resource.getFilename(), e.getMessage(), e);
            return "could not access documentation template " + e.getMessage();
        }
    }

    private <T> List<T> toList(T... t) {
        return Arrays.asList(t);
    }
}
