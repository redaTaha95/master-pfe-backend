package employee.domain;

import employee.application.out.http.user.IdentityGateway;
import employee.application.out.http.user.UserRequest;
import employee.application.out.http.user.UserResponse;
import employee.domain.out.EmployeeRepository;
import employee.application.in.EmployeeRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final IdentityGateway identityGateway;

    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = getEmployeeByIdIfExists(id);
        return convertToResponse(employee);
    }

    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = buildEmployeeFromRequest(employeeRequest);
        Employee savedEmployee = employeeRepository.save(employee);

        createUser(employee);

        return convertToResponse(savedEmployee);
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeRequest) {
        Employee employee = getEmployeeByIdIfExists(id);
        updateEmployeeFromRequest(employee, employeeRequest);
        employeeRepository.save(employee);
        return convertToResponse(employee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    private Employee getEmployeeByIdIfExists(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }

    private Employee buildEmployeeFromRequest(EmployeeRequest employeeRequest) {
        return Employee.builder()
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .email(employeeRequest.getEmail())
                .build();
    }

    private void updateEmployeeFromRequest(Employee employee, EmployeeRequest employeeRequest) {
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setEmail(employeeRequest.getEmail());
    }

    private EmployeeResponse convertToResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .build();
    }

    private UserResponse createUser(Employee employee) {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(employee.getFirstName()+employee.getLastName()+employee.getId());
        userRequest.setEmail(employee.getEmail());
        userRequest.setPassword(generateSecureRandomPassword());

        return identityGateway.createUser(userRequest);
    }

    private String generateSecureRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
        return RandomStringUtils.random( 15, characters );
    }
}
