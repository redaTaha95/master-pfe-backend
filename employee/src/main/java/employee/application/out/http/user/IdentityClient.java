package employee.application.out.http.user;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class IdentityClient {
    public static final String BASE_URL = "http://identity-service";
    private final WebClient webClient;

    public IdentityClient(WebClient.Builder userWebclient) {
        this.webClient = userWebclient.build();
    }

    public Mono<UserResponse> createUser(UserRequest userRequest) {
        return webClient.post()
                .uri("/auth/register")
                .bodyValue(userRequest)
                .retrieve()
                .bodyToMono(UserResponse.class);
    }
}
