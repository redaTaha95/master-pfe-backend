package interview.application.out.config;

import interview.application.out.http.candidate.CandidateClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder candidateWebClient() {

        return WebClient.builder().baseUrl(CandidateClient.BASE_URL);
    }
}
