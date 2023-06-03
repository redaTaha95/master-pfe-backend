package task.domain.out;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.domain.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
