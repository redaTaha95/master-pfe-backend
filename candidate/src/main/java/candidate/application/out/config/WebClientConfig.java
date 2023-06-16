package candidate.application.out.config;

import candidate.application.out.http.recruitment_demand.RecruitmentDemandClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder recruitmentDemandWebClient() {
        return WebClient.builder().baseUrl(RecruitmentDemandClient.BASE_URL);
    }
}
