package payslip.application.out.http.payroll;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class PayrollGateway {
    private final PayrollClient payrollClient;

    public PayrollResponse getPayroll(Long projectId) {
        return payrollClient.getPayroll(projectId)
                .doOnSuccess(payrollResponse -> log.info("Payslip {} created with success", payrollResponse.getId()))
                .doOnError(throwable -> log.error("Error while creating a task !!"))
                .onErrorResume(throwable -> Mono.empty())

                .block();
    }
}