package employee.application.out.http.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserClient {
    private final WebClient webClient;

    public UserClient(@Autowired WebClient.Builder webClient) {
        this.webClient = webClient.baseUrl("http://user-service").build();
    }

    public Mono<UserResponse> createUser(UserRequest userRequest) {
        return webClient.post()
                .uri("/users")
                .bodyValue(userRequest)
                .retrieve()
                .bodyToMono(UserResponse.class);
    }
}
