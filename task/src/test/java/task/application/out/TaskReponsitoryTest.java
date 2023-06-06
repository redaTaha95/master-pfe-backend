package task.application.out;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import task.domain.Task;
import task.domain.out.TaskRepository;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = TaskReponsitoryTest.DataSourceInitializer.class)
public class TaskReponsitoryTest {

    @Autowired
    private TaskRepository taskRepository;
    @Container
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:15.3");

    @Test
    @DisplayName("should save a task to the database")
    public void shouldSaveATask() {

        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();

        Task task = new Task();
        task.setName("Yassine's task");
        task.setDescription("Small little one");
        task.setProjectId(1L);
        task.setStartDate(startDate);
        task.setEndDate(endDate);

        Task savedTask = taskRepository.save(task);

        Assertions.assertEquals(savedTask.getName(), "Yassine's task");
        Assertions.assertEquals(savedTask.getDescription(), "Small little one");
        Assertions.assertEquals(savedTask.getProjectId(), 1);
        Assertions.assertEquals(savedTask.getStartDate(), startDate);
        Assertions.assertEquals(savedTask.getEndDate(), endDate);
    }

    @Test
    @DisplayName("should update a task in the database")
    public void shouldUpdateATask() {

        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();

        Task task = new Task();
        task.setName("Yassine's task");
        task.setDescription("Small little one");
        task.setProjectId(1L);
        task.setStartDate(startDate);
        task.setEndDate(endDate);

        Task savedTask = taskRepository.save(task);

        savedTask.setName("not yassine's task");

        Task updatedTask = taskRepository.save(savedTask);

        Assertions.assertEquals(updatedTask.getName(), "not yassine's task");
    }

    @Test
    @DisplayName("should retrieve a task from the database")
    public void shouldRetrieveATask() {
        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();

        Task task = new Task();
        task.setName("Yassine's task");
        task.setDescription("Small little one");
        task.setProjectId(1L);
        task.setStartDate(startDate);
        task.setEndDate(endDate);

        Task savedTask = taskRepository.save(task);

        Task retrievedTask = taskRepository.findById(savedTask.getId()).orElse(null);

        Assertions.assertNotNull(retrievedTask);
        Assertions.assertEquals(retrievedTask.getId(), savedTask.getId());
    }

    @Test
    @DisplayName("should delete a task from the database")
    public void shouldDeleteAnEmployee() {
        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();

        Task task = new Task();
        task.setName("Yassine's task");
        task.setDescription("Small little one");
        task.setProjectId(1L);
        task.setStartDate(startDate);
        task.setEndDate(endDate);

        Task savedTask = taskRepository.save(task);

        taskRepository.delete(savedTask);

        Task deletedEmployee = taskRepository.findById(savedTask.getId()).orElse(null);

        Assertions.assertNull(deletedEmployee);
    }

    @Test
    @DisplayName("should retrieve all tasks from the database")
    public void shouldRetrieveAllEmployees() {
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

        Task task = new Task();
        task.setName("Yassine's task");
        task.setDescription("Small little one");
        task.setProjectId(1L);
        task.setStartDate(startDate);
        task.setEndDate(endDate);

        Task task2 = new Task();
        task2.setName("Yassine's task 2");
        task2.setDescription("Small little one 2");
        task2.setProjectId(1L);
        task2.setStartDate(startDate2);
        task2.setEndDate(endDate2);

        taskRepository.saveAll(List.of(task, task2));

        List<Task> allTasks = taskRepository.findAll();

        Assertions.assertEquals(allTasks.size(), 2);
    }




    public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + database.getJdbcUrl(),
                    "spring.datasource.username=" + database.getUsername(),
                    "spring.datasource.password=" + database.getPassword()
            );
        }
    }


}
