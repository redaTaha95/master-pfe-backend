package employee;

import employee.domain.Employee;
import employee.domain.out.EmployeeRepository;
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
@ContextConfiguration(initializers = EmployeeRepositoryTest.DataSourceInitializer.class)
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Container
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:15.3");

    @Test
    @DisplayName("should save an employee to the database")
    public void shouldSaveAnEmployee() {
        // Create a new employee
        Employee employee = new Employee();
        employee.setFirstName("Reda");
        employee.setLastName("TAHA");
        employee.setEmail("redataha27@gmail.com");

        // Save the employee to the database
        Employee savedEmployee = employeeRepository.save(employee);

        // Assert that the saved employee has the expected values
        Assertions.assertEquals(savedEmployee.getFirstName(), "Reda");
        Assertions.assertEquals(savedEmployee.getLastName(), "TAHA");
        Assertions.assertEquals(savedEmployee.getEmail(), "redataha27@gmail.com");
    }

    @Test
    @DisplayName("should update an employee in the database")
    public void shouldUpdateAnEmployee() {
        // Create a new employee
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("johndoe@example.com");

        // Save the employee to the database
        Employee savedEmployee = employeeRepository.save(employee);

        // Update the employee's email
        savedEmployee.setEmail("newemail@example.com");

        // Save the updated employee to the database
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        // Assert that the updated employee has the new email
        Assertions.assertEquals(updatedEmployee.getEmail(), "newemail@example.com");
    }

    @Test
    @DisplayName("should retrieve an employee from the database")
    public void shouldRetrieveAnEmployee() {
        // Create a new employee
        Employee employee = new Employee();
        employee.setFirstName("Jane");
        employee.setLastName("Smith");
        employee.setEmail("janesmith@example.com");

        // Save the employee to the database
        Employee savedEmployee = employeeRepository.save(employee);

        // Retrieve the employee from the database
        Employee retrievedEmployee = employeeRepository.findById(savedEmployee.getId()).orElse(null);

        // Assert that the retrieved employee is not null
        Assertions.assertNotNull(retrievedEmployee);
        // Assert that the retrieved employee has the same ID as the saved employee
        Assertions.assertEquals(retrievedEmployee.getId(), savedEmployee.getId());
    }

    @Test
    @DisplayName("should delete an employee from the database")
    public void shouldDeleteAnEmployee() {
        // Create a new employee
        Employee employee = new Employee();
        employee.setFirstName("Alice");
        employee.setLastName("Johnson");
        employee.setEmail("alicejohnson@example.com");

        // Save the employee to the database
        Employee savedEmployee = employeeRepository.save(employee);

        // Delete the employee from the database
        employeeRepository.delete(savedEmployee);

        // Try to retrieve the deleted employee from the database
        Employee deletedEmployee = employeeRepository.findById(savedEmployee.getId()).orElse(null);

        // Assert that the deleted employee is null
        Assertions.assertNull(deletedEmployee);
    }

    @Test
    @DisplayName("should retrieve all employees from the database")
    public void shouldRetrieveAllEmployees() {
        // Create multiple employees
        Employee employee1 = new Employee();
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setEmail("johndoe@example.com");

        Employee employee2 = new Employee();
        employee2.setFirstName("Jane");
        employee2.setLastName("Smith");
        employee2.setEmail("janesmith@example.com");

        // Save the employees to the database
        employeeRepository.saveAll(List.of(employee1, employee2));

        // Retrieve all employees from the database
        List<Employee> allEmployees = employeeRepository.findAll();

        // Assert that the list of employees contains the expected number of entries
        Assertions.assertEquals(allEmployees.size(), 2);
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
