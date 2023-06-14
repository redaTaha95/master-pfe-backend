package task.application.out.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import task.application.out.http.project.ProjectClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder projectWebclient() {
        return WebClient.builder().baseUrl(ProjectClient.BASE_URL);
    }
}
