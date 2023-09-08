package com.sonng2k.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

// As well as mocking the HTTP request cycle, you can also use Spring Boot to write a simple full-stack
// integration test. For example, instead of (or as well as) the mock test shown earlier, we could create this
// integration test:

// The embedded server starts on a random port because of webEnvironment = SpringBootTest.WebEnvironment
// .RANDOM_PORT, and the actual port is configured automatically in the base URL for the TestRestTemplate.

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerIT {
    @Autowired
    private TestRestTemplate template;

    @Test
    public void getHello() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/", String.class);
        assertThat(response.getBody()).isEqualTo("Greetings from Spring Boot!");
    }
}