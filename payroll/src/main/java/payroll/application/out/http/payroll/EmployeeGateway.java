package payroll.application.out.http.payroll;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmployeeGateway {

    private final EmployeeClient employeeClient;

    public EmployeeResponse getEmployee(Long employeeId) {
        return employeeClient.getEmployee(employeeId)
                .doOnSuccess(employeeResponse -> log.info("Employee {} found with success", employeeResponse.getId()))
                .doOnError(throwable -> log.error("Error while finding the employee !!"))
                .onErrorResume(throwable -> Mono.empty())
                .block();
    }
}
