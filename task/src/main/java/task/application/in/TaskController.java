package task.application.in;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import task.application.out.http.project.ProjectRequest;
import task.application.out.http.project.ProjectResponse;
import task.domain.TaskService;
import task.domain.out.TaskResponse;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        TaskResponse taskResponse = taskService.createTask(taskRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponse);
    }

}
