package com.chromascan.api;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.time.Duration;

import com.chromascan.api.storage.StorageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Tag("Integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChromaScanControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
    
	@MockBean
	private StorageService storageService;
    
	@LocalServerPort
	private int port;
    
    @Test
	public void successUploadFile() throws Exception {
		// api endpoint
		String url = "/api/file/upload";

		// multi value map for image
		ClassPathResource resource = new ClassPathResource("test-img4.png", getClass());
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("file", resource);

		// HTTP request headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);

		// makes the request and the response is returned
		ResponseEntity<String> response = this.restTemplate
			.postForEntity(url, request, String.class);

		// assertions
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
		then(storageService).should().store(any(MultipartFile.class));
	}

    @Test
	public void successGetDominantColour() throws Exception {
		// assert that the resource exists in storage service
		ClassPathResource resource = new ClassPathResource("test-img4.png", getClass());
		given(this.storageService.loadAsResource("test-img4.png")).willReturn(resource);

		// api endpoint
		String url = "/api/file/{filename}/getDominantColour";
		ResponseEntity<String> response = this.restTemplate
			.getForEntity(url, String.class, "test-img4.png");

		// map response body to json object
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response.getBody());

		// assertions
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION))
			.isEqualTo("attachment; filename=\"test-img4.png\"");
        assertAll(
			() -> assertNotNull(root.path("rgb")),
            () -> assertNotNull(root.path("hex")),
            () -> assertNotNull(root.path("percentage")),
            () -> assertNotNull(root.path("name"))      
        );
	}

	@Test
	public void successDataPointsDominantColour() throws Exception {
		// assert that the resource exists in storage service
		ClassPathResource resource = new ClassPathResource("test-img5.png", getClass());
		given(this.storageService.loadAsResource("test-img5.png")).willReturn(resource);

		// api endpoint
		String url = "/api/file/{filename}/{datapoints}/getDominantColour";
		String datapoints = "10, 500, 358, 285, 600, 10";
		ResponseEntity<String> response = this.restTemplate
			.getForEntity(url, String.class, "test-img5.png", datapoints);

		// map response body to json object
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response.getBody());

		// assertions
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION))
			.isEqualTo("attachment; filename=\"test-img5.png\"");
        assertAll(
			() -> assertNotNull(root.path("rgb")),
            () -> assertNotNull(root.path("hex")),
            () -> assertNotNull(root.path("percentage")),
            () -> assertNotNull(root.path("name"))
        );
	}
	
    @Test
	public void successGetColourMix() throws Exception {
		// assert that the resource exists in storage service
		ClassPathResource resource = new ClassPathResource("test-img4.png", getClass());
		given(this.storageService.loadAsResource("test-img4.png")).willReturn(resource);

		// api endpoint
		String url = "/api/file/{filename}/getColourMix";
		ResponseEntity<String> response = this.restTemplate
			.getForEntity(url, String.class, "test-img4.png");

		// map response body to json object
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response.getBody());

		// assertions
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION))
			.isEqualTo("attachment; filename=\"test-img4.png\"");
        assertAll(
			() -> assertNotNull(root.path("rgb")),
            () -> assertNotNull(root.path("hex")),
            () -> assertNotNull(root.path("percentage")),
            () -> assertNotNull(root.path("name"))      
        );
	}

    @TestConfiguration(proxyBeanMethods = false)
    static class RestTemplateBuilderConfiguration {

        @Bean
        RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().setConnectTimeout(Duration.ofMinutes(5))
                .setReadTimeout(Duration.ofMinutes(5));
        }

    }
}
