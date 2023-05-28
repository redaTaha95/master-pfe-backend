package employee.application.out.http.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserClient {
    public static final String BASE_URL = "http://user-service";
    private final WebClient webClient;

    public UserClient(WebClient.Builder userWebclient) {
        this.webClient = userWebclient.build();
    }

    public Mono<UserResponse> createUser(UserRequest userRequest) {
        return webClient.post()
                .uri("/users")
                .bodyValue(userRequest)
                .retrieve()
                .bodyToMono(UserResponse.class);
    }
}
