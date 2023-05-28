package employee.application.out.config;

import employee.application.out.http.user.UserClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder userWebclient() {
        return WebClient.builder().baseUrl(UserClient.BASE_URL);
    }
}
