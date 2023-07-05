package task.application.in;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import task.domain.TaskService;
import task.domain.TaskResponse;
import task.domain.TaskStatus;

import java.util.*;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    public void shouldReturnAllTask() throws Exception {

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

        // Arrange
        System.out.println(startDate);
        List<TaskResponse> taskResponse = Arrays.asList(
                new TaskResponse(1L, "P1", "D1",1L, TaskStatus.TODO,startDate,endDate ),
                new TaskResponse(2L, "P2", "D2",1L,TaskStatus.TODO,startDate2,endDate2)
        );
        Mockito.when(taskService.getAllTasks()).thenReturn(taskResponse);
        System.out.println(taskResponse);
        // Act and Assert
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("P1"))
                .andExpect(jsonPath("$[0].description").value("D1"))
                .andExpect(jsonPath("$[0].projectId").value(1))
                .andExpect(jsonPath("$[0].status").value("TODO"))
                .andExpect(jsonPath("$[0].startDate").value("2023-01-01T00:00:00.000+00:00"))
                .andExpect(jsonPath("$[0].endDate").value("2023-08-01T00:00:00.000+00:00"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("P2"))
                .andExpect(jsonPath("$[1].description").value("D2"))
                .andExpect(jsonPath("$[1].projectId").value(1))
                .andExpect(jsonPath("$[1].status").value("TODO"))
                .andExpect(jsonPath("$[1].startDate").value("2024-01-01T00:00:00.000+00:00"))
                .andExpect(jsonPath("$[1].endDate").value("2024-08-01T00:00:00.000+00:00"));
    }

    @Test
    public void shouldReturnTaskById() throws Exception {

        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();

        // Arrange
        Long taskId = 1L;
        TaskResponse taskResponse = new TaskResponse(taskId, "P1", "D1",1L,TaskStatus.TODO, startDate,endDate);
        Mockito.when(taskService.getTaskById(taskId)).thenReturn(taskResponse);

        // Act and Assert
        mockMvc.perform(get("/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.intValue()))
                .andExpect(jsonPath("$.name").value("P1"))
                .andExpect(jsonPath("$.description").value("D1"))
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.status").value("TODO"))
                .andExpect(jsonPath("$.startDate").value("2023-01-01T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.endDate").value("2023-08-01T00:00:00.000+00:00"));
    }

    @Test
    public void shouldCreateTask() throws Exception {

        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();
        // Arrange
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("P1");
        taskRequest.setDescription("D1");
        taskRequest.setProjectId(1L);
        taskRequest.setStartDate(startDate);
        taskRequest.setEndDate(endDate);

        TaskResponse createdTask = new TaskResponse(1L, "P1", "D1",1L,TaskStatus.TODO, startDate,endDate);
        Mockito.when(taskService.createTask(taskRequest)).thenReturn(createdTask);

        // Act and Assert
        mockMvc.perform(post("/tasks")
                .content(new ObjectMapper().writeValueAsString(taskRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("P1"))
                .andExpect(jsonPath("$.description").value("D1"))
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.status").value("TODO"))
                .andExpect(jsonPath("$.startDate").value("2023-01-01T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.endDate").value("2023-08-01T00:00:00.000+00:00"));
    }

    @Test
    public void shouldUpdateTask() throws Exception {

        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();
        // Arrange
        Long taskId = 1L;
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("P1");
        taskRequest.setDescription("D1");
        taskRequest.setProjectId(1L);
        taskRequest.setStatus(TaskStatus.TODO);
        taskRequest.setStartDate(startDate);
        taskRequest.setEndDate(endDate);

        TaskResponse updatedTask = new TaskResponse(taskId, "P1", "D1",1L,TaskStatus.TODO, startDate,endDate);

        Mockito.when(taskService.updateTask(taskId, taskRequest)).thenReturn(updatedTask);

        // Act and Assert
        mockMvc.perform(put("/tasks/{id}", taskId)
                .content(new ObjectMapper().writeValueAsString(taskRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.intValue()))
                .andExpect(jsonPath("$.name").value("P1"))
                .andExpect(jsonPath("$.description").value("D1"))
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.status").value("TODO"))
                .andExpect(jsonPath("$.startDate").value("2023-01-01T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.endDate").value("2023-08-01T00:00:00.000+00:00"));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        // Arrange
        Long taskId = 1L;

        // Act and Assert
        mockMvc.perform(delete("/tasks/{id}", taskId))
                .andExpect(status().isNoContent());

        Mockito.verify(taskService, times(1)).deleteTask(taskId);
    }


}
