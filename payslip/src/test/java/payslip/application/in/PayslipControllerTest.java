package payslip.application.in;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import payslip.domain.PayslipResponse;
import payslip.domain.PayslipService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PayslipController.class)
public class PayslipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PayslipService payslipService;

    @Test
    public void shouldReturnAllPayslips() throws Exception {
        // Arrange
        List<PayslipResponse> payslipResponses = Arrays.asList(
                PayslipResponse.builder()
                        .id(1L)
                        .payslipDate(new Date())
                        .monthlyNetSalary(5000.0)
                        .monthlyBasedSalary(4000.0)
                        .monthlyHoursWorked(160.0)
                        .bonusPaiment(1000.0)
                        .payrollId(1L)
                        .build(),
                PayslipResponse.builder()
                        .id(2L)
                        .payslipDate(new Date())
                        .monthlyNetSalary(6000.0)
                        .monthlyBasedSalary(5000.0)
                        .monthlyHoursWorked(160.0)
                        .bonusPaiment(1000.0)
                        .payrollId(2L)
                        .build()
        );
        Mockito.when(payslipService.getAllPayslips()).thenReturn(payslipResponses);

        // Act and Assert
        mockMvc.perform(get("/payslips"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].payslipDate").isNotEmpty())
                .andExpect(jsonPath("$[0].monthlyNetSalary").value(5000.0))
                .andExpect(jsonPath("$[0].monthlyBasedSalary").value(4000.0))
                .andExpect(jsonPath("$[0].monthlyHoursWorked").value(160.0))
                .andExpect(jsonPath("$[0].bonusPaiment").value(1000.0))
                .andExpect(jsonPath("$[0].payrollId").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].payslipDate").isNotEmpty())
                .andExpect(jsonPath("$[1].monthlyNetSalary").value(6000.0))
                .andExpect(jsonPath("$[1].monthlyBasedSalary").value(5000.0))
                .andExpect(jsonPath("$[1].monthlyHoursWorked").value(160.0))
                .andExpect(jsonPath("$[1].bonusPaiment").value(1000.0))
                .andExpect(jsonPath("$[1].payrollId").value(2));
    }

    @Test
    public void shouldReturnPayslipById() throws Exception {
        // Arrange
        Long payslipId = 1L;
        PayslipResponse payslipResponse = PayslipResponse.builder()
                .id(payslipId)
                .payslipDate(new Date())
                .monthlyNetSalary(5000.0)
                .monthlyBasedSalary(4000.0)
                .monthlyHoursWorked(160.0)
                .bonusPaiment(1000.0)
                .payrollId(1L)
                .build();
        Mockito.when(payslipService.getPayslipById(payslipId)).thenReturn(payslipResponse);

        // Act and Assert
        mockMvc.perform(get("/payslips/{id}", payslipId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(payslipId))
                .andExpect(jsonPath("$.payslipDate").isNotEmpty())
                .andExpect(jsonPath("$.monthlyNetSalary").value(5000.0))
                .andExpect(jsonPath("$.monthlyBasedSalary").value(4000.0))
                .andExpect(jsonPath("$.monthlyHoursWorked").value(160.0))
                .andExpect(jsonPath("$.bonusPaiment").value(1000.0))
                .andExpect(jsonPath("$.payrollId").value(1));
    }

    @Test
    public void shouldCreatePayslip() throws Exception {
        // Arrange
        PayslipRequest payslipRequest = PayslipRequest.builder()
                .payslipDate(new Date())
                .monthlyNetSalary(5000.0)
                .monthlyBasedSalary(4000.0)
                .monthlyHoursWorked(160.0)
                .bonusPaiment(1000.0)
                .payrollId(1L)
                .build();
        Long createdPayslipId = 1L;
        PayslipResponse createdPayslipResponse = PayslipResponse.builder()
                .id(createdPayslipId)
                .payslipDate(payslipRequest.getPayslipDate())
                .monthlyNetSalary(payslipRequest.getMonthlyNetSalary())
                .monthlyBasedSalary(payslipRequest.getMonthlyBasedSalary())
                .monthlyHoursWorked(payslipRequest.getMonthlyHoursWorked())
                .bonusPaiment(payslipRequest.getBonusPaiment())
                .payrollId(payslipRequest.getPayrollId())
                .build();
        Mockito.when(payslipService.createPayslip(payslipRequest)).thenReturn(createdPayslipResponse);

        // Act and Assert
        mockMvc.perform(post("/payslips")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(payslipRequest)))
                .andExpect(status().isCreated()) // Update the assertion to expect 201
                .andExpect(jsonPath("$.id").value(createdPayslipId))
                .andExpect(jsonPath("$.payslipDate").isNotEmpty())
                .andExpect(jsonPath("$.monthlyNetSalary").value(5000.0))
                .andExpect(jsonPath("$.monthlyBasedSalary").value(4000.0))
                .andExpect(jsonPath("$.monthlyHoursWorked").value(160.0))
                .andExpect(jsonPath("$.bonusPaiment").value(1000.0))
                .andExpect(jsonPath("$.payrollId").value(1));
    }

    @Test
    public void shouldGetPayslipsByPayrollId() throws Exception {
        // Arrange
        Long payrollId = 1L;
        List<PayslipResponse> payslipResponses = Arrays.asList(
                PayslipResponse.builder()
                        .id(1L)
                        .payslipDate(new Date())
                        .monthlyNetSalary(5000.0)
                        .monthlyBasedSalary(4000.0)
                        .monthlyHoursWorked(160.0)
                        .bonusPaiment(1000.0)
                        .payrollId(payrollId)
                        .build(),
                PayslipResponse.builder()
                        .id(2L)
                        .payslipDate(new Date())
                        .monthlyNetSalary(5500.0)
                        .monthlyBasedSalary(4500.0)
                        .monthlyHoursWorked(170.0)
                        .bonusPaiment(1200.0)
                        .payrollId(payrollId)
                        .build()
        );
        Mockito.when(payslipService.getPayslipsByPayrollId(payrollId)).thenReturn(payslipResponses);

        // Act and Assert
        mockMvc.perform(get("/payslips/payroll/{payrollId}", payrollId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].payslipDate").isNotEmpty())
                .andExpect(jsonPath("$[0].monthlyNetSalary").value(5000.0))
                .andExpect(jsonPath("$[0].monthlyBasedSalary").value(4000.0))
                .andExpect(jsonPath("$[0].monthlyHoursWorked").value(160.0))
                .andExpect(jsonPath("$[0].bonusPaiment").value(1000.0))
                .andExpect(jsonPath("$[0].payrollId").value(payrollId))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].payslipDate").isNotEmpty())
                .andExpect(jsonPath("$[1].monthlyNetSalary").value(5500.0))
                .andExpect(jsonPath("$[1].monthlyBasedSalary").value(4500.0))
                .andExpect(jsonPath("$[1].monthlyHoursWorked").value(170.0))
                .andExpect(jsonPath("$[1].bonusPaiment").value(1200.0))
                .andExpect(jsonPath("$[1].payrollId").value(payrollId));
    }


    @Test
    public void shouldUpdatePayslip() throws Exception {
        // Arrange
        Long payslipId = 1L;
        PayslipRequest payslipRequest = PayslipRequest.builder()
                .payslipDate(new Date())
                .monthlyNetSalary(5500.0)
                .monthlyBasedSalary(4500.0)
                .monthlyHoursWorked(170.0)
                .bonusPaiment(1200.0)
                .payrollId(1L)
                .build();
        PayslipResponse updatedPayslipResponse = PayslipResponse.builder()
                .id(payslipId)
                .payslipDate(payslipRequest.getPayslipDate())
                .monthlyNetSalary(payslipRequest.getMonthlyNetSalary())
                .monthlyBasedSalary(payslipRequest.getMonthlyBasedSalary())
                .monthlyHoursWorked(payslipRequest.getMonthlyHoursWorked())
                .bonusPaiment(payslipRequest.getBonusPaiment())
                .payrollId(payslipRequest.getPayrollId())
                .build();
        Mockito.when(payslipService.updatePayslip(payslipId, payslipRequest))
                .thenReturn(updatedPayslipResponse);

        // Act and Assert
        mockMvc.perform(put("/payslips/{id}", payslipId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(payslipRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(payslipId))
                .andExpect(jsonPath("$.payslipDate").isNotEmpty())
                .andExpect(jsonPath("$.monthlyNetSalary").value(5500.0))
                .andExpect(jsonPath("$.monthlyBasedSalary").value(4500.0))
                .andExpect(jsonPath("$.monthlyHoursWorked").value(170.0))
                .andExpect(jsonPath("$.bonusPaiment").value(1200.0))
                .andExpect(jsonPath("$.payrollId").value(1));
    }
    // Helper method to convert objects to JSON string
    private static String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}