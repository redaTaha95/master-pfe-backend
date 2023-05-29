package employee.application.out;

import com.google.gson.Gson;
import employee.application.out.http.user.UserClient;
import employee.application.out.http.user.UserRequest;
import employee.application.out.http.user.UserResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

public class UserClientTest {
    private MockWebServer mockWebServer;
    private UserClient userClient;

    @BeforeEach
    public void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient.Builder userWebClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/users").toString());

        userClient = new UserClient(userWebClient);
    }

    @AfterEach
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("Should create a new user")
    public void shouldCreateANewUser() {
        UserResponse mockResponse = new UserResponse(1L, "john_doe", "john@example.com");
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(201)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(new Gson().toJson(mockResponse))
        );

        // Perform the actual method call
        UserRequest userRequest = new UserRequest("john_doe", "password", "john@example.com");
        Mono<UserResponse> createUserMono = userClient.createUser(userRequest);
        StepVerifier.create(createUserMono)
                .expectNext(mockResponse)
                .verifyComplete();
    }

}
