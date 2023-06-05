package project.application.in;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import project.domain.ProjectService;
import project.domain.ProjectResponse;

import java.util.*;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;


    @Test
    public void shouldReturnAllProject() throws Exception {

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
        List<ProjectResponse> projectResponses = Arrays.asList(
                new ProjectResponse(1L, "P1", "D1",startDate,endDate ),
                new ProjectResponse(2L, "P2", "D2",startDate2,endDate2)
        );
        Mockito.when(projectService.getAllProjects()).thenReturn(projectResponses);
        System.out.println(projectResponses);
        // Act and Assert
        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("P1"))
                .andExpect(jsonPath("$[0].description").value("D1"))
                .andExpect(jsonPath("$[0].startDate").value("2023-01-01T00:00:00.000+00:00"))
                .andExpect(jsonPath("$[0].endDate").value("2023-08-01T00:00:00.000+00:00"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("P2"))
                .andExpect(jsonPath("$[1].description").value("D2"))
                .andExpect(jsonPath("$[1].startDate").value("2024-01-01T00:00:00.000+00:00"))
                .andExpect(jsonPath("$[1].endDate").value("2024-08-01T00:00:00.000+00:00"));
    }

    @Test
    public void shouldReturnProjectById() throws Exception {

        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();

        // Arrange
        Long projectId = 1L;
        ProjectResponse projectResponse = new ProjectResponse(projectId, "P1", "D1", startDate,endDate);
        Mockito.when(projectService.getProjectById(projectId)).thenReturn(projectResponse);

        // Act and Assert
        mockMvc.perform(get("/projects/{id}", projectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(projectId.intValue()))
                .andExpect(jsonPath("$.name").value("P1"))
                .andExpect(jsonPath("$.description").value("D1"))
                .andExpect(jsonPath("$.startDate").value("2023-01-01T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.endDate").value("2023-08-01T00:00:00.000+00:00"));
    }

    @Test
    public void shouldCreateProject() throws Exception {

        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();
        // Arrange
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setName("P1");
        projectRequest.setDescription("D1");
        projectRequest.setStartDate(new  Date());
        projectRequest.setEndDate(endDate);

        ProjectResponse createdProject = new ProjectResponse(1L, "P1", "D1", startDate,endDate);
        Mockito.when(projectService.createProject(projectRequest)).thenReturn(createdProject);

        // Act and Assert
        mockMvc.perform(post("/projects")
                .content(new ObjectMapper().writeValueAsString(projectRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("P1"))
                .andExpect(jsonPath("$.description").value("D1"))
                .andExpect(jsonPath("$.startDate").value("2023-01-01T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.endDate").value("2023-08-01T00:00:00.000+00:00"));
    }

    @Test
    public void shouldUpdateProject() throws Exception {

        Long projectId = 1L;

        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();
        // Arrange

        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setName("P1");
        projectRequest.setDescription("D1");
        projectRequest.setStartDate(startDate);
        projectRequest.setEndDate(endDate);

        ProjectResponse updatedProject = new ProjectResponse(projectId, "P1", "D1", startDate,endDate);

        Mockito.when(projectService.updateProject(projectId, projectRequest)).thenReturn(updatedProject);

        // Act and Assert
        mockMvc.perform(put("/projects/{id}", projectId)
                .content(new ObjectMapper().writeValueAsString(projectRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(projectId.intValue()))
                .andExpect(jsonPath("$.name").value("P1"))
                .andExpect(jsonPath("$.description").value("D1"))
                .andExpect(jsonPath("$.startDate").value("2023-01-01T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.endDate").value("2023-08-01T00:00:00.000+00:00"));
    }

    @Test
    public void shouldDeleteProject() throws Exception {
        // Arrange
        Long projectId = 1L;

        // Act and Assert
        mockMvc.perform(delete("/projects/{id}", projectId))
                .andExpect(status().isNoContent());

        Mockito.verify(projectService, times(1)).deleteProject(projectId);
    }


}
