package employee.application.in;

import employee.domain.EmployeeResponse;
import employee.domain.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void shouldReturnAllEmployees() throws Exception {
        // Arrange
        List<EmployeeResponse> employeeResponses = Arrays.asList(
                new EmployeeResponse(1L, "John", "Doe", "john.doe@example.com"),
                new EmployeeResponse(2L, "Jane", "Smith", "jane.smith@example.com")
        );
        Mockito.when(employeeService.getAllEmployees()).thenReturn(employeeResponses);

        // Act and Assert
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].lastName").value("Smith"))
                .andExpect(jsonPath("$[1].email").value("jane.smith@example.com"));
    }

    @Test
    public void shouldReturnEmployeeById() throws Exception {
        // Arrange
        Long employeeId = 1L;
        EmployeeResponse employeeResponse = new EmployeeResponse(employeeId, "John", "Doe", "john.doe@example.com");
        Mockito.when(employeeService.getEmployeeById(employeeId)).thenReturn(employeeResponse);

        // Act and Assert
        mockMvc.perform(get("/employees/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employeeId.intValue()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void shouldCreateEmployee() throws Exception {
        // Arrange
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setFirstName("John");
        employeeRequest.setLastName("Doe");
        employeeRequest.setEmail("john.doe@example.com");

        EmployeeResponse createdEmployee = new EmployeeResponse(1L, "John", "Doe", "john.doe@example.com");
        Mockito.when(employeeService.createEmployee(employeeRequest)).thenReturn(createdEmployee);

        // Act and Assert
        mockMvc.perform(post("/employees")
                .content(new ObjectMapper().writeValueAsString(employeeRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void shouldUpdateEmployee() throws Exception {
        // Arrange
        Long employeeId = 1L;
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setFirstName("John");
        employeeRequest.setLastName("Doe");
        employeeRequest.setEmail("john.doe@example.com");

        EmployeeResponse updatedEmployee = new EmployeeResponse(employeeId, "John", "Doe", "john.doe@example.com");

        Mockito.when(employeeService.updateEmployee(employeeId, employeeRequest)).thenReturn(updatedEmployee);

        // Act and Assert
        mockMvc.perform(put("/employees/{id}", employeeId)
                .content(new ObjectMapper().writeValueAsString(employeeRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employeeId.intValue()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void shouldDeleteEmployee() throws Exception {
        // Arrange
        Long employeeId = 1L;

        // Act and Assert
        mockMvc.perform(delete("/employees/{id}", employeeId))
                .andExpect(status().isNoContent());

        Mockito.verify(employeeService, times(1)).deleteEmployee(employeeId);
    }
}
