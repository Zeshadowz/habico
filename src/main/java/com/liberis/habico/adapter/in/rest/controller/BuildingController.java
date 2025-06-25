package com.liberis.habico.adapter.in.rest.controller;

import com.liberis.habico.adapter.in.rest.dto.BuildingRequest;
import com.liberis.habico.adapter.in.rest.dto.BuildingResponse;
import com.liberis.habico.adapter.in.rest.error.ErrorResponse;
import com.liberis.habico.app.usecase.CreateBuildingUsecase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/building")
public class BuildingController {

    private final CreateBuildingUsecase createBuildingUsecase;

    @PostMapping
    @Operation(
            summary = "Create a building project",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(
                                    name = "Complete Building",
                                    ref = "#/components/examples/example-create-full-building"
                            )
                    }
                    )
            )
    )
    public ResponseEntity<BuildingResponse> createBuilding(@RequestBody @Valid BuildingRequest building) {
        log.info("Building created {}", building);
        BuildingResponse buildingResponse = createBuildingUsecase.createBuilding(building);
        return ResponseEntity.status(HttpStatus.CREATED).body(buildingResponse);
    }

    @Operation(description = "Liefert eine Building", summary = "Basis information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "ENTITY_NOT_FOUND",
                                            ref = "#/components/examples/example-create-full-building")
                            })),
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    public BuildingResponse getBuilding(@Parameter(required = true, example = "635N837364", description = "")
                                        @PathVariable String id) {
        log.info("Building getting {}", id);
        return new BuildingResponse();
    }
}
