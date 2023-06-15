package payslip.application.out.http.payroll;


import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PayrollClient {
    public static final String BASE_URL = "http://payroll-service";
    private final WebClient webClient;

    public PayrollClient(WebClient.Builder projectWebclient) {
        this.webClient = projectWebclient.build();
    }

    public Mono<PayrollResponse> getPayroll(Long id) {

        return webClient.get()
                .uri("/payrolls/{id}", id)
                .retrieve()
                .bodyToMono(PayrollResponse.class);
    }
}
