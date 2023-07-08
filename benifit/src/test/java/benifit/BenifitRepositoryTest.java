package benifit;

import benifit.domain.Benifit;
import benifit.domain.out.BenifitRepository;
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



@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = BenifitRepositoryTest.DataSourceInitializer.class)
public class BenifitRepositoryTest {
    @Container
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:15.3");

    @Autowired
    private BenifitRepository benefitRepository;

    @Test
    @DisplayName("should save a benefit to the database")
    public void shouldSaveBenefit() {
        Benifit benefit = Benifit.builder()
                .details("Health Insurance")
                .matricule("ABC123")
                .employeeId(1L)
                .typeValidationId(1L)
                .build();

        Benifit savedBenefit = benefitRepository.save(benefit);

        Assertions.assertNotNull(savedBenefit.getId());
        Assertions.assertEquals(savedBenefit.getDetails(), benefit.getDetails());
        Assertions.assertEquals(savedBenefit.getMatricule(), benefit.getMatricule());
        Assertions.assertEquals(savedBenefit.getEmployeeId(), benefit.getEmployeeId());
        Assertions.assertEquals(savedBenefit.getTypeValidationId(), benefit.getTypeValidationId());
    }

    @Test
    @DisplayName("should update a benifit in the database")
    public void shouldUpdateABenifit() {
        Benifit benifit = Benifit.builder()
                .details("Some details")
                .matricule("123456")
                .employeeId(1L)
                .typeValidationId(1L)
                .build();

        Benifit savedBenifit = benefitRepository.save(benifit);

        savedBenifit.setDetails("Updated details");

        Benifit updatedBenifit = benefitRepository.save(savedBenifit);

        Assertions.assertEquals("Updated details", updatedBenifit.getDetails());
    }

    @Test
    @DisplayName("should retrieve a benefit by id <")
    public void shouldRetrieveBenefitById() {
        Benifit benefit = Benifit.builder()
                .details("Health Insurance")
                .matricule("ABC123")
                .employeeId(1L)
                .typeValidationId(1L)
                .build();

        Benifit savedBenefit = benefitRepository.save(benefit);

        Benifit retrievedBenefit = benefitRepository.findById(savedBenefit.getId()).orElse(null);

        Assertions.assertNotNull(retrievedBenefit);
        Assertions.assertEquals(retrievedBenefit.getId(), savedBenefit.getId());
        Assertions.assertEquals(retrievedBenefit.getDetails(), savedBenefit.getDetails());
        Assertions.assertEquals(retrievedBenefit.getMatricule(), savedBenefit.getMatricule());
        Assertions.assertEquals(retrievedBenefit.getEmployeeId(), savedBenefit.getEmployeeId());
        Assertions.assertEquals(retrievedBenefit.getTypeValidationId(), savedBenefit.getTypeValidationId());
    }

    @Test
    @DisplayName("should delete a benefit from the database")
    public void shouldDeleteBenefit() {
        Benifit benefit = Benifit.builder()
                .details("Health Insurance")
                .matricule("ABC123")
                .employeeId(1L)
                .typeValidationId(1L)
                .build();

        Benifit savedBenefit = benefitRepository.save(benefit);

        benefitRepository.delete(savedBenefit);

        Benifit deletedBenefit = benefitRepository.findById(savedBenefit.getId()).orElse(null);

        Assertions.assertNull(deletedBenefit);
    }

    @Test
    @DisplayName("should delete a benifit from the database")
    public void shouldDeleteABenifit() {
        Benifit benifit = Benifit.builder()
                .details("Some details")
                .matricule("123456")
                .employeeId(1L)
                .typeValidationId(1L)
                .build();

        Benifit savedBenifit = benefitRepository.save(benifit);

        benefitRepository.delete(savedBenifit);

        Benifit deletedBenifit = benefitRepository.findById(savedBenifit.getId()).orElse(null);

        Assertions.assertNull(deletedBenifit);
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