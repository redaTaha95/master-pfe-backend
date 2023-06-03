package task.domain;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import task.application.in.TaskRequest;
import task.application.out.http.project.ProjectClient;
import task.application.out.http.project.ProjectGateway;
import task.application.out.http.project.ProjectResponse;
import task.domain.out.TaskRepository;
import task.domain.out.TaskResponse;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectGateway projectGateway;

    @Transactional
    public TaskResponse createTask(TaskRequest taskRequest) {
        ProjectResponse project = projectGateway.getProject(taskRequest.getProjectId());

        if (project != null)
        {
            Task task = Task.builder()
                    .name(taskRequest.getName())
                    .description(taskRequest.getDescription())
                    .projectId(taskRequest.getProjectId())
                    .startDate(taskRequest.getStartDate())
                    .endDate(taskRequest.getEndDate())
                    .build();

            Task savedTask = taskRepository.save(task);

            return  convertToResponse(savedTask);
        }
        else {
            throw new ProjectNotFoundException("project not found");
        }
    }

    private TaskResponse convertToResponse(Task task) {

        return TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .projectId(task.getProjectId())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .build();
    }


}
