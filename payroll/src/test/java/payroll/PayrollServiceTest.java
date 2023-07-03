package payroll;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import payroll.application.in.PayrollRequest;
import payroll.application.out.http.payroll.EmployeeGateway;
import payroll.application.out.http.payroll.EmployeeResponse;
import payroll.domain.Payroll;
import payroll.domain.PayrollResponse;
import payroll.domain.PayrollService;
import payroll.domain.out.PayrollRepository;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class PayrollServiceTest {
    @Mock
    private PayrollRepository payrollRepository;
    @Mock
    private EmployeeGateway employeeGateway;

    @InjectMocks
    private PayrollService payrollService;

    @Test
    @DisplayName("Should create a payroll")
    public void shouldCreatePayroll() {
        PayrollRequest payrollRequest = new PayrollRequest();
        payrollRequest.setPayrollDate(new Date());
        payrollRequest.setMonthlyBasedSalary(2000.0);
        payrollRequest.setBonusPaiment(500.0);
        payrollRequest.setMonthlyHoursWorked(160.0);
        payrollRequest.setEmployeeId(1L);

        EmployeeResponse employee = new EmployeeResponse();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");

        Payroll savedPayroll = Payroll.builder()
                .id(1L)
                .payrollDate(payrollRequest.getPayrollDate())
                .monthlyBasedSalary(payrollRequest.getMonthlyBasedSalary())
                .bonusPaiment(payrollRequest.getBonusPaiment())
                .monthlyHoursWorked(payrollRequest.getMonthlyHoursWorked())
                .employeeId(payrollRequest.getEmployeeId())
                .build();

        Mockito.when(employeeGateway.getEmployee(1L)).thenReturn(employee);
        Mockito.when(payrollRepository.save(Mockito.any(Payroll.class))).thenReturn(savedPayroll);

        PayrollResponse payrollResponse = payrollService.createPayroll(payrollRequest);

        Assertions.assertEquals(savedPayroll.getId(), payrollResponse.getId());
        Assertions.assertEquals(payrollRequest.getPayrollDate(), payrollResponse.getPayrollDate());
        Assertions.assertEquals(payrollRequest.getMonthlyBasedSalary(), payrollResponse.getMonthlyBasedSalary());
        Assertions.assertEquals(payrollRequest.getBonusPaiment(), payrollResponse.getBonusPaiment());
        Assertions.assertEquals(payrollRequest.getMonthlyHoursWorked(), payrollResponse.getMonthlyHoursWorked());
        Assertions.assertEquals(employee, payrollResponse.getEmployee());
    }

    @Test
    @DisplayName("Should return all payrolls")
    public void shouldReturnAllPayrolls() {
        List<Payroll> payrolls = Arrays.asList(
                new Payroll(1L, new Date(), 2000.0, 500.0, 160.0, 1L),
                new Payroll(2L, new Date(), 2500.0, 300.0, 180.0, 1L)
        );

        EmployeeResponse employee = new EmployeeResponse();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");


        Mockito.when(payrollRepository.findAll()).thenReturn(payrolls);
        Mockito.when(employeeGateway.getEmployee(Mockito.anyLong())).thenReturn(employee);

        List<PayrollResponse> payrollResponses = payrollService.getAllPayrolls();

        Assertions.assertEquals(payrolls.size(), payrollResponses.size());
        Assertions.assertEquals(payrolls.get(0).getId(), payrollResponses.get(0).getId());
        Assertions.assertEquals(payrolls.get(1).getMonthlyBasedSalary(), payrollResponses.get(1).getMonthlyBasedSalary());
    }

    @Test
    @DisplayName("Should return payroll by ID")
    public void shouldReturnPayrollById() {
        Long payrollId = 1L;
        Payroll payroll = new Payroll(payrollId, new Date(), 2000.0, 500.0, 160.0, 1L);

        EmployeeResponse employee = new EmployeeResponse();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");

        Mockito.when(payrollRepository.findById(payrollId)).thenReturn(Optional.of(payroll));
        Mockito.when(employeeGateway.getEmployee(Mockito.anyLong())).thenReturn(employee);

        PayrollResponse payrollResponse = payrollService.getPayrollById(payrollId);

        Assertions.assertEquals(payroll.getId(), payrollResponse.getId());
        Assertions.assertEquals(payroll.getMonthlyBasedSalary(), payrollResponse.getMonthlyBasedSalary());
        Assertions.assertEquals(payroll.getEmployeeId(), payrollResponse.getEmployee().getId());
    }

    @Test
    @DisplayName("Should return all employees with their latest payroll")
    public void shouldReturnAllEmployeesWithLatestPayroll() {
        List<EmployeeResponse> employees = Arrays.asList(
                new EmployeeResponse(1L, "John", "Doe", "john.doe@example.com"),
                new EmployeeResponse(2L, "Jane", "Smith", "jane.smith@example.com")
        );

        List<Payroll> payrolls = Arrays.asList(
                new Payroll(1L, new Date(), 2000.0, 500.0, 160.0, 1L),
                new Payroll(2L, new Date(), 2500.0, 300.0, 180.0, 1L)
        );

        Mockito.when(employeeGateway.getAllEmployees()).thenReturn(employees);


        Mockito.when(payrollRepository.findFirstByEmployeeIdOrderByPayrollDateDesc(1L))
                .thenReturn(payrolls.get(0));
        Mockito.when(payrollRepository.findFirstByEmployeeIdOrderByPayrollDateDesc(2L))
                .thenReturn(payrolls.get(1)); // Latest payroll for employee with ID 2

        Mockito.when(employeeGateway.getEmployee(1L)).thenReturn(employees.get(0));
        Mockito.when(employeeGateway.getEmployee(2L)).thenReturn(employees.get(1));

        List<PayrollResponse> payrollResponses = payrollService.getAllEmployeesWithLatestPayroll();

        Assertions.assertEquals(2, payrollResponses.size());

        PayrollResponse johnPayrollResponse = payrollResponses.get(0);
        Assertions.assertEquals(1L, johnPayrollResponse.getEmployee().getId());
        Assertions.assertEquals("John", johnPayrollResponse.getEmployee().getFirstName());
        Assertions.assertEquals("Doe", johnPayrollResponse.getEmployee().getLastName());

        PayrollResponse janePayrollResponse = payrollResponses.get(1);
        Assertions.assertEquals(2L, janePayrollResponse.getEmployee().getId());
        Assertions.assertEquals("Jane", janePayrollResponse.getEmployee().getFirstName());
        Assertions.assertEquals("Smith", janePayrollResponse.getEmployee().getLastName());
    }

    @Test
    @DisplayName("Should return payroll by ID")
    public void shouldReturnPayrollByEmployeeId() {
        List<Payroll> payrolls = Arrays.asList(
                new Payroll(1L, new Date(), 2000.0, 500.0, 160.0, 1L),
                new Payroll(2L, new Date(), 2500.0, 300.0, 180.0, 1L)
        );



        Mockito.when(payrollRepository.findByEmployeeId(Mockito.anyLong())).thenReturn(payrolls);
        List<PayrollResponse> payrollResponses = payrollService.getPayrollsByEmployeeId(1L);
        System.out.println(payrollResponses);
        Assertions.assertEquals(payrolls.size(), payrollResponses.size());
        Assertions.assertEquals(payrolls.get(0).getId(), payrollResponses.get(0).getId());
        Assertions.assertEquals(payrolls.get(1).getMonthlyBasedSalary(), payrollResponses.get(1).getMonthlyBasedSalary());

    }

    @Test
    @DisplayName("Should update payroll")
    public void shouldUpdatePayroll() {
        Long payrollId = 1L;
        PayrollRequest payrollRequest = new PayrollRequest();
        payrollRequest.setPayrollDate(new Date());
        payrollRequest.setMonthlyBasedSalary(2000.0);
        payrollRequest.setBonusPaiment(500.0);
        payrollRequest.setMonthlyHoursWorked(160.0);
        payrollRequest.setEmployeeId(1L);

        Payroll existingPayroll = new Payroll(payrollId, new Date(), 1500.0, 300.0, 140.0, 1L);

        EmployeeResponse employee = new EmployeeResponse();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");

        Mockito.when(payrollRepository.findById(payrollId)).thenReturn(Optional.of(existingPayroll));
        Mockito.when(employeeGateway.getEmployee(payrollRequest.getEmployeeId())).thenReturn(employee);
        Mockito.when(payrollRepository.save(Mockito.any(Payroll.class))).thenReturn(existingPayroll);

        PayrollResponse updatedPayrollResponse = payrollService.updatePayroll(payrollId, payrollRequest);

        Assertions.assertEquals(existingPayroll.getId(), updatedPayrollResponse.getId());
        Assertions.assertEquals(payrollRequest.getMonthlyBasedSalary(), updatedPayrollResponse.getMonthlyBasedSalary());
        Assertions.assertEquals(existingPayroll.getEmployeeId(), updatedPayrollResponse.getEmployee().getId());
    }

    @Test
    @DisplayName("Should delete payroll")
    public void shouldDeletePayroll() {
        Long payrollId = 1L;
        Mockito.when(payrollRepository.existsById(payrollId)).thenReturn(true);

        payrollService.deletePayroll(payrollId);

        Mockito.verify(payrollRepository).deleteById(payrollId);
    }
}