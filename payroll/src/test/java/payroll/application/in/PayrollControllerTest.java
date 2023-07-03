package payroll.application.in;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import payroll.application.out.http.payroll.EmployeeResponse;
import payroll.domain.PayrollResponse;
import payroll.domain.PayrollService;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PayrollController.class)
public class PayrollControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PayrollService payrollService;

    @Test
    public void shouldReturnAllPayrolls() throws Exception {
        // Arrange
        List<PayrollResponse> payrollResponses = Arrays.asList(
                PayrollResponse.builder()
                        .id(1L)
                        .payrollDate(new Date())
                        .monthlyNetSalary(2000.0)
                        .monthlyBasedSalary(2000.0)
                        .monthlyHoursWorked(160.0)
                        .bonusPaiment(500.0)
                        .employee(new EmployeeResponse(1L, "John", "Doe", "john.doe@example.com"))
                        .build(),
                PayrollResponse.builder()
                        .id(2L)
                        .payrollDate(new Date())
                        .monthlyNetSalary(2500.0)
                        .monthlyBasedSalary(2500.0)
                        .monthlyHoursWorked(180.0)
                        .bonusPaiment(300.0)
                        .employee(new EmployeeResponse(2L, "Jane", "Smith", "jane.smith@example.com"))
                        .build()
        );
        Mockito.when(payrollService.getAllPayrolls()).thenReturn(payrollResponses);

        // Act and Assert
        mockMvc.perform(get("/payrolls"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].payrollDate").isNotEmpty())
                .andExpect(jsonPath("$[0].monthlyNetSalary").value(2000.0))
                .andExpect(jsonPath("$[0].monthlyBasedSalary").value(2000.0))
                .andExpect(jsonPath("$[0].monthlyHoursWorked").value(160.0))
                .andExpect(jsonPath("$[0].bonusPaiment").value(500.0))
                .andExpect(jsonPath("$[0].employee.id").value(1))
                .andExpect(jsonPath("$[0].employee.firstName").value("John"))
                .andExpect(jsonPath("$[0].employee.lastName").value("Doe"))
                .andExpect(jsonPath("$[0].employee.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].payrollDate").isNotEmpty())
                .andExpect(jsonPath("$[1].monthlyNetSalary").value(2500.0))
                .andExpect(jsonPath("$[1].monthlyBasedSalary").value(2500.0))
                .andExpect(jsonPath("$[1].monthlyHoursWorked").value(180.0))
                .andExpect(jsonPath("$[1].bonusPaiment").value(300.0))
                .andExpect(jsonPath("$[1].employee.id").value(2))
                .andExpect(jsonPath("$[1].employee.firstName").value("Jane"))
                .andExpect(jsonPath("$[1].employee.lastName").value("Smith"))
                .andExpect(jsonPath("$[1].employee.email").value("jane.smith@example.com"));
    }

    @Test
    public void shouldReturnPayrollById() throws Exception {
        // Arrange
        Long payrollId = 1L;
        PayrollResponse payrollResponse = PayrollResponse.builder()
                .id(payrollId)
                .payrollDate(new Date())
                .monthlyNetSalary(2000.0)
                .monthlyBasedSalary(2000.0)
                .monthlyHoursWorked(160.0)
                .bonusPaiment(500.0)
                .employee(new EmployeeResponse(1L, "John", "Doe", "john.doe@example.com"))
                .build();
        Mockito.when(payrollService.getPayrollById(payrollId)).thenReturn(payrollResponse);

        // Act and Assert
        mockMvc.perform(get("/payrolls/{id}", payrollId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(payrollId))
                .andExpect(jsonPath("$.payrollDate").isNotEmpty())
                .andExpect(jsonPath("$.monthlyNetSalary").value(2000.0))
                .andExpect(jsonPath("$.monthlyBasedSalary").value(2000.0))
                .andExpect(jsonPath("$.monthlyHoursWorked").value(160.0))
                .andExpect(jsonPath("$.bonusPaiment").value(500.0))
                .andExpect(jsonPath("$.employee.id").value(1))
                .andExpect(jsonPath("$.employee.firstName").value("John"))
                .andExpect(jsonPath("$.employee.lastName").value("Doe"))
                .andExpect(jsonPath("$.employee.email").value("john.doe@example.com"));
    }

    @Test
    public void shouldReturnPayrollsByEmployeeId() throws Exception {
        // Arrange
        Long employeeId = 1L;
        List<PayrollResponse> payrollResponses = Arrays.asList(
                PayrollResponse.builder()
                        .id(1L)
                        .payrollDate(new Date())
                        .monthlyNetSalary(2000.0)
                        .monthlyBasedSalary(2000.0)
                        .monthlyHoursWorked(160.0)
                        .bonusPaiment(500.0)
                        .employee(new EmployeeResponse(1L, "John", "Doe", "john.doe@example.com"))
                        .build(),
                PayrollResponse.builder()
                        .id(2L)
                        .payrollDate(new Date())
                        .monthlyNetSalary(2500.0)
                        .monthlyBasedSalary(2500.0)
                        .monthlyHoursWorked(180.0)
                        .bonusPaiment(300.0)
                        .employee(new EmployeeResponse(1L, "John", "Doe", "john.doe@example.com"))
                        .build()
        );
        Mockito.when(payrollService.getPayrollsByEmployeeId(employeeId)).thenReturn(payrollResponses);

        // Act and Assert
        mockMvc.perform(get("/payrolls/employee/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].payrollDate").isNotEmpty())
                .andExpect(jsonPath("$[0].monthlyNetSalary").value(2000.0))
                .andExpect(jsonPath("$[0].monthlyBasedSalary").value(2000.0))
                .andExpect(jsonPath("$[0].monthlyHoursWorked").value(160.0))
                .andExpect(jsonPath("$[0].bonusPaiment").value(500.0))
                .andExpect(jsonPath("$[0].employee.id").value(1))
                .andExpect(jsonPath("$[0].employee.firstName").value("John"))
                .andExpect(jsonPath("$[0].employee.lastName").value("Doe"))
                .andExpect(jsonPath("$[0].employee.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].payrollDate").isNotEmpty())
                .andExpect(jsonPath("$[1].monthlyNetSalary").value(2500.0))
                .andExpect(jsonPath("$[1].monthlyBasedSalary").value(2500.0))
                .andExpect(jsonPath("$[1].monthlyHoursWorked").value(180.0))
                .andExpect(jsonPath("$[1].bonusPaiment").value(300.0))
                .andExpect(jsonPath("$[1].employee.id").value(1))
                .andExpect(jsonPath("$[1].employee.firstName").value("John"))
                .andExpect(jsonPath("$[1].employee.lastName").value("Doe"))
                .andExpect(jsonPath("$[1].employee.email").value("john.doe@example.com"));
    }

    @Test
    public void shouldCreatePayroll() throws Exception {
        // Arrange
        PayrollRequest payrollRequest = new PayrollRequest();
        payrollRequest.setPayrollDate(new Date());
        payrollRequest.setMonthlyNetSalary(2000.0);
        payrollRequest.setMonthlyBasedSalary(2000.0);
        payrollRequest.setMonthlyHoursWorked(160.0);
        payrollRequest.setBonusPaiment(500.0);
        payrollRequest.setEmployeeId(1L);

        PayrollResponse createdPayroll = PayrollResponse.builder()
                .id(1L)
                .payrollDate(new Date())
                .monthlyNetSalary(2000.0)
                .monthlyBasedSalary(2000.0)
                .monthlyHoursWorked(160.0)
                .bonusPaiment(500.0)
                .employee(new EmployeeResponse(1L, "John", "Doe", "john.doe@example.com"))
                .build();
        Mockito.when(payrollService.createPayroll(payrollRequest)).thenReturn(createdPayroll);

        // Act and Assert
        mockMvc.perform(post("/payrolls")
                .content(new ObjectMapper().writeValueAsString(payrollRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.payrollDate").isNotEmpty())
                .andExpect(jsonPath("$.monthlyNetSalary").value(2000.0))
                .andExpect(jsonPath("$.monthlyBasedSalary").value(2000.0))
                .andExpect(jsonPath("$.monthlyHoursWorked").value(160.0))
                .andExpect(jsonPath("$.bonusPaiment").value(500.0))
                .andExpect(jsonPath("$.employee.id").value(1))
                .andExpect(jsonPath("$.employee.firstName").value("John"))
                .andExpect(jsonPath("$.employee.lastName").value("Doe"))
                .andExpect(jsonPath("$.employee.email").value("john.doe@example.com"));
    }

    @Test
    public void shouldUpdatePayroll() throws Exception {
        // Arrange
        Long payrollId = 1L;
        PayrollRequest payrollRequest = new PayrollRequest();
        payrollRequest.setPayrollDate(new Date());
        payrollRequest.setMonthlyNetSalary(2000.0);
        payrollRequest.setMonthlyBasedSalary(2000.0);
        payrollRequest.setMonthlyHoursWorked(160.0);
        payrollRequest.setBonusPaiment(500.0);
        payrollRequest.setEmployeeId(1L);

        PayrollResponse updatedPayroll = PayrollResponse.builder()
                .id(payrollId)
                .payrollDate(new Date())
                .monthlyNetSalary(2000.0)
                .monthlyBasedSalary(2000.0)
                .monthlyHoursWorked(160.0)
                .bonusPaiment(500.0)
                .employee(new EmployeeResponse(1L, "John", "Doe", "john.doe@example.com"))
                .build();

        Mockito.when(payrollService.updatePayroll(payrollId, payrollRequest)).thenReturn(updatedPayroll);

        // Act and Assert
        mockMvc.perform(put("/payrolls/{id}", payrollId)
                .content(new ObjectMapper().writeValueAsString(payrollRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(payrollId))
                .andExpect(jsonPath("$.payrollDate").isNotEmpty())
                .andExpect(jsonPath("$.monthlyNetSalary").value(2000.0))
                .andExpect(jsonPath("$.monthlyBasedSalary").value(2000.0))
                .andExpect(jsonPath("$.monthlyHoursWorked").value(160.0))
                .andExpect(jsonPath("$.bonusPaiment").value(500.0))
                .andExpect(jsonPath("$.employee.id").value(1))
                .andExpect(jsonPath("$.employee.firstName").value("John"))
                .andExpect(jsonPath("$.employee.lastName").value("Doe"))
                .andExpect(jsonPath("$.employee.email").value("john.doe@example.com"));
    }

    @Test
    public void shouldDeletePayroll() throws Exception {
        // Arrange
        Long payrollId = 1L;
        Mockito.doNothing().when(payrollService).deletePayroll(payrollId);

        mockMvc.perform(delete("/payrolls/{id}", payrollId))
                .andExpect(status().isNoContent());
    }
}