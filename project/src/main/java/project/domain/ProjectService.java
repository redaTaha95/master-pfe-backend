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

    public List<ProjectResponse> getAllprojects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public ProjectResponse getProjectById(Long id) {
        Project employee = getProjectByIdIfExists(id);
        return convertToResponse(employee);
    }




    private Project getProjectByIdIfExists(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Employee not found with id: " + id));
    }

    private ProjectResponse convertToResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .description(project.getDescription())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .build();
    }

    private Project buildProjectFromRequest(ProjectRequest projectRequest) {
        return Project.builder()
                .projectName(projectRequest.getProjectName())
                .description(projectRequest.getDescription())
                .startDate(projectRequest.getStartDate())
                .endDate(projectRequest.getEndDate())
                .build();


    }

}
