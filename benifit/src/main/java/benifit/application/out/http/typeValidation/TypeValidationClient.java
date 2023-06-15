package benifit.application.out.http.typeValidation;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TypeValidationClient {

    public static final String BASE_URL = "http://typeValidation-service";
    private final WebClient webClient;

    public TypeValidationClient(WebClient.Builder typeValidationWebclient) {
        this.webClient = typeValidationWebclient.build();
    }

    public Mono<TypeValidationResponse> getTypeValidation(Long id) {

        return webClient.get()
                .uri("/typeValidations/{id}", id)
                .retrieve()
                .bodyToMono(TypeValidationResponse.class);
    }

}
