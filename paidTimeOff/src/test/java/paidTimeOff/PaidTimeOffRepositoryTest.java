package paidTimeOff;

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
import paidTimeOff.domain.PaidTimeOff;
import paidTimeOff.domain.out.PaidTimeOffRepository;

import java.util.Date;
import java.util.List;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = PaidTimeOffRepositoryTest.DataSourceInitializer.class)
public class PaidTimeOffRepositoryTest {
    @Container
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:15.3");
    @Autowired
    private PaidTimeOffRepository paidTimeOffRepository;

    @Test
    @DisplayName("should save a paid time off to the database")
    public void shouldSavePaidTimeOff() {
        PaidTimeOff paidTimeOff = new PaidTimeOff();
        paidTimeOff.setDetails("Vacation");
        paidTimeOff.setStartDate(new Date());
        paidTimeOff.setEndDate(new Date());
        paidTimeOff.setEmployeeId(1L);

        PaidTimeOff savedPaidTimeOff = paidTimeOffRepository.save(paidTimeOff);

        Assertions.assertNotNull(savedPaidTimeOff.getId());
        Assertions.assertEquals(savedPaidTimeOff.getDetails(), paidTimeOff.getDetails());
        Assertions.assertEquals(savedPaidTimeOff.getStartDate(), paidTimeOff.getStartDate());
        Assertions.assertEquals(savedPaidTimeOff.getEndDate(), paidTimeOff.getEndDate());
        Assertions.assertEquals(savedPaidTimeOff.getEmployeeId(), paidTimeOff.getEmployeeId());
    }

    @Test
    @DisplayName("should retrieve a paid time off from the database")
    public void shouldRetrievePaidTimeOff() {
        PaidTimeOff paidTimeOff = new PaidTimeOff();
        paidTimeOff.setDetails("Vacation");
        paidTimeOff.setStartDate(new Date());
        paidTimeOff.setEndDate(new Date());
        paidTimeOff.setEmployeeId(1L);

        PaidTimeOff savedPaidTimeOff = paidTimeOffRepository.save(paidTimeOff);

        PaidTimeOff retrievedPaidTimeOff = paidTimeOffRepository.findById(savedPaidTimeOff.getId()).orElse(null);

        Assertions.assertNotNull(retrievedPaidTimeOff);
        Assertions.assertEquals(retrievedPaidTimeOff.getId(), savedPaidTimeOff.getId());
        Assertions.assertEquals(retrievedPaidTimeOff.getDetails(), savedPaidTimeOff.getDetails());
        Assertions.assertEquals(retrievedPaidTimeOff.getStartDate(), savedPaidTimeOff.getStartDate());
        Assertions.assertEquals(retrievedPaidTimeOff.getEndDate(), savedPaidTimeOff.getEndDate());
        Assertions.assertEquals(retrievedPaidTimeOff.getEmployeeId(), savedPaidTimeOff.getEmployeeId());
    }

    @Test
    @DisplayName("should delete a paid time off from the database")
    public void shouldDeletePaidTimeOff() {
        PaidTimeOff paidTimeOff = new PaidTimeOff();
        paidTimeOff.setDetails("Vacation");
        paidTimeOff.setStartDate(new Date());
        paidTimeOff.setEndDate(new Date());
        paidTimeOff.setEmployeeId(1L);

        PaidTimeOff savedPaidTimeOff = paidTimeOffRepository.save(paidTimeOff);

        paidTimeOffRepository.delete(savedPaidTimeOff);

        PaidTimeOff deletedPaidTimeOff = paidTimeOffRepository.findById(savedPaidTimeOff.getId()).orElse(null);

        Assertions.assertNull(deletedPaidTimeOff);
    }

    @Test
    @DisplayName("should retrieve all paid time offs from the database")
    public void shouldRetrieveAllPaidTimeOffs() {
        PaidTimeOff paidTimeOff1 = new PaidTimeOff();
        paidTimeOff1.setDetails("Vacation");
        paidTimeOff1.setStartDate(new Date());
        paidTimeOff1.setEndDate(new Date());
        paidTimeOff1.setEmployeeId(1L);

        PaidTimeOff paidTimeOff2 = new PaidTimeOff();
        paidTimeOff2.setDetails("Sick leave");
        paidTimeOff2.setStartDate(new Date());
        paidTimeOff2.setEndDate(new Date());
        paidTimeOff2.setEmployeeId(2L);

        paidTimeOffRepository.saveAll(List.of(paidTimeOff1, paidTimeOff2));

        List<PaidTimeOff> allPaidTimeOffs = paidTimeOffRepository.findAll();

        Assertions.assertEquals(allPaidTimeOffs.size(), 2);
    }

    @Test
    @DisplayName("should retrieve paid time offs by employee ID")
    public void shouldRetrievePaidTimeOffsByEmployeeId() {
        PaidTimeOff paidTimeOff1 = new PaidTimeOff();
        paidTimeOff1.setDetails("Vacation");
        paidTimeOff1.setStartDate(new Date());
        paidTimeOff1.setEndDate(new Date());
        paidTimeOff1.setEmployeeId(1L);

        PaidTimeOff paidTimeOff2 = new PaidTimeOff();
        paidTimeOff2.setDetails("Sick leave");
        paidTimeOff2.setStartDate(new Date());
        paidTimeOff2.setEndDate(new Date());
        paidTimeOff2.setEmployeeId(2L);

        paidTimeOffRepository.saveAll(List.of(paidTimeOff1, paidTimeOff2));

        List<PaidTimeOff> paidTimeOffs = paidTimeOffRepository.findByEmployeeId(1L);

        Assertions.assertEquals(paidTimeOffs.size(), 1);
        Assertions.assertEquals(paidTimeOffs.get(0).getEmployeeId(), 1L);
    }

    @Test
    @DisplayName("should retrieve the latest paid time off by employee ID")
    public void shouldRetrieveLatestPaidTimeOffByEmployeeId() {
        PaidTimeOff paidTimeOff1 = new PaidTimeOff();
        paidTimeOff1.setDetails("Vacation");
        paidTimeOff1.setStartDate(new Date(2022));
        paidTimeOff1.setEndDate(new Date(2022));
        paidTimeOff1.setEmployeeId(1L);

        PaidTimeOff paidTimeOff2 = new PaidTimeOff();
        paidTimeOff2.setDetails("Sick leave");
        paidTimeOff2.setStartDate(new Date(2023));
        paidTimeOff2.setEndDate(new Date(2023));
        paidTimeOff2.setEmployeeId(1L);

        paidTimeOffRepository.saveAll(List.of(paidTimeOff1, paidTimeOff2));

        PaidTimeOff latestPaidTimeOff = paidTimeOffRepository.findFirstByEmployeeIdOrderByStartDateDesc(1L);

        Assertions.assertNotNull(latestPaidTimeOff);
        Assertions.assertEquals(latestPaidTimeOff.getEmployeeId(), 1L);
        Assertions.assertEquals(latestPaidTimeOff.getStartDate(), new Date(2023));
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