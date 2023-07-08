package task.application.out.http.project;


import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProjectClient {
    public static final String BASE_URL = "http://project-service";
    private final WebClient webClient;

    public ProjectClient(WebClient.Builder projectWebclient) {
        this.webClient = projectWebclient.build();
    }

    public Mono<ProjectResponse> getProject(Long id) {

        return webClient.get()
                .uri("/projects/{id}", id)
                .retrieve()
                .bodyToMono(ProjectResponse.class);
    }
}
