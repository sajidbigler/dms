package com.cts.dmsauth.customsvc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cts.dmsauth.api.request.DocumentRequest;
import com.cts.dmsauth.customsvc.DmsBootApplication;
import com.cts.dmsauth.customsvc.service.DmsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)
@WebMvcTest(DmsController.class)
@EnableConfigurationProperties
@SpringJUnitConfig(classes = {DmsBootApplication.class})
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "local")
@PropertySource("classpath:/application.properties")
class DmsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private DmsController dmsauthController;

	@MockBean
	private DmsService dmsauthService;

	private ObjectMapper mapper = null;

	static Stream<Arguments> trueFalse(){
		return Stream.of(Arguments.of(true),Arguments.of(false));
	}

	@BeforeEach
	void setUp() {
		//ReflectionTestUtils.setField(dmsauthService,"userName","Test");
		mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	}

	@ParameterizedTest
            @MethodSource("trueFalse")
	void downloadDocumentTest(boolean trueFalse) throws Exception {
		DocumentRequest documentRequest = DocumentRequest.builder().docId(123L).build();
		//if(trueFalse)
		//ObjectWriter ow = mapper.writer();
		String requestJson = mapper.writeValueAsString(documentRequest);
		//RestTemplate restTemplate=mock(RestTemplate.class);
		//ResponseEntity<?> res=ResponseEntity.ok().body(null);
		//lenient().when(x).thenReturn();
		mockMvc.perform(MockMvcRequestBuilders.post("/dmsauthApi/document").content(requestJson)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
	}

	@Test
	void downloadAllDocumentsTest() throws Exception {
		mockMvc.perform(get("/dmsauthApi/documents").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

}
