package com.liberis.habico.integration;

import com.liberis.habico.S3TestExecutionListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestExecutionListeners(
        listeners = S3TestExecutionListener.class,
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
public class DocumentIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void uploadDocument_ShouldReturnSuccessMessage() {
        String key = "another.txt";
        byte[] content = "Hello from S3Mock!".getBytes(StandardCharsets.UTF_8);

        ByteArrayResource resource = new ByteArrayResource(content) {
            @Override
            public String getFilename() {
                return key;
            }
        };

        // create ContentDisposition for the file part
        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("file")
                .filename(key)
                .build();

        // Create HttpHeaders for the file part
        HttpHeaders fileHeaders = new HttpHeaders();
        fileHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        fileHeaders.setContentDisposition(contentDisposition);

        // Create HttpEntity for the file part
        HttpEntity<ByteArrayResource> filePart = new HttpEntity<>(resource, fileHeaders);

        // Create the overall body for the multipart request
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", filePart);

        // Create headers for the main request (important: RestTemplate will set the Content-Type to multipart/form-data itself)
        HttpHeaders requestHeaders = new HttpHeaders();
        // Do NOT set Content-Type to MediaType.MULTIPART_FORM_DATA here. RestTemplate will handle it.
        // If you set it, you might override what RestTemplate wants to do internally,
        // and it might cause issues with boundary generation.

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, requestHeaders);

        ResponseEntity<String> response = restTemplate.exchange("/documents/upload", HttpMethod.POST, requestEntity, String.class);

        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> {
                    assertThat(response.getBody()).isNotNull();
                    assertThat(response.getBody()).isEqualTo(key);
                }
        );
    }
}
