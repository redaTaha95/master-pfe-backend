package interview.application.out.http.candidate;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CandidateClient {

    public static final String BASE_URL = "http://candidate-service";
    private final WebClient webClient;

    public CandidateClient(WebClient.Builder candidateWebClient) {
        this.webClient = candidateWebClient.build();
    }

    public Mono<CandidateResponse> getCandidate(Long id) {

        return webClient.get()
                .uri("/candidates/{id}", id)
                .retrieve()
                .bodyToMono(CandidateResponse.class);
    }
}
