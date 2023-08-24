package com.chromascan.api;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;

import com.chromascan.api.storage.StorageService;
import com.chromascan.model.ColourBreakdown;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileUploadControllerTest {
    
	@Autowired
	private MockMvc mvc;
	@Autowired
	private TestRestTemplate restTemplate;
    
	@MockBean
	private StorageService storageService;
    
	@LocalServerPort
	private int port;
    
    @Test
	public void successUploadFile() throws Exception {
		String url = "/api/file/upload";
		ClassPathResource resource = new ClassPathResource("test-img1.png", getClass());

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("file", resource);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);

		ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
		then(storageService).should().store(any(MultipartFile.class));
	}

    @Test
	public void successGetDominantColour() throws Exception {
		String url = "/api/file/{filename}";
		ClassPathResource resource = new ClassPathResource("test-img1.png", getClass());
		given(this.storageService.loadAsResource("test-img1.png")).willReturn(resource);

		ResponseEntity<String> response = this.restTemplate
			.getForEntity(url, String.class, "test-img1.png");

		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response.getBody());
		JsonNode rgb = root.path("rgb");
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION))
			.isEqualTo("attachment; filename=\"test-img1.png\"");
        assertAll(
            () -> assertEquals("#1E1F1C", root.path("hex").asText()),
            () -> assertEquals(30, rgb.get("red").asInt()),
            () -> assertEquals(31, rgb.get("green").asInt()),
            () -> assertEquals(28, rgb.get("blue").asInt()),
            () -> assertEquals(100.0, root.path("percentage").asDouble()),
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
