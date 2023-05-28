package employee;

import employee.application.in.EmployeeRequest;
import employee.application.out.http.user.UserClient;
import employee.application.out.http.user.UserGateway;
import employee.application.out.http.user.UserRequest;
import employee.domain.Employee;
import employee.domain.EmployeeResponse;
import employee.domain.EmployeeService;
import employee.domain.out.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    @DisplayName("Should save an employee")
    public void shouldSaveAnEmployee() {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setFirstName("Reda");
        employeeRequest.setLastName("TAHA");
        employeeRequest.setEmail("redataha27@gmail.com");

        Employee savedEmployee = Employee.builder()
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .email(employeeRequest.getEmail())
                .build();

        Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);
        Mockito.when(userGateway.createUser(any())).thenReturn(any());

        EmployeeResponse employee = employeeService.createEmployee(employeeRequest);

        Assertions.assertEquals(employee.getFirstName(), employeeRequest.getFirstName());
    }

    @Test
    @DisplayName("Should return all employees")
    public void shouldReturnAllEmployees() {
        List<Employee> employees = Arrays.asList(
                new Employee(1L, "John", "Doe", "john.doe@example.com"),
                new Employee(2L, "Jane", "Smith", "jane.smith@example.com")
        );
        Mockito.when(employeeRepository.findAll()).thenReturn(employees);

        List<EmployeeResponse> employeeResponses = employeeService.getAllEmployees();

        Assertions.assertEquals(employees.size(), employeeResponses.size());
    }

    @Test
    @DisplayName("Should return employee by ID")
    public void shouldReturnEmployeeById() {
        Long employeeId = 1L;
        Employee employee = new Employee(employeeId, "John", "Doe", "john.doe@example.com");
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        EmployeeResponse employeeResponse = employeeService.getEmployeeById(employeeId);

        Assertions.assertEquals(employee.getId(), employeeResponse.getId());
        Assertions.assertEquals(employee.getFirstName(), employeeResponse.getFirstName());
        Assertions.assertEquals(employee.getLastName(), employeeResponse.getLastName());
        Assertions.assertEquals(employee.getEmail(), employeeResponse.getEmail());
    }

    @Test
    @DisplayName("Should update employee")
    public void shouldUpdateEmployee() {
        Long employeeId = 1L;
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setFirstName("John");
        employeeRequest.setLastName("Doe");
        employeeRequest.setEmail("john.doe@example.com");

        Employee existingEmployee = new Employee(employeeId, "Old First Name", "Old Last Name", "old.email@example.com");
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));

        EmployeeResponse updatedEmployeeResponse = employeeService.updateEmployee(employeeId, employeeRequest);

        Assertions.assertEquals(existingEmployee.getId(), updatedEmployeeResponse.getId());
        Assertions.assertEquals(employeeRequest.getFirstName(), updatedEmployeeResponse.getFirstName());
        Assertions.assertEquals(employeeRequest.getLastName(), updatedEmployeeResponse.getLastName());
        Assertions.assertEquals(employeeRequest.getEmail(), updatedEmployeeResponse.getEmail());
    }

    @Test
    @DisplayName("Should delete employee")
    public void shouldDeleteEmployee() {
        Long employeeId = 1L;
        Mockito.when(employeeRepository.existsById(employeeId)).thenReturn(true);

        employeeService.deleteEmployee(employeeId);

        Mockito.verify(employeeRepository).deleteById(employeeId);
    }
}
