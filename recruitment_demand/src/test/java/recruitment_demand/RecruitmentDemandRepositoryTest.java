package recruitment_demand;

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
import recruitment_demand.domain.RecruitmentDemand;
import recruitment_demand.domain.out.RecruitmentDemandRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = RecruitmentDemandRepositoryTest.DataSourceInitializer.class)
public class RecruitmentDemandRepositoryTest {

    @Autowired
    private RecruitmentDemandRepository recruitmentDemandRepository;

    @Container
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:15.3");

    @Test
    @DisplayName("should retrieve all recruitment demands from the database")
    public void shouldRetrieveAllRecruitmentDemands() {

        //Create multiple recruitment demands
        RecruitmentDemand recruitmentDemand1 = new RecruitmentDemand();
        recruitmentDemand1.setPostTitle("Software Developer");
        recruitmentDemand1.setPostDescription("Post Description");
        recruitmentDemand1.setDepartment("IT");
        recruitmentDemand1.setNumberOfProfiles(5);
        recruitmentDemand1.setNumberOfYearsOfExperience(2);
        recruitmentDemand1.setLevelOfStudies("Bac + 5");
        recruitmentDemand1.setStatusOfDemand("En cours");
        recruitmentDemand1.setDateOfDemand(new Date(2023, 5, 7));

        RecruitmentDemand recruitmentDemand2 = new RecruitmentDemand();
        recruitmentDemand2.setPostTitle("Software Engineer");
        recruitmentDemand2.setPostDescription("Post Description");
        recruitmentDemand2.setDepartment("IT");
        recruitmentDemand2.setNumberOfProfiles(1);
        recruitmentDemand2.setNumberOfYearsOfExperience(1);
        recruitmentDemand2.setLevelOfStudies("Bac + 5");
        recruitmentDemand2.setStatusOfDemand("En cours");
        recruitmentDemand2.setDateOfDemand(new Date(2023, 5, 7));

        // Save the recruitment demands to the database
        recruitmentDemandRepository.saveAll(List.of(recruitmentDemand1, recruitmentDemand2));

        // Retrieve all recruitment demands from the database
        List<RecruitmentDemand> allRecruitmentDemands = recruitmentDemandRepository.findAll();

        // Assert that the list of recruitment demands contains the expected number of entries
        Assertions.assertEquals(allRecruitmentDemands.size(), 2);
    }

    @Test
    @DisplayName("should retrieve a recruitment demand from the database")
    public void shouldRetrieveARecruitmentDemand() {

        // Create a new recruitment demand
        RecruitmentDemand recruitmentDemand = new RecruitmentDemand();
        recruitmentDemand.setPostTitle("Software Developer");
        recruitmentDemand.setPostDescription("Post Description");
        recruitmentDemand.setDepartment("IT");
        recruitmentDemand.setNumberOfProfiles(5);
        recruitmentDemand.setNumberOfYearsOfExperience(2);
        recruitmentDemand.setLevelOfStudies("Bac + 5");
        recruitmentDemand.setStatusOfDemand("En cours");
        recruitmentDemand.setDateOfDemand(new Date(2023, 5, 7));

        // Save the recruitment demand to the database
        RecruitmentDemand savedRecruitmentDemand = recruitmentDemandRepository.save(recruitmentDemand);

        // Retrieve the recruitment demand from the database
        RecruitmentDemand retrieveRecruitmentDemand = recruitmentDemandRepository.findById(savedRecruitmentDemand.getId())
                .orElse(null);

        // Assert that the retrieved recruitment demand is not null
        Assertions.assertNotNull(retrieveRecruitmentDemand);

        // Assert that the retrieved recruitment demand has the same ID as the saved recruitment demand
        Assertions.assertEquals(retrieveRecruitmentDemand.getId(), savedRecruitmentDemand.getId());
    }

