package payslip;

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
import payslip.domain.Payslip;
import payslip.domain.out.PayslipRepository;

import java.util.Date;
import java.util.List;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = PayslipRepositoryTest.DataSourceInitializer.class)
public class PayslipRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:15.3");

    @Autowired
    private PayslipRepository payslipRepository;

    @Test
    @DisplayName("should save a payslip to the database")
    public void shouldSavePayslip() {
        Payslip payslip = new Payslip();
        payslip.setPayslipDate(new Date());
        payslip.setMonthlyBasedSalary(2000.0);
        payslip.setBonusPaiment(500.0);
        payslip.setMonthlyHoursWorked(160.0);
        payslip.setPayrollId(1L);
        payslip.setMonthlyNetSalary(2500.0);

        Payslip savedPayslip = payslipRepository.save(payslip);

        Assertions.assertNotNull(savedPayslip.getId());
        Assertions.assertEquals(payslip.getPayslipDate(), savedPayslip.getPayslipDate());
        Assertions.assertEquals(payslip.getMonthlyBasedSalary(), savedPayslip.getMonthlyBasedSalary());
        Assertions.assertEquals(payslip.getBonusPaiment(), savedPayslip.getBonusPaiment());
        Assertions.assertEquals(payslip.getMonthlyHoursWorked(), savedPayslip.getMonthlyHoursWorked());
        Assertions.assertEquals(payslip.getPayrollId(), savedPayslip.getPayrollId());
        Assertions.assertEquals(payslip.getMonthlyNetSalary(), savedPayslip.getMonthlyNetSalary());
    }

    @Test
    @DisplayName("should retrieve a payslip from the database")
    public void shouldRetrievePayslip() {
        Payslip payslip = new Payslip();
        payslip.setPayslipDate(new Date());
        payslip.setMonthlyBasedSalary(2000.0);
        payslip.setBonusPaiment(500.0);
        payslip.setMonthlyHoursWorked(160.0);
        payslip.setPayrollId(1L);
        payslip.setMonthlyNetSalary(2500.0);

        Payslip savedPayslip = payslipRepository.save(payslip);

        Payslip retrievedPayslip = payslipRepository.findById(savedPayslip.getId()).orElse(null);

        Assertions.assertNotNull(retrievedPayslip);
        Assertions.assertEquals(retrievedPayslip.getId(), savedPayslip.getId());
        Assertions.assertEquals(retrievedPayslip.getPayslipDate(), savedPayslip.getPayslipDate());
        Assertions.assertEquals(retrievedPayslip.getMonthlyBasedSalary(), savedPayslip.getMonthlyBasedSalary());
        Assertions.assertEquals(retrievedPayslip.getBonusPaiment(), savedPayslip.getBonusPaiment());
        Assertions.assertEquals(retrievedPayslip.getMonthlyHoursWorked(), savedPayslip.getMonthlyHoursWorked());
        Assertions.assertEquals(retrievedPayslip.getPayrollId(), savedPayslip.getPayrollId());
        Assertions.assertEquals(retrievedPayslip.getMonthlyNetSalary(), savedPayslip.getMonthlyNetSalary());
    }

    @Test
    @DisplayName("should delete a payslip from the database")
    public void shouldDeletePayslip() {
        Payslip payslip = new Payslip();
        payslip.setPayslipDate(new Date());
        payslip.setMonthlyBasedSalary(2000.0);
        payslip.setBonusPaiment(500.0);
        payslip.setMonthlyHoursWorked(160.0);
        payslip.setPayrollId(1L);
        payslip.setMonthlyNetSalary(2500.0);

        Payslip savedPayslip = payslipRepository.save(payslip);

        payslipRepository.delete(savedPayslip);

        Payslip deletedPayslip = payslipRepository.findById(savedPayslip.getId()).orElse(null);

        Assertions.assertNull(deletedPayslip);
    }

    @Test
    @DisplayName("should retrieve all payslips from the database")
    public void shouldRetrieveAllPayslips() {
        Payslip payslip1 = new Payslip();
        payslip1.setPayslipDate(new Date());
        payslip1.setMonthlyBasedSalary(2000.0);
        payslip1.setBonusPaiment(500.0);
        payslip1.setMonthlyHoursWorked(160.0);
        payslip1.setPayrollId(1L);
        payslip1.setMonthlyNetSalary(2500.0);

        Payslip payslip2 = new Payslip();
        payslip2.setPayslipDate(new Date());
        payslip2.setMonthlyBasedSalary(3000.0);
        payslip2.setBonusPaiment(1000.0);
        payslip2.setMonthlyHoursWorked(180.0);
        payslip2.setPayrollId(2L);
        payslip2.setMonthlyNetSalary(3500.0);

        payslipRepository.saveAll(List.of(payslip1, payslip2));

        List<Payslip> allPayslips = payslipRepository.findAll();

        Assertions.assertEquals(allPayslips.size(), 2);
    }

    @Test
    @DisplayName("should retrieve payslips by payroll ID")
    public void shouldRetrievePayslipsByPayrollId() {
        Payslip payslip1 = new Payslip();
        payslip1.setPayslipDate(new Date());
        payslip1.setMonthlyBasedSalary(2000.0);
        payslip1.setBonusPaiment(500.0);
        payslip1.setMonthlyHoursWorked(160.0);
        payslip1.setPayrollId(1L);
        payslip1.setMonthlyNetSalary(2500.0);

        Payslip payslip2 = new Payslip();
        payslip2.setPayslipDate(new Date());
        payslip2.setMonthlyBasedSalary(3000.0);
        payslip2.setBonusPaiment(1000.0);
        payslip2.setMonthlyHoursWorked(180.0);
        payslip2.setPayrollId(2L);
        payslip2.setMonthlyNetSalary(3500.0);

        payslipRepository.saveAll(List.of(payslip1, payslip2));

        List<Payslip> payslips = payslipRepository.findByPayrollId(1L);

        Assertions.assertEquals(payslips.size(), 1);
        Assertions.assertEquals(payslips.get(0).getPayrollId(), 1L);
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