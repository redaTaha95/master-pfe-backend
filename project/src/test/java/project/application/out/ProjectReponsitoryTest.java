package project.application.out;


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
import project.domain.Project;
import project.domain.out.ProjectRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = ProjectReponsitoryTest.DataSourceInitializer.class)
public class ProjectReponsitoryTest {

    @Autowired
    private ProjectRepository projectRepository;
    @Container
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:15.3");

    @Test
    @DisplayName("should save a project to the database")
    public void shouldSaveAnEmployee() {

        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();
        // Create a new Project
        Project project = new Project();
        project.setName("Yassine's project");
        project.setDescription("Small little one");
        project.setStartDate(startDate);
        project.setEndDate(endDate);

        // Save the project to the database
        Project savedProject = projectRepository.save(project);

        // Assert that the saved project has the expected values
        Assertions.assertEquals(savedProject.getName(), "Yassine's project");
        Assertions.assertEquals(savedProject.getDescription(), "Small little one");
        Assertions.assertEquals(savedProject.getStartDate(), startDate);
        Assertions.assertEquals(savedProject.getEndDate(), endDate);
    }

    @Test
    @DisplayName("should update a project in the database")
    public void shouldUpdateAnEmployee() {

        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();
        // Create a new Project
        Project project = new Project();
        project.setName("Yassine's project");
        project.setDescription("Small little one");
        project.setStartDate(startDate);
        project.setEndDate(endDate);

        // Save the Project to the database
        Project savedProject = projectRepository.save(project);

        // Update the Project's email
        savedProject.setName("not yassine's project");

        // Save the updated Project to the database
        Project updatedProject = projectRepository.save(savedProject);

        // Assert that the updated Project has the new name
        Assertions.assertEquals(updatedProject.getName(), "not yassine's project");
    }

    @Test
    @DisplayName("should retrieve a project from the database")
    public void shouldRetrieveAProject() {
        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();
        // Create a new Project
        Project project = new Project();
        project.setName("Yassine's project");
        project.setDescription("Small little one");
        project.setStartDate(startDate);
        project.setEndDate(endDate);

        // Save the project to the database
        Project savedProject = projectRepository.save(project);

        // Retrieve the project from the database
        Project retrievedProject = projectRepository.findById(savedProject.getId()).orElse(null);

        // Assert that the retrieved project is not null
        Assertions.assertNotNull(retrievedProject);
        // Assert that the retrieved project has the same ID as the saved project
        Assertions.assertEquals(retrievedProject.getId(), savedProject.getId());
    }

    @Test
    @DisplayName("should delete a project from the database")
    public void shouldDeleteAnEmployee() {
        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();
        // Create a new Project
        Project project = new Project();
        project.setName("Yassine's project");
        project.setDescription("Small little one");
        project.setStartDate(startDate);
        project.setEndDate(endDate);

        // Save the project to the database
        Project savedProject = projectRepository.save(project);

        // Delete the project from the database
        projectRepository.delete(savedProject);

        // Try to retrieve the deleted project from the database
        Project deletedEmployee = projectRepository.findById(savedProject.getId()).orElse(null);

        // Assert that the deleted project is null
        Assertions.assertNull(deletedEmployee);
    }

    @Test
    @DisplayName("should retrieve all projects from the database")
    public void shouldRetrieveAllEmployees() {
        Calendar startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.JANUARY, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date startDate2 = startDateCalendar.getTime();

        startDateCalendar.set(2023, Calendar.AUGUST, 1,00,00,00);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        Date endDate2 = startDateCalendar.getTime();
        // Create a new Project
        Project project = new Project();
        project.setName("Yassine's project");
        project.setDescription("Small little one");
        project.setStartDate(startDate);
        project.setEndDate(endDate);

        Project project2 = new Project();
        project2.setName("Project 2 ");
        project2.setDescription("Small little one2");
        project2.setStartDate(startDate2);
        project2.setEndDate(endDate2);

        // Save the project to the database
        projectRepository.saveAll(List.of(project, project2));

        // Retrieve all projects from the database
        List<Project> allProjects = projectRepository.findAll();

        // Assert that the list of projects contains the expected number of entries
        Assertions.assertEquals(allProjects.size(), 2);
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
