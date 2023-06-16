package candidate.application.out.http.recruitment_demand;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RecruitmentDemandClient {

    public static final String BASE_URL = "http://recruitment-demand-service";
    private final WebClient webClient;


    public RecruitmentDemandClient(WebClient.Builder recruitmentDemandWebClient) {
        this.webClient = recruitmentDemandWebClient.build();
    }

    public Mono<RecruitmentDemandResponse> getRecruitmentDemand(Long id) {

        return webClient.get()
                .uri("/recruitment_demands/{id}", id)
                .retrieve()
                .bodyToMono(RecruitmentDemandResponse.class);
    }
}
