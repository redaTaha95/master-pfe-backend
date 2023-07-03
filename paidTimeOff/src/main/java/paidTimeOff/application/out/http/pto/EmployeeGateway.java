package paidTimeOff.application.out.http.pto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

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
    public List<EmployeeResponse> getAllEmployees() {
        return employeeClient.getAllEmployees()
                .collectList()
                .doOnSuccess(employeeResponse -> log.info("Employees {} found with success"))
                .doOnError(throwable -> log.error("Error while finding the employee !!"))
                .block();
    }
}
