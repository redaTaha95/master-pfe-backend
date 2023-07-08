package payroll;

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
import payroll.domain.Payroll;
import payroll.domain.out.PayrollRepository;

import java.util.Date;
import java.util.List;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = PayrollRepositoryTest.DataSourceInitializer.class)
public class PayrollRepositoryTest {
    @Autowired
    private PayrollRepository payrollRepository;
    @Container
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:15.3");

    @Test
    @DisplayName("should save a payroll to the database")
    public void shouldSaveAPayroll() {
        Payroll payroll = new Payroll();
        payroll.setPayrollDate(new Date());
        payroll.setMonthlyBasedSalary(5000.0);
        payroll.setBonusPaiment(1000.0);
        payroll.setMonthlyHoursWorked(160.0);
        payroll.setEmployeeId(1L);

        Payroll savedPayroll = payrollRepository.save(payroll);

        Assertions.assertEquals(savedPayroll.getPayrollDate(), payroll.getPayrollDate());
        Assertions.assertEquals(savedPayroll.getMonthlyBasedSalary(), payroll.getMonthlyBasedSalary());
        Assertions.assertEquals(savedPayroll.getBonusPaiment(), payroll.getBonusPaiment());
        Assertions.assertEquals(savedPayroll.getMonthlyHoursWorked(), payroll.getMonthlyHoursWorked());
        Assertions.assertEquals(savedPayroll.getEmployeeId(), payroll.getEmployeeId());
    }

    @Test
    @DisplayName("should update a payroll in the database")
    public void shouldUpdateAPayroll() {
        Payroll payroll = new Payroll();
        payroll.setPayrollDate(new Date());
        payroll.setMonthlyBasedSalary(5000.0);
        payroll.setBonusPaiment(1000.0);
        payroll.setMonthlyHoursWorked(160.0);
        payroll.setEmployeeId(1L);

        Payroll savedPayroll = payrollRepository.save(payroll);

        savedPayroll.setBonusPaiment(2000.0);

        Payroll updatedPayroll = payrollRepository.save(savedPayroll);

        Assertions.assertEquals(updatedPayroll.getBonusPaiment(), 2000.0);
    }

    @Test
    @DisplayName("should retrieve a payroll from the database")
    public void shouldRetrieveAPayroll() {
        // Create a new payroll
        Payroll payroll = new Payroll();
        payroll.setPayrollDate(new Date());
        payroll.setMonthlyBasedSalary(5000.0);
        payroll.setBonusPaiment(1000.0);
        payroll.setMonthlyHoursWorked(160.0);
        payroll.setEmployeeId(1L);

        Payroll savedPayroll = payrollRepository.save(payroll);

        Payroll retrievedPayroll = payrollRepository.findById(savedPayroll.getId()).orElse(null);

        Assertions.assertNotNull(retrievedPayroll);
        Assertions.assertEquals(retrievedPayroll.getId(), savedPayroll.getId());
    }

    @Test
    public void shouldRetrievePayrollByEmployeeId() {
        Payroll payroll = new Payroll();
        payroll.setPayrollDate(new Date());
        payroll.setMonthlyBasedSalary(5000.0);
        payroll.setBonusPaiment(1000.0);
        payroll.setMonthlyHoursWorked(160.0);
        payroll.setEmployeeId(1L);

        Payroll savedPayroll = payrollRepository.save(payroll);

        List<Payroll> retrievedPayrolls = payrollRepository.findByEmployeeId(savedPayroll.getEmployeeId());

        Assertions.assertNotNull(retrievedPayrolls);
        Assertions.assertEquals(savedPayroll.getId(), retrievedPayrolls.get(0).getId());
    }

    @Test
    public void shouldRetrievePayrollWithLatestPayrollByEmployeeId() {
        Payroll payroll1 = new Payroll();
        payroll1.setPayrollDate(new Date(2022));
        payroll1.setMonthlyBasedSalary(5000.0);
        payroll1.setBonusPaiment(1000.0);
        payroll1.setMonthlyHoursWorked(160.0);
        payroll1.setEmployeeId(1L);

        Payroll payroll2 = new Payroll();
        payroll2.setPayrollDate(new Date(2023));
        payroll2.setMonthlyBasedSalary(6000.0);
        payroll2.setBonusPaiment(2000.0);
        payroll2.setMonthlyHoursWorked(160.0);
        payroll2.setEmployeeId(1L);

        payrollRepository.save(payroll1);
        payrollRepository.save(payroll2);

        Payroll latestPayroll = payrollRepository.findFirstByEmployeeIdOrderByPayrollDateDesc(1L);

        Assertions.assertNotNull(latestPayroll);
        Assertions.assertEquals(payroll2.getId(), latestPayroll.getId());
    }

    @Test
    @DisplayName("should delete a payroll from the database")
    public void shouldDeleteAPayroll() {
        Payroll payroll = new Payroll();
        payroll.setPayrollDate(new Date());
        payroll.setMonthlyBasedSalary(5000.0);
        payroll.setBonusPaiment(1000.0);
        payroll.setMonthlyHoursWorked(160.0);
        payroll.setEmployeeId(1L);

        Payroll savedPayroll = payrollRepository.save(payroll);

        payrollRepository.delete(savedPayroll);

        Payroll deletedPayroll = payrollRepository.findById(savedPayroll.getId()).orElse(null);

        Assertions.assertNull(deletedPayroll);
    }

    @Test
    @DisplayName("should retrieve all payrolls from the database")
    public void shouldRetrieveAllPayrolls() {
        Payroll payroll1 = new Payroll();
        payroll1.setPayrollDate(new Date());
        payroll1.setMonthlyBasedSalary(5000.0);
        payroll1.setBonusPaiment(1000.0);
        payroll1.setMonthlyHoursWorked(160.0);
        payroll1.setEmployeeId(1L);

        Payroll payroll2 = new Payroll();
        payroll2.setPayrollDate(new Date());
        payroll2.setMonthlyBasedSalary(6000.0);
        payroll2.setBonusPaiment(2000.0);
        payroll2.setMonthlyHoursWorked(160.0);
        payroll2.setEmployeeId(2L);

        payrollRepository.saveAll(List.of(payroll1, payroll2));

        List<Payroll> allPayrolls = payrollRepository.findAll();

        Assertions.assertEquals(allPayrolls.size(), 2);
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