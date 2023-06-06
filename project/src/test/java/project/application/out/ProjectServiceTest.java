package project.application.out;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import project.application.in.ProjectRequest;
import project.domain.Project;
import project.domain.ProjectService;
import project.domain.out.ProjectRepository;
import project.domain.ProjectResponse;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    @DisplayName("Should save a project")
    public void shouldSaveAProject() {
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
        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();
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
        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();

        startDateCalendar.set(2024, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date oldStartDate = startDateCalendar.getTime();

        startDateCalendar.set(2024, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date oldEndDate = startDateCalendar.getTime();

        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setName("P1");
        projectRequest.setDescription("D1");
        projectRequest.setStartDate(startDate);
        projectRequest.setEndDate(endDate);

        Project existingProject = new Project(projectId, "old P1", "old D1",oldStartDate,oldEndDate);
        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));

        ProjectResponse updatedProjectResponse = projectService.updateProject(projectId, projectRequest);

        Assertions.assertEquals(existingProject.getId(), updatedProjectResponse.getId());
        Assertions.assertEquals(projectRequest.getName(), updatedProjectResponse.getName());
        Assertions.assertEquals(projectRequest.getDescription(), updatedProjectResponse.getDescription());
        Assertions.assertEquals(projectRequest.getStartDate(), updatedProjectResponse.getStartDate());
        Assertions.assertEquals(projectRequest.getEndDate(), updatedProjectResponse.getEndDate());
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
