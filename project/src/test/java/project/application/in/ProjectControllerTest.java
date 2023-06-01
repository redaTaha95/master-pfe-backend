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
import project.domain.out.ProjectResponse;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

        Date startDate = new Date("01/01/2023");
        Date endDate = new Date("08/01/2023");
        Date startDate2 = new Date("01/01/2024");
        Date endDate2 = new Date("08/01/2024");

        // Arrange
        List<ProjectResponse> projectResponses = Arrays.asList(
                new ProjectResponse(1L, "P1", "D1",startDate,endDate ),
                new ProjectResponse(2L, "P2", "D2",startDate2,endDate2)
        );
        Mockito.when(projectService.getAllProjects()).thenReturn(projectResponses);

        // Act and Assert
        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("P1"))
                .andExpect(jsonPath("$[0].description").value("D1"))
                .andExpect(jsonPath("$[0].startDate").value(startDate))
                .andExpect(jsonPath("$[0].endDate").value(endDate))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("P2"))
                .andExpect(jsonPath("$[1].description").value("D2"))
                .andExpect(jsonPath("$[1].startDate").value(startDate2))
                .andExpect(jsonPath("$[1].endDate").value(endDate2));
    }

    @Test
    public void shouldReturnProjectById() throws Exception {

        Date startDate = new Date("01/01/2023");
        Date endDate = new Date("08/01/2023");
        // Arrange
        Long projectId = 1L;
        ProjectResponse projectResponse = new ProjectResponse(projectId, "P1", "D1", startDate,endDate);
        Mockito.when(projectService.getProjectById(projectId)).thenReturn(projectResponse);

        // Act and Assert
        mockMvc.perform(get("/employees/{id}", projectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(projectId.intValue()))
                .andExpect(jsonPath("$.name").value("P1"))
                .andExpect(jsonPath("$.description").value("D1"))
                .andExpect(jsonPath("$.startDate").value(startDate))
                .andExpect(jsonPath("$.endDate").value(endDate));
    }

    @Test
    public void shouldCreateProject() throws Exception {

        Date startDate = new Date("01/01/2023");
        Date endDate = new Date("08/01/2023");
        // Arrange
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setName("P1");
        projectRequest.setDescription("D1");
        projectRequest.setStartDate(startDate);
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
                .andExpect(jsonPath("$.startDate").value(startDate))
                .andExpect(jsonPath("$.endDate").value(endDate));
    }

    @Test
    public void shouldUpdateProject() throws Exception {
        Date startDate = new Date("01/01/2023");
        Date endDate = new Date("08/01/2023");
        // Arrange
        Long projectId = 1L;
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setName("P1");
        projectRequest.setDescription("D1");
        projectRequest.setStartDate(startDate);
        projectRequest.setEndDate(endDate);

        ProjectResponse updatedProject = new ProjectResponse(projectId, "P1", "D1", startDate,endDate);

        Mockito.when(projectService.updateProject(projectId, projectRequest)).thenReturn(updatedProject);

        // Act and Assert
        mockMvc.perform(put("/employees/{id}", projectId)
                .content(new ObjectMapper().writeValueAsString(projectRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(projectId.intValue()))
                .andExpect(jsonPath("$.name").value("P1"))
                .andExpect(jsonPath("$.description").value("D1"))
                .andExpect(jsonPath("$.startDate").value(startDate))
                .andExpect(jsonPath("$.endDate").value(endDate));
    }

    @Test
    public void shouldDeleteProject() throws Exception {
        // Arrange
        Long projectId = 1L;

        // Act and Assert
        mockMvc.perform(delete("/project/{id}", projectId))
                .andExpect(status().isNoContent());

        Mockito.verify(projectService, times(1)).deleteProject(projectId);
    }


}
