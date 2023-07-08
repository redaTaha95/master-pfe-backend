package paidTimeOff;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import paidTimeOff.application.in.PaidTimeOffRequest;
import paidTimeOff.application.out.http.pto.EmployeeGateway;
import paidTimeOff.application.out.http.pto.EmployeeResponse;
import paidTimeOff.domain.PaidTimeOff;
import paidTimeOff.domain.PaidTimeOffResponse;
import paidTimeOff.domain.PaidTimeService;
import paidTimeOff.domain.out.PaidTimeOffRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PaidTimeOffServiceTest {
    @Mock
    private PaidTimeOffRepository paidTimeOffRepository;

    @Mock
    private EmployeeGateway employeeGateway;

    @InjectMocks
    private PaidTimeService paidTimeOffService;

    @Test
    @DisplayName("Should create a paid time off")
    public void shouldCreatePaidTimeOff() {
        PaidTimeOffRequest paidTimeOffRequest = new PaidTimeOffRequest();
        paidTimeOffRequest.setDetails("Vacation");
        paidTimeOffRequest.setStartDate(new Date());
        paidTimeOffRequest.setEndDate(new Date());
        paidTimeOffRequest.setEmployeeId(1L);

        EmployeeResponse employee = new EmployeeResponse();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");

        PaidTimeOff savedPaidTimeOff = PaidTimeOff.builder()
                .id(1L)
                .details(paidTimeOffRequest.getDetails())
                .startDate(paidTimeOffRequest.getStartDate())
                .endDate(paidTimeOffRequest.getEndDate())
                .employeeId(paidTimeOffRequest.getEmployeeId())
                .build();

        Mockito.when(employeeGateway.getEmployee(1L)).thenReturn(employee);
        Mockito.when(paidTimeOffRepository.save(Mockito.any(PaidTimeOff.class))).thenReturn(savedPaidTimeOff);

        PaidTimeOffResponse paidTimeOffResponse = paidTimeOffService.createPaidTimeOff(paidTimeOffRequest);

        Assertions.assertEquals(savedPaidTimeOff.getId(), paidTimeOffResponse.getId());
        Assertions.assertEquals(paidTimeOffRequest.getDetails(), paidTimeOffResponse.getDetails());
        Assertions.assertEquals(paidTimeOffRequest.getStartDate(), paidTimeOffResponse.getStartDate());
        Assertions.assertEquals(paidTimeOffRequest.getEndDate(), paidTimeOffResponse.getEndDate());
        Assertions.assertEquals(employee, paidTimeOffResponse.getEmployee());
    }

    @Test
    @DisplayName("Should return paid time offs by employee ID")
    public void shouldReturnPaidTimeOffsByEmployeeId() {
        List<PaidTimeOff> paidTimeOffs = Arrays.asList(
                PaidTimeOff.builder()
                        .id(1L)
                        .details("Vacation")
                        .startDate(new Date())
                        .endDate(new Date())
                        .employeeId(1L)
                        .build(),
                PaidTimeOff.builder()
                        .id(2L)
                        .details("Sick leave")
                        .startDate(new Date())
                        .endDate(new Date())
                        .employeeId(1L)
                        .build()
        );

        EmployeeResponse employee = new EmployeeResponse();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");

        Mockito.when(employeeGateway.getEmployee(1L)).thenReturn(employee);
        Mockito.when(paidTimeOffRepository.findByEmployeeId(Mockito.anyLong())).thenReturn(paidTimeOffs);

        List<PaidTimeOffResponse> paidTimeOffResponses = paidTimeOffService.getPaidTimesOffByEmployeeId(1L);

        Assertions.assertEquals(paidTimeOffs.size(), paidTimeOffResponses.size());
        Assertions.assertEquals(paidTimeOffs.get(0).getId(), paidTimeOffResponses.get(0).getId());
        Assertions.assertEquals(paidTimeOffs.get(1).getDetails(), paidTimeOffResponses.get(1).getDetails());
        Assertions.assertEquals(paidTimeOffs.get(0).getStartDate(), paidTimeOffResponses.get(0).getStartDate());
        Assertions.assertEquals(paidTimeOffs.get(1).getEndDate(), paidTimeOffResponses.get(1).getEndDate());
        Assertions.assertEquals(employee, paidTimeOffResponses.get(0).getEmployee());
    }

    @Test
    @DisplayName("Should return all employees with latest paid time off")
    public void shouldReturnAllEmployeesWithLatestPaidTimeOff() {
        List<EmployeeResponse> employees = Arrays.asList(
                new EmployeeResponse(1L, "John", "Doe", "john.doe@example.com"),
                new EmployeeResponse(2L, "Jane", "Smith", "jane.smith@example.com")
        );

        PaidTimeOff latestPaidTimeOffJohn = PaidTimeOff.builder()
                .id(1L)
                .details("Vacation")
                .startDate(new Date())
                .endDate(new Date())
                .employeeId(1L)
                .build();

        PaidTimeOff latestPaidTimeOffJane = PaidTimeOff.builder()
                .id(2L)
                .details("Sick leave")
                .startDate(new Date())
                .endDate(new Date())
                .employeeId(2L)
                .build();

        Mockito.when(employeeGateway.getAllEmployees()).thenReturn(employees);
        Mockito.when(paidTimeOffRepository.findFirstByEmployeeIdOrderByStartDateDesc(1L)).thenReturn(latestPaidTimeOffJohn);
        Mockito.when(paidTimeOffRepository.findFirstByEmployeeIdOrderByStartDateDesc(2L)).thenReturn(latestPaidTimeOffJane);

        Mockito.when(employeeGateway.getEmployee(1L)).thenReturn(employees.get(0));
        Mockito.when(employeeGateway.getEmployee(2L)).thenReturn(employees.get(1));


        EmployeeResponse employeeResponseJohn = new EmployeeResponse(1L, "John", "Doe", "john.doe@example.com");
        PaidTimeOffResponse paidTimeOffResponseJohn = new PaidTimeOffResponse(1L, "Vacation", new Date(), new Date(), employeeResponseJohn);

        EmployeeResponse employeeResponseJane = new EmployeeResponse(2L, "Jane", "Smith", "jane.smith@example.com");
        PaidTimeOffResponse paidTimeOffResponseJane = new PaidTimeOffResponse(2L, "Sick leave", new Date(), new Date(), employeeResponseJane);

        List<PaidTimeOffResponse> expectedPaidTimeOffResponses = Arrays.asList(paidTimeOffResponseJohn, paidTimeOffResponseJane);

        List<PaidTimeOffResponse> actualPaidTimeOffResponses = paidTimeOffService.getAllEmployeesWithLatestPayroll();

        Assertions.assertEquals(expectedPaidTimeOffResponses.size(), actualPaidTimeOffResponses.size());
        Assertions.assertEquals(expectedPaidTimeOffResponses.get(0).getId(), actualPaidTimeOffResponses.get(0).getId());
        Assertions.assertEquals(expectedPaidTimeOffResponses.get(1).getDetails(), actualPaidTimeOffResponses.get(1).getDetails());
        Assertions.assertEquals(expectedPaidTimeOffResponses.get(0).getStartDate().toString(), actualPaidTimeOffResponses.get(0).getStartDate().toString());
        Assertions.assertEquals(expectedPaidTimeOffResponses.get(1).getEndDate().toString(), actualPaidTimeOffResponses.get(1).getEndDate().toString());
        Assertions.assertEquals(expectedPaidTimeOffResponses.get(0).getEmployee(), actualPaidTimeOffResponses.get(0).getEmployee());
        Assertions.assertEquals(expectedPaidTimeOffResponses.get(1).getEmployee(), actualPaidTimeOffResponses.get(1).getEmployee());
    }

    @Test
    @DisplayName("Should delete a paid time off")
    public void shouldDeletePaidTimeOff() {
        Long paidTimeOffId = 1L;
        Mockito.when(paidTimeOffRepository.existsById(paidTimeOffId)).thenReturn(true);

        paidTimeOffService.deletePaidTimeOff(paidTimeOffId);

        Mockito.verify(paidTimeOffRepository).deleteById(paidTimeOffId);
    }
}