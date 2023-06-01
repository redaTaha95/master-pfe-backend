package project.domain;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.application.in.ProjectRequest;
import project.domain.out.ProjectRepository;
import project.domain.out.ProjectResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {


    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectResponse createProject(ProjectRequest projectRequest) {
        Project project = buildProjectFromRequest(projectRequest);
        Project savedProject = projectRepository.save(project);
        return convertToResponse(savedProject);

    }

    public List<ProjectResponse> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public ProjectResponse getProjectById(Long id) {
        Project employee = getProjectByIdIfExists(id);
        return convertToResponse(employee);
    }

    public ProjectResponse updateProject(Long id, ProjectRequest projectRequest) {
        Project project = getProjectByIdIfExists(id);
        updateEmployeeFromRequest(project, projectRequest);
        projectRepository.save(project);
        return convertToResponse(project);
    }

    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ProjectNotFoundException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }


    private void updateEmployeeFromRequest(Project project, ProjectRequest projectRequest) {
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setStartDate(projectRequest.getStartDate());
        project.setEndDate(projectRequest.getEndDate());
    }



    private Project getProjectByIdIfExists(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + id));
    }

    private ProjectResponse convertToResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .build();
    }

    private Project buildProjectFromRequest(ProjectRequest projectRequest) {
        return Project.builder()
                .name(projectRequest.getName())
                .description(projectRequest.getDescription())
                .startDate(projectRequest.getStartDate())
                .endDate(projectRequest.getEndDate())
                .build();


    }

}