    @Test
    @DisplayName("should save a recruitment demand to the database")
    public void shouldSaveARecruitmentDemand() {

        // Create a new recruitment demand
        RecruitmentDemand recruitmentDemand = new RecruitmentDemand();
        recruitmentDemand.setPostTitle("Software Developer");
        recruitmentDemand.setPostDescription("Post Description");
        recruitmentDemand.setDepartment("IT");
        recruitmentDemand.setNumberOfProfiles(5);
        recruitmentDemand.setNumberOfYearsOfExperience(2);
        recruitmentDemand.setLevelOfStudies("Bac + 5");
        recruitmentDemand.setStatusOfDemand("En cours");
        recruitmentDemand.setDateOfDemand(new Date(2023, 5, 7));

        // Save the recruitment demand to the database
        RecruitmentDemand savedRecruitmentDemand = recruitmentDemandRepository.save(recruitmentDemand);

        // Assert that the saved recruitment demand has the expected values
        Assertions.assertEquals(savedRecruitmentDemand.getPostTitle(), "Software Developer");
        Assertions.assertEquals(savedRecruitmentDemand.getPostDescription(), "Post Description");
        Assertions.assertEquals(savedRecruitmentDemand.getDepartment(), "IT");
        Assertions.assertEquals(savedRecruitmentDemand.getNumberOfProfiles(), 5);
        Assertions.assertEquals(savedRecruitmentDemand.getNumberOfYearsOfExperience(), 2);
        Assertions.assertEquals(savedRecruitmentDemand.getLevelOfStudies(), "Bac + 5");
        Assertions.assertEquals(savedRecruitmentDemand.getStatusOfDemand(), "En cours");
        Assertions.assertEquals(savedRecruitmentDemand.getDateOfDemand(), new Date(2023, 5, 7));
    }

    @Test
    @DisplayName("should update a recruitment demand in the database")
    public void shouldUpdateARecruitmentDemand() {

        // Create a new recruitment demand
        RecruitmentDemand recruitmentDemand = new RecruitmentDemand();
        recruitmentDemand.setPostTitle("Software Developer");
        recruitmentDemand.setPostDescription("Post Description");
        recruitmentDemand.setDepartment("IT");
        recruitmentDemand.setNumberOfProfiles(5);
        recruitmentDemand.setNumberOfYearsOfExperience(2);
        recruitmentDemand.setLevelOfStudies("Bac + 5");
        recruitmentDemand.setStatusOfDemand("En cours");
        recruitmentDemand.setDateOfDemand(new Date(2023, 5, 7));

        // Save the recruitment demand to the database
        RecruitmentDemand savedRecruitmentDemand = recruitmentDemandRepository.save(recruitmentDemand);

        // Update the recruitment demand's level of studies
        savedRecruitmentDemand.setLevelOfStudies("Bac + 3");

        // Save the updated recruitmentDemand to the database
        RecruitmentDemand updatedRecruitmentDemand = recruitmentDemandRepository.save(savedRecruitmentDemand);

        // Assert that the updated recruitment demand has the new level of studies
        Assertions.assertEquals(updatedRecruitmentDemand.getLevelOfStudies(), "Bac + 3");
    }

    @Test
    @DisplayName("should delete a recruitment demand from the database")
    public void shouldDeleteARecruitmentDemand() {

        // Create a new recruitment demand
        RecruitmentDemand recruitmentDemand = new RecruitmentDemand();
        recruitmentDemand.setPostTitle("Software Developer");
        recruitmentDemand.setPostDescription("Post Description");
        recruitmentDemand.setDepartment("IT");
        recruitmentDemand.setNumberOfProfiles(5);
        recruitmentDemand.setNumberOfYearsOfExperience(2);
        recruitmentDemand.setLevelOfStudies("Bac + 5");
        recruitmentDemand.setStatusOfDemand("En cours");
        recruitmentDemand.setDateOfDemand(new Date(2023, 5, 7));

        // Save the recruitment demand to the database
        RecruitmentDemand savedRecruitmentDemand = recruitmentDemandRepository.save(recruitmentDemand);

        // Delete the recruitment demand from the database
        recruitmentDemandRepository.delete(savedRecruitmentDemand);

        // Try to retrieve the deleted recruitment demand from the database
        RecruitmentDemand deletedRecruitmentDemand = recruitmentDemandRepository.findById(savedRecruitmentDemand.getId())
                .orElse(null);

        // Assert that the deleted recruitment demand is null
        Assertions.assertNull(deletedRecruitmentDemand);
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
