package task.application.out;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import task.application.in.TaskRequest;
import task.application.out.http.project.ProjectGateway;
import task.domain.Task;
import task.domain.TaskService;
import task.domain.out.TaskRepository;
import task.domain.TaskResponse;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;
    @InjectMocks
    private ProjectGateway projectGateway;

    @Test
    @DisplayName("Should save a task")
    public void shouldSaveATask() {
        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("Yassine");
        taskRequest.setDescription("Haddaj");
        taskRequest.setProjectId(1L);
        taskRequest.setStartDate(startDate);
        taskRequest.setEndDate(endDate);

        Task savedTask = Task.builder()
                .name(taskRequest.getName())
                .description(taskRequest.getDescription())
                .projectId(taskRequest.getProjectId())
                .startDate(taskRequest.getStartDate())
                .endDate(taskRequest.getEndDate())
                .build();

        Mockito.when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskResponse task = taskService.createTask(taskRequest);

        Assertions.assertEquals(task.getName(), taskRequest.getName());
    }

    @Test
    @DisplayName("Should return all tasks")
    public void shouldReturnAllTasks() {
        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();

        startDateCalendar.set(2024, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate2 = startDateCalendar.getTime();

        startDateCalendar.set(2024, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate2 = startDateCalendar.getTime();

        List<Task> tasks = Arrays.asList(
                new Task(1L,"P1", "D1",1L, startDate, endDate),
                new Task(2L,"P2", "D2",1L, startDate2, endDate2)
        );
        Mockito.when(taskRepository.findAll()).thenReturn(tasks);

        List<TaskResponse> taskResponses = taskService.getAllTasks();

        Assertions.assertEquals(tasks.size(), taskResponses.size());
    }

    @Test
    @DisplayName("Should return task by ID")
    public void shouldReturnTaskById() {
        Long TaskId = 1L;
        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();

        Task task = new Task(1L,"P1", "D1",1L, startDate, endDate);
        Mockito.when(taskRepository.findById(TaskId)).thenReturn(Optional.of(task));

        TaskResponse taskResponse = taskService.getTaskById(TaskId);

        Assertions.assertEquals(task.getId(), taskResponse.getId());
        Assertions.assertEquals(task.getName(), taskResponse.getName());
        Assertions.assertEquals(task.getDescription(), taskResponse.getDescription());
        Assertions.assertEquals(task.getProjectId(), taskResponse.getProjectId());
        Assertions.assertEquals(task.getStartDate(), taskResponse.getStartDate());
        Assertions.assertEquals(task.getEndDate(), taskResponse.getEndDate());
    }

    @Test
    @DisplayName("Should update task")
    public void shouldUpdateTask() {
        Long taskId = 1L;
        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date oldStartDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date oldEndDate = startDateCalendar.getTime();

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("P1");
        taskRequest.setDescription("D1");
        taskRequest.setProjectId(1L);
        taskRequest.setStartDate(startDate);
        taskRequest.setEndDate(endDate);

        Task existingTask = new Task(taskId, "old P1", "old D1",1L,oldStartDate,oldEndDate);
        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));

        TaskResponse updatedTaskResponse = taskService.updateTask(taskId, taskRequest);

        Assertions.assertEquals(existingTask.getId(), updatedTaskResponse.getId());
        Assertions.assertEquals(taskRequest.getName(), updatedTaskResponse.getName());
        Assertions.assertEquals(taskRequest.getDescription(), updatedTaskResponse.getDescription());
        Assertions.assertEquals(taskRequest.getProjectId(), updatedTaskResponse.getProjectId());
        Assertions.assertEquals(taskRequest.getStartDate(), updatedTaskResponse.getStartDate());
        Assertions.assertEquals(taskRequest.getEndDate(), updatedTaskResponse.getEndDate());
    }

    @Test
    @DisplayName("Should delete task")
    public void shouldDeleteTask() {
        Long taskId = 1L;
        Mockito.when(taskRepository.existsById(taskId)).thenReturn(true);

        taskService.deleteTask(taskId);

        Mockito.verify(taskRepository).deleteById(taskId);
    }


}
