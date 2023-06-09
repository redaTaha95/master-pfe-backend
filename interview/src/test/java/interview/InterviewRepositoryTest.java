package interview;

import interview.domain.Interview;
import interview.domain.out.InterviewRepository;
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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = InterviewRepositoryTest.DataSourceInitializer.class)
public class InterviewRepositoryTest {

    @Autowired
    private InterviewRepository interviewRepository;

    @Container
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:15.3");

    @Test
    @DisplayName("should retrieve all interviews from the database")
    public void shouldRetrieveAllInterviews() {

        //Create multiple interviews
        Interview interview1 = new Interview();
        interview1.setInterviewTitle("Entretien telephonique");
        interview1.setInterviewDate(new Date(2023, 7, 7));

        Interview interview2 = new Interview();
        interview2.setInterviewTitle("Entretien technique");
        interview2.setInterviewDate(new Date(2023, 6, 7));

        // Save the interviews to the database
        interviewRepository.saveAll(List.of(interview1, interview2));

        // Retrieve all interviews from the database
        List<Interview> allInterviews = interviewRepository.findAll();

        // Assert that the list of interviews contains the expected number of entries
        Assertions.assertEquals(allInterviews.size(), 2);
    }

    @Test
    @DisplayName("should retrieve an interview from the database")
    public void shouldRetrieveAnInterview() {

        // Create a new interview
        Interview interview = new Interview();
        interview.setInterviewTitle("Entretien RH");
        interview.setInterviewDate(new Date(2024, 7, 20));

        // Save the interview to the database
        Interview savedInterview = interviewRepository.save(interview);

        // Retrieve the interview from the database
        Interview retrieveInterview = interviewRepository.findById(savedInterview.getId())
                .orElse(null);

        // Assert that the retrieve interview is not null
        Assertions.assertNotNull(retrieveInterview);

        // Assert that the retrieved interview has the same ID as the saved interview
        Assertions.assertEquals(retrieveInterview.getId(), savedInterview.getId());
    }

    @Test
    @DisplayName("should save an interview to the database")
    public void shouldSaveAnInterview() {

        // Create a new interview
        Interview interview = new Interview();
        interview.setInterviewTitle("Entretien d'embauche");
        interview.setInterviewDate(new Date(2023, 7, 9));

        // Save the interview to the database
        Interview savedInterview = interviewRepository.save(interview);

        // Assert that the saved recruitmentDemand has the expected values
        Assertions.assertEquals(savedInterview.getInterviewTitle(), "Entretien d'embauche");
        Assertions.assertEquals(savedInterview.getInterviewDate(), new Date(2023, 7, 9));
    }

    @Test
    @DisplayName("should update an interview in the database")
    public void shouldUpdateAnInterview() {

        // Create a new interview
        Interview interview = new Interview();
        interview.setInterviewTitle("Entretien d'embauche");
        interview.setInterviewDate(new Date(2023, 7, 9));

        // Save the interview to the database
        Interview savedInterview = interviewRepository.save(interview);

        // Update the interview's date
        savedInterview.setInterviewDate(new Date(2023, Calendar.DECEMBER, 29));

        // Save the updated interview to the database
        Interview updatedInterview = interviewRepository.save(savedInterview);

        // Assert the the saved interview has the new date
        Assertions.assertEquals(updatedInterview.getInterviewDate(), new Date(2023, Calendar.DECEMBER, 29));
    }

    @Test
    @DisplayName("should delete an interview from the database")
    public void shouldDeleteAnInterview() {

        // Create a new interview
        Interview interview = new Interview();
        interview.setInterviewTitle("Entretien d'embauche");
        interview.setInterviewDate(new Date(2023, 7, 9));

        // Save the interview to the database
        Interview savedInterview = interviewRepository.save(interview);

        // Delete the interview from the database
        interviewRepository.delete(savedInterview);

        // Try to retrieve the deleted interview from the database
        Interview deletedInterview = interviewRepository.findById(savedInterview.getId())
                .orElse(null);

        // Assert that the deleted interview is null
        Assertions.assertNull(deletedInterview);

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
