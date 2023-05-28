package employee.application.out.http.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserGateway {
    private final UserClient userClient;

    public UserResponse createUser(UserRequest userRequest) {
        return userClient.createUser(userRequest)
                .doOnError(throwable -> log.error("Error while creating a user !!"))
                .doOnSuccess(userResponse -> log.info("User {} created with success", userResponse.getId()))
                .block();
    }
}
