package benifit.application.out.config;

import benifit.application.out.http.employee.EmployeeClient;
import benifit.application.out.http.typeValidation.TypeValidationClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder employeeWebclient() {
        return WebClient.builder().baseUrl(EmployeeClient.BASE_URL);
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder typeValidationWebclient() { return WebClient.builder().baseUrl(TypeValidationClient.BASE_URL);
    }
}
