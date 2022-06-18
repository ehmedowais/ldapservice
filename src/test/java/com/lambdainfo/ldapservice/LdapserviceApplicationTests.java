package com.lambdainfo.ldapservice;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.lambdainfo.ldapservice.domain.Tasks;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations="classpath:application-test.properties")
@ActiveProfiles("test")
@Profile("test")
@Slf4j
//@WireMockTest(httpPort = 8888)
class LdapserviceApplicationTests {
	@Value("${some.test.value}")
	private String someValue;

	@LocalServerPort
	int port;
	@Autowired
	TestRestTemplate client;
	@Test
	void contextLoads() throws URISyntaxException {
		log.info("test value = "+ someValue);
		var uri = new URI("http://localhost:"+port+"/greeting/");

		var response = client.getForEntity(uri,String.class);
		log.info(response.getStatusCode().toString());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	@RegisterExtension
	static WireMockExtension wireMockServer = WireMockExtension.newInstance()
			.options(wireMockConfig().port(8888))
			.build();
	@Test
	void todo_tasks_int_test() throws Exception {

		wireMockServer.stubFor(
				WireMock.get("/task")
						.willReturn(aResponse()
								.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
								.withBody("{\n" +
										"    \"count\": 1,\n" +
										"    \"data\": [\n" +
										"        {\n" +
										"            \"completed\": false,\n" +
										"            \"_id\": \"62adb9721d95e40017e95fff\",\n" +
										"            \"description\": \"reading book\",\n" +
										"            \"owner\": \"62adb8221d95e40017e95fff\",\n" +
										"            \"createdAt\": \"2022-06-18T11:39:30.947Z\",\n" +
										"            \"updatedAt\": \"2022-06-18T11:39:30.947Z\",\n" +
										"            \"__v\": 0\n" +
										"        }\n" +
										"    ]\n" +
										"}"))
		);

		var uri = new URI("http://localhost:"+port+"/tasks/");

		var response = client.getForEntity(uri, Tasks.class);
		log.info(response.getStatusCode().toString());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		var task = response.getBody();
		assertEquals(1, task.getCount());
		assertEquals("62adb9721d95e40017e95fff",task.getTasks().get(0).get_id() );

	}

}
