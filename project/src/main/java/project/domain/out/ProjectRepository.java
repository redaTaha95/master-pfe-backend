package project.domain.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
