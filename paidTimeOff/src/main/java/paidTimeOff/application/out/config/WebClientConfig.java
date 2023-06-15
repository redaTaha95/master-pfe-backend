package paidTimeOff.application.out.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import paidTimeOff.application.out.http.pto.EmployeeClient;


@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder userWebclient() {
        return WebClient.builder().baseUrl(EmployeeClient.BASE_URL);
    }
}
