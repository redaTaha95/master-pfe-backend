package task.application.out.http.project;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProjectGateway {
    private final ProjectClient projectClient;

    public ProjectResponse getProject(Long projectId) {
        return projectClient.getProject(projectId)
                .doOnSuccess(projectResponse -> log.info("Task {} created with success", projectResponse.getId()))
                .doOnError(throwable -> log.error("Error while creating a task !!"))
                .onErrorResume(throwable -> Mono.empty())

                .block();
    }
}