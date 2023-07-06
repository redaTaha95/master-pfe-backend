package task.domain;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import task.application.in.TaskRequest;
import task.application.out.http.employee.EmployeeGateway;
import task.application.out.http.employee.EmployeeResponse;
import task.application.out.http.project.ProjectGateway;
import task.application.out.http.project.ProjectResponse;
import task.domain.out.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectGateway projectGateway;
    private final EmployeeGateway employeeGateway;

    @Transactional
    public TaskResponse createTask(TaskRequest taskRequest) {
        ProjectResponse project = projectGateway.getProject(taskRequest.getProjectId());
        EmployeeResponse employee = getEmployeeById(taskRequest.getEmployeeId());

        if (project != null )
        {
            if (employee != null ) {
                Task task = Task.builder()
                        .name(taskRequest.getName())
                        .description(taskRequest.getDescription())
                        .projectId(taskRequest.getProjectId())
                        .status(taskRequest.getStatus())
                        .employeeId(taskRequest.getEmployeeId())
                        .startDate(taskRequest.getStartDate())
                        .endDate(taskRequest.getEndDate())
                        .build();

                Task savedTask = taskRepository.save(task);

                return convertToResponse(savedTask, employee);
            }
            else {
             throw new EmployeeNotFoundException("employee not found");
            }
        }
        else {
            throw new ProjectNotFoundException("task not found");
        }
    }

    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskResponse> benifitResponses = new ArrayList<>();

        tasks.forEach(task -> {
            EmployeeResponse employee = employeeGateway.getEmployee(task.getEmployeeId());
            TaskResponse benifitResponse = convertToResponse(task, employee);
            benifitResponses.add(benifitResponse);
        });
        return benifitResponses;
    }

    public TaskResponse getTaskById(Long id) {
        Task task = getTaskByIdIfExists(id);
        EmployeeResponse employee = employeeGateway.getEmployee(task.getEmployeeId());
        if (employee != null) {
            return convertToResponse(task, employee);
        }
        else {
            throw new EmployeeNotFoundException("employee not found");
        }
    }

    public List<TaskResponse> getTasksByProjectId(Long id) {
        List<Task> tasks = taskRepository.findByProjectId(id);
        List<TaskResponse> taskResponses = new ArrayList<>();

        tasks.forEach(task -> {
            EmployeeResponse employee = getEmployeeById(task.getEmployeeId());
            TaskResponse taskResponse = convertToResponse(task, employee);
            taskResponses.add(taskResponse);
        });

        return taskResponses;
    }
    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {

        ProjectResponse project = projectGateway.getProject(taskRequest.getProjectId());
        EmployeeResponse employee = employeeGateway.getEmployee(taskRequest.getEmployeeId());
        if (project != null)
        {
            if (employee != null) {
                Task task = getTaskByIdIfExists(id);
                updateTaskFromRequest(task, taskRequest);
                taskRepository.save(task);
                return convertToResponse(task, employee);
            }
            else {
                throw new EmployeeNotFoundException("employee not found");
            }
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
        task.setStatus(taskRequest.getStatus());
        task.setStartDate(taskRequest.getStartDate());
        task.setEndDate(taskRequest.getEndDate());
    }


    private EmployeeResponse getEmployeeById(Long employeeId) {
        return employeeGateway.getEmployee(employeeId);
    }

    private Task getTaskByIdIfExists(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    private TaskResponse convertToResponse(Task task, EmployeeResponse employeeResponse) {

        return TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .projectId(task.getProjectId())
                .status(task.getStatus())
                .employee(employeeResponse)
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .build();
    }


}
