package paidTimeOff.application.in;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import paidTimeOff.application.out.http.pto.EmployeeResponse;
import paidTimeOff.domain.PaidTimeOffResponse;
import paidTimeOff.domain.PaidTimeService;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaidTimeOffController.class)
public class PaidTimeOffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaidTimeService paidTimeOffService;

    @Test
    public void shouldReturnAllPaidTimeOffs() throws Exception {
        // Arrange
        List<PaidTimeOffResponse> paidTimeOffResponses = Arrays.asList(
                PaidTimeOffResponse.builder()
                        .id(1L)
                        .details("Vacation")
                        .startDate(new Date())
                        .endDate(new Date())
                        .employee(new EmployeeResponse(1L, "John", "Doe", "john.doe@example.com"))
                        .build(),
                PaidTimeOffResponse.builder()
                        .id(2L)
                        .details("Sick Leave")
                        .startDate(new Date())
                        .endDate(new Date())
                        .employee(new EmployeeResponse(2L, "Jane", "Smith", "jane.smith@example.com"))
                        .build()
        );
        Mockito.when(paidTimeOffService.getAllPaidTimeOff()).thenReturn(paidTimeOffResponses);

        // Act and Assert
        mockMvc.perform(get("/paidTimesOff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].details").value("Vacation"))
                .andExpect(jsonPath("$[0].startDate").isNotEmpty())
                .andExpect(jsonPath("$[0].endDate").isNotEmpty())
                .andExpect(jsonPath("$[0].employee.id").value(1))
                .andExpect(jsonPath("$[0].employee.firstName").value("John"))
                .andExpect(jsonPath("$[0].employee.lastName").value("Doe"))
                .andExpect(jsonPath("$[0].employee.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].details").value("Sick Leave"))
                .andExpect(jsonPath("$[1].startDate").isNotEmpty())
                .andExpect(jsonPath("$[1].endDate").isNotEmpty())
                .andExpect(jsonPath("$[1].employee.id").value(2))
                .andExpect(jsonPath("$[1].employee.firstName").value("Jane"))
                .andExpect(jsonPath("$[1].employee.lastName").value("Smith"))
                .andExpect(jsonPath("$[1].employee.email").value("jane.smith@example.com"));
    }

    @Test
    public void shouldReturnPaidTimeOffById() throws Exception {
        // Arrange
        Long paidTimeOffId = 1L;
        PaidTimeOffResponse paidTimeOffResponse = PaidTimeOffResponse.builder()
                .id(paidTimeOffId)
                .details("Vacation")
                .startDate(new Date())
                .endDate(new Date())
                .employee(new EmployeeResponse(1L, "John", "Doe", "john.doe@example.com"))
                .build();
        Mockito.when(paidTimeOffService.getPaidTimeOffById(paidTimeOffId)).thenReturn(paidTimeOffResponse);

        // Act and Assert
        mockMvc.perform(get("/paidTimesOff/{id}", paidTimeOffId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(paidTimeOffId))
                .andExpect(jsonPath("$.details").value("Vacation"))
                .andExpect(jsonPath("$.startDate").isNotEmpty())
                .andExpect(jsonPath("$.endDate").isNotEmpty())
                .andExpect(jsonPath("$.employee.id").value(1))
                .andExpect(jsonPath("$.employee.firstName").value("John"))
                .andExpect(jsonPath("$.employee.lastName").value("Doe"))
                .andExpect(jsonPath("$.employee.email").value("john.doe@example.com"));
    }

    @Test
    public void shouldCreatePaidTimeOff() throws Exception {
        // Arrange
        PaidTimeOffRequest paidTimeOffRequest = PaidTimeOffRequest.builder()
                .details("Vacation")
                .startDate(new Date())
                .endDate(new Date())
                .employeeId(1L)
                .build();
        Long createdPaidTimeOffId = 1L;
        PaidTimeOffResponse createdPaidTimeOffResponse = PaidTimeOffResponse.builder()
                .id(createdPaidTimeOffId)
                .details("Vacation")
                .startDate(new Date())
                .endDate(new Date())
                .employee(new EmployeeResponse(1L, "John", "Doe", "john.doe@example.com"))
                .build();
        Mockito.when(paidTimeOffService.createPaidTimeOff(paidTimeOffRequest)).thenReturn(createdPaidTimeOffResponse);

        // Act and Assert
        mockMvc.perform(post("/paidTimesOff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(paidTimeOffRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(createdPaidTimeOffId))
                .andExpect(jsonPath("$.details").value("Vacation"))
                .andExpect(jsonPath("$.startDate").isNotEmpty())
                .andExpect(jsonPath("$.endDate").isNotEmpty())
                .andExpect(jsonPath("$.employee.id").value(1))
                .andExpect(jsonPath("$.employee.firstName").value("John"))
                .andExpect(jsonPath("$.employee.lastName").value("Doe"))
                .andExpect(jsonPath("$.employee.email").value("john.doe@example.com"));
    }

    @Test
    public void shouldUpdatePaidTimeOff() throws Exception {
        // Arrange
        Long paidTimeOffId = 1L;
        PaidTimeOffRequest paidTimeOffRequest = PaidTimeOffRequest.builder()
                .details("Sick Leave")
                .startDate(new Date())
                .endDate(new Date())
                .employeeId(2L)
                .build();
        PaidTimeOffResponse updatedPaidTimeOffResponse = PaidTimeOffResponse.builder()
                .id(paidTimeOffId)
                .details("Sick Leave")
                .startDate(new Date())
                .endDate(new Date())
                .employee(new EmployeeResponse(2L, "Jane", "Smith", "jane.smith@example.com"))
                .build();
        Mockito.when(paidTimeOffService.updatePaidTimeOff(paidTimeOffId, paidTimeOffRequest))
                .thenReturn(updatedPaidTimeOffResponse);

        // Act and Assert
        mockMvc.perform(put("/paidTimesOff/{id}", paidTimeOffId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(paidTimeOffRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(paidTimeOffId))
                .andExpect(jsonPath("$.details").value("Sick Leave"))
                .andExpect(jsonPath("$.startDate").isNotEmpty())
                .andExpect(jsonPath("$.endDate").isNotEmpty())
                .andExpect(jsonPath("$.employee.id").value(2))
                .andExpect(jsonPath("$.employee.firstName").value("Jane"))
                .andExpect(jsonPath("$.employee.lastName").value("Smith"))
                .andExpect(jsonPath("$.employee.email").value("jane.smith@example.com"));
    }

    @Test
    public void shouldDeletePaidTimeOff() throws Exception {
        // Arrange
        Long paidTimeOffId = 1L;

        // Act and Assert
        mockMvc.perform(delete("/paidTimesOff/{id}", paidTimeOffId))
                .andExpect(status().isNoContent());

        Mockito.verify(paidTimeOffService, Mockito.times(1)).deletePaidTimeOff(paidTimeOffId);
    }

    // Utility method to convert an object to JSON string
    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
