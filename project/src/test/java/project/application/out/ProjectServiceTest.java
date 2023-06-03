package project.application.out;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import project.application.in.ProjectRequest;
import project.domain.Project;
import project.domain.ProjectService;
import project.domain.out.ProjectRepository;
import project.domain.out.ProjectResponse;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    @DisplayName("Should save a project")
    public void shouldSaveAnEmployee() {
        Date startDate = new Date("01/01/2023");
        Date endDate = new Date("08/01/2023");
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setName("Reda");
        projectRequest.setDescription("TAHA");
        projectRequest.setStartDate(startDate);
        projectRequest.setEndDate(endDate);

        Project savedProject = Project.builder()
                .name(projectRequest.getName())
                .description(projectRequest.getDescription())
                .startDate(projectRequest.getStartDate())
                .endDate(projectRequest.getEndDate())
                .build();

        Mockito.when(projectRepository.save(any(Project.class))).thenReturn(savedProject);

        ProjectResponse project = projectService.createProject(projectRequest);

        Assertions.assertEquals(project.getName(), projectRequest.getName());
    }

    @Test
    @DisplayName("Should return all projects")
    public void shouldReturnAllProjects() {
        Date startDate = new Date("01/01/2023");
        Date endDate = new Date("08/01/2023");
        Date startDate2 = new Date("01/01/2024");
        Date endDate2 = new Date("08/01/2024");
        List<Project> projects = Arrays.asList(
                new Project(1L,"P1", "D1", startDate, endDate),
                new Project(1L,"P2", "D2", startDate2, endDate2)
        );
        Mockito.when(projectRepository.findAll()).thenReturn(projects);

        List<ProjectResponse> projectResponses = projectService.getAllProjects();

        Assertions.assertEquals(projects.size(), projectResponses.size());
    }

    @Test
    @DisplayName("Should return project by ID")
    public void shouldReturnProjectById() {
        Long ProjectId = 1L;
        Date startDate = new Date("01/01/2023");
        Date endDate = new Date("08/01/2023");
        Project project = new Project(1L,"P1", "D1", startDate, endDate);
        Mockito.when(projectRepository.findById(ProjectId)).thenReturn(Optional.of(project));

        ProjectResponse projectResponse = projectService.getProjectById(ProjectId);

        Assertions.assertEquals(project.getId(), projectResponse.getId());
        Assertions.assertEquals(project.getName(), projectResponse.getName());
        Assertions.assertEquals(project.getDescription(), projectResponse.getDescription());
        Assertions.assertEquals(project.getStartDate(), projectResponse.getStartDate());
        Assertions.assertEquals(project.getEndDate(), projectResponse.getEndDate());
    }

    @Test
    @DisplayName("Should update project")
    public void shouldUpdateProject() {
        Long projectId = 1L;
        Date startDate = new Date("01/01/2023");
        Date endDate = new Date("08/01/2023");
        Date oldStartDate = new Date("01/01/2023");
        Date oldEndDate = new Date("08/01/2023");
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setName("P1");
        projectRequest.setDescription("D1");
        projectRequest.setStartDate(startDate);
        projectRequest.setEndDate(endDate);

        Project existingProject = new Project(projectId, "old P1", "old D1",oldStartDate,oldEndDate);
        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));

        ProjectResponse updatedEmployeeResponse = projectService.updateProject(projectId, projectRequest);

        Assertions.assertEquals(existingProject.getId(), updatedEmployeeResponse.getId());
        Assertions.assertEquals(projectRequest.getName(), updatedEmployeeResponse.getName());
        Assertions.assertEquals(projectRequest.getDescription(), updatedEmployeeResponse.getDescription());
        Assertions.assertEquals(projectRequest.getStartDate(), updatedEmployeeResponse.getStartDate());
        Assertions.assertEquals(projectRequest.getEndDate(), updatedEmployeeResponse.getEndDate());
    }

    @Test
    @DisplayName("Should delete project")
    public void shouldDeleteProject() {
        Long projectId = 1L;
        Mockito.when(projectRepository.existsById(projectId)).thenReturn(true);

        projectService.deleteProject(projectId);

        Mockito.verify(projectRepository).deleteById(projectId);
    }


}
