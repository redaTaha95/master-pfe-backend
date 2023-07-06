package task.application.out.http.employee;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class EmployeeClient {

    public static final String BASE_URL = "http://employee-service";
    private final WebClient webClient;

    public EmployeeClient(WebClient.Builder employeeWebclient) {
        this.webClient = employeeWebclient.build();
    }

    public Mono<EmployeeResponse> getEmployee(Long id) {

        return webClient.get()
                .uri("/employees/{id}", id)
                .retrieve()
                .bodyToMono(EmployeeResponse.class);
    }

}