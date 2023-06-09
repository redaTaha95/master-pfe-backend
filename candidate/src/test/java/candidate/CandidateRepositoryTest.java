package candidate;

import candidate.domain.Candidate;
import candidate.domain.out.CandidateRepository;
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

import java.util.List;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = CandidateRepositoryTest.DataSourceInitializer.class)
public class CandidateRepositoryTest {

    @Autowired
    private CandidateRepository candidateRepository;

    @Container
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:15.3");

    @Test
    @DisplayName("should retrieve all candidates from the database")
    public void shouldRetrieveAllCandidates() {

        // Create multiple candidates
        Candidate candidate1 = new Candidate();
        candidate1.setFirstName("Wiam");
        candidate1.setLastName("KHAMLICHI");
        candidate1.setEmail("khamlichiwiam@gmail.com");
        candidate1.setPhone("0691929394");
        candidate1.setAddress("Casablanca, Maroc");

        Candidate candidate2 = new Candidate();
        candidate2.setFirstName("Abdou");
        candidate2.setLastName("KHAMLICHI");
        candidate2.setEmail("khamlichiabdou@gmail.com");
        candidate2.setPhone("0707070001");
        candidate2.setAddress("Casablanca, Maroc");

        // Save the candidates to the database
        candidateRepository.saveAll(List.of(candidate1, candidate2));

        // Retrieve all candidates from the database
        List<Candidate> allCandidates = candidateRepository.findAll();

        // Assert that the list of candidates contains the expected number of entries
        Assertions.assertEquals(allCandidates.size(), 2);
    }

    @Test
    @DisplayName("should retrieve a candidate from the database")
    public void shouldRetrieveACandidate() {

        // Create a new candidate
        Candidate candidate = new Candidate();
        candidate.setFirstName("Jane");
        candidate.setLastName("Smith");
        candidate.setEmail("janesmith@example.com");
        candidate.setPhone("0001020304");
        candidate.setAddress("Paris, France");

        // Save the candidate to the database
        Candidate savedCandidate = candidateRepository.save(candidate);

        // Retrieve the candidate from the database
        Candidate retrieveCandidate = candidateRepository.findById(savedCandidate.getId())
                .orElse(null);

        // Assert that the retrieved candidate is not null
        Assertions.assertNotNull(retrieveCandidate);

        // Assert that the retrieved candidate has the same ID as the saved candidate
        Assertions.assertEquals(retrieveCandidate.getId(), savedCandidate.getId());
    }

    @Test
    @DisplayName("should save a candidate to the database")
    public void shouldSaveACandidate() {

        // Create a new candidate
        Candidate candidate = new Candidate();
        candidate.setFirstName("John");
        candidate.setLastName("Doe");
        candidate.setEmail("johndoe@example.com");
        candidate.setPhone("1234567890");
        candidate.setAddress("123 Main Street, City, State");

        // Save the candidate to the database
        Candidate savedCandidate = candidateRepository.save(candidate);

        // Assert that the saved candidate has the expected values
        Assertions.assertEquals(savedCandidate.getFirstName(), "John");
        Assertions.assertEquals(savedCandidate.getLastName(), "Doe");
        Assertions.assertEquals(savedCandidate.getEmail(), "johndoe@example.com");
        Assertions.assertEquals(savedCandidate.getPhone(), "1234567890");
        Assertions.assertEquals(savedCandidate.getAddress(), "123 Main Street, City, State");
    }

    @Test
    @DisplayName("should update a candidate in the database")
    public void shouldUpdateACandidate() {

        // Create a new candidate
        Candidate candidate = new Candidate();
        candidate.setFirstName("John");
        candidate.setLastName("Doe");
        candidate.setEmail("johndoe@example.com");
        candidate.setPhone("1234567890");
        candidate.setAddress("123 Main Street, City, State");

        // Save the candidate to the database
        Candidate savedCandidate = candidateRepository.save(candidate);

        // Update the candidate's address
        savedCandidate.setAddress("Seville, Spain");

        // Save the updated candidate to the database
        Candidate updatedCandidate = candidateRepository.save(savedCandidate);

        // Assert that the updated candidate has the new address
        Assertions.assertEquals(updatedCandidate.getAddress(), "Seville, Spain");
    }

    @Test
    @DisplayName("should delete a candidate from the database")
    public void shouldDeleteACandidate() {

        // Create a new candidate
        Candidate candidate = new Candidate();
        candidate.setFirstName("John");
        candidate.setLastName("Doe");
        candidate.setEmail("johndoe@example.com");
        candidate.setPhone("1234567890");
        candidate.setAddress("123 Main Street, City, State");

        // Save the candidate to the database
        Candidate savedCandidate = candidateRepository.save(candidate);

        // Delete the candidate from the database
        candidateRepository.delete(savedCandidate);

        // Try to retrieve the deleted candidate from the database
        Candidate deletedCandidate = candidateRepository.findById(savedCandidate.getId())
                .orElse(null);

        // Assert that the deleted candidate is null
        Assertions.assertNull(deletedCandidate);
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
