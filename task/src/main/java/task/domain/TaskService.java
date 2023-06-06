package task.domain;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import task.application.in.TaskRequest;
import task.application.out.http.project.ProjectGateway;
import task.application.out.http.project.ProjectResponse;
import task.domain.out.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse getTaskById(Long id) {
        Task task = getTaskByIdIfExists(id);
        return convertToResponse(task);
    }

    public List<TaskResponse>  getTasksByProjectId(Long id) {
            List<Task> tasks = taskRepository.findByProjectId(id);
            return tasks.stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());
    }

    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {

        ProjectResponse project = projectGateway.getProject(taskRequest.getProjectId());
        if (project != null)
        {
            Task task = getTaskByIdIfExists(id);
            updateTaskFromRequest(task, taskRequest);
            taskRepository.save(task);
            return convertToResponse(task);
        }
        else {
            throw new ProjectNotFoundException("Project not found");
        }

    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ProjectNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    private void updateTaskFromRequest(Task task, TaskRequest taskRequest) {
        task.setName(taskRequest.getName());
        task.setDescription(taskRequest.getDescription());
        task.setProjectId(taskRequest.getProjectId());
        task.setStartDate(taskRequest.getStartDate());
        task.setEndDate(taskRequest.getEndDate());
    }

    private Task getTaskByIdIfExists(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
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
