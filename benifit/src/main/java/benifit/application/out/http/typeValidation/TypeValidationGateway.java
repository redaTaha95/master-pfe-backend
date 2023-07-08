package benifit.application.out.http.typeValidation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class TypeValidationGateway {

    private final TypeValidationClient employeeClient;

    public TypeValidationResponse getTypeValidation(Long employeeId) {
        return employeeClient.getTypeValidation(employeeId)
                .doOnSuccess(typeValidationResponse -> log.info("TypeValidation {} found with success", typeValidationResponse.getId()))
                .doOnError(throwable -> log.error("Error while finding the TypeValidation !!"))
                .onErrorResume(throwable -> Mono.empty())
                .block();
    }
}
