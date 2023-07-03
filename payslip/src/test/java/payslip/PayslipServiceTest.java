package payslip;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import payslip.application.in.PayslipRequest;
import payslip.application.out.http.payroll.PayrollGateway;
import payslip.application.out.http.payroll.PayrollResponse;
import payslip.domain.Payslip;
import payslip.domain.PayslipResponse;
import payslip.domain.PayslipService;
import payslip.domain.out.PayslipRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PayslipServiceTest {

    @Mock
    private PayslipRepository payslipRepository;

    @Mock
    private PayrollGateway payrollGateway;

    @InjectMocks
    private PayslipService payslipService;

    @Test
    @DisplayName("Should create a payslip")
    public void shouldCreatePayslip() {
        PayslipRequest payslipRequest = new PayslipRequest();
        payslipRequest.setPayslipDate(new Date());
        payslipRequest.setPayrollId(1L);

        PayrollResponse payrollResponse = new PayrollResponse();
        payrollResponse.setMonthlyBasedSalary(2000.0);
        payrollResponse.setBonusPaiment(500.0);
        payrollResponse.setMonthlyHoursWorked(160.0);
        payrollResponse.setMonthlyNetSalary(2500.0);

        Payslip savedPayslip = new Payslip();
        savedPayslip.setId(1L);
        savedPayslip.setPayslipDate(payslipRequest.getPayslipDate());
        savedPayslip.setPayrollId(payslipRequest.getPayrollId());
        savedPayslip.setMonthlyBasedSalary(payrollResponse.getMonthlyBasedSalary());
        savedPayslip.setBonusPaiment(payrollResponse.getBonusPaiment());
        savedPayslip.setMonthlyHoursWorked(payrollResponse.getMonthlyHoursWorked());
        savedPayslip.setMonthlyNetSalary(payrollResponse.getMonthlyNetSalary());

        Mockito.when(payrollGateway.getPayroll(1L)).thenReturn(payrollResponse);
        Mockito.when(payslipRepository.save(Mockito.any(Payslip.class))).thenReturn(savedPayslip);

        PayslipResponse payslipResponse = payslipService.createPayslip(payslipRequest);

        Assertions.assertEquals(savedPayslip.getId(), payslipResponse.getId());
        Assertions.assertEquals(payslipRequest.getPayslipDate(), payslipResponse.getPayslipDate());
        Assertions.assertEquals(payrollResponse.getMonthlyBasedSalary(), payslipResponse.getMonthlyBasedSalary());
        Assertions.assertEquals(payrollResponse.getBonusPaiment(), payslipResponse.getBonusPaiment());
        Assertions.assertEquals(payrollResponse.getMonthlyHoursWorked(), payslipResponse.getMonthlyHoursWorked());
        Assertions.assertEquals(payrollResponse.getMonthlyNetSalary(), payslipResponse.getMonthlyNetSalary());
        Assertions.assertEquals(payslipRequest.getPayrollId(), payslipResponse.getPayrollId());
    }

    @Test
    @DisplayName("Should get all payslips")
    public void shouldGetAllPayslips() {
        List<Payslip> payslips = Arrays.asList(
                createPayslip(1L, new Date(), 2000.0, 500.0, 160.0, 2500.0, 1L),
                createPayslip(2L, new Date(), 2500.0, 600.0, 180.0, 3000.0, 1L)
        );

        Mockito.when(payslipRepository.findAll()).thenReturn(payslips);

        List<PayslipResponse> payslipResponses = payslipService.getAllPayslips();

        Assertions.assertEquals(payslips.size(), payslipResponses.size());
        Assertions.assertEquals(payslips.get(0).getId(), payslipResponses.get(0).getId());
        Assertions.assertEquals(payslips.get(1).getPayslipDate(), payslipResponses.get(1).getPayslipDate());
        Assertions.assertEquals(payslips.get(0).getMonthlyBasedSalary(), payslipResponses.get(0).getMonthlyBasedSalary());
        Assertions.assertEquals(payslips.get(1).getBonusPaiment(), payslipResponses.get(1).getBonusPaiment());
        Assertions.assertEquals(payslips.get(0).getMonthlyHoursWorked(), payslipResponses.get(0).getMonthlyHoursWorked());
        Assertions.assertEquals(payslips.get(1).getMonthlyNetSalary(), payslipResponses.get(1).getMonthlyNetSalary());
        Assertions.assertEquals(payslips.get(0).getPayrollId(), payslipResponses.get(0).getPayrollId());
    }

    @Test
    @DisplayName("Should get payslip by ID")
    public void shouldGetPayslipById() {
        Long payslipId = 1L;
        Payslip payslip = createPayslip(payslipId, new Date(), 2000.0, 500.0, 160.0, 2500.0, 1L);

        Mockito.when(payslipRepository.findById(payslipId)).thenReturn(Optional.of(payslip));

        PayslipResponse payslipResponse = payslipService.getPayslipById(payslipId);

        Assertions.assertEquals(payslip.getId(), payslipResponse.getId());
        Assertions.assertEquals(payslip.getPayslipDate(), payslipResponse.getPayslipDate());
        Assertions.assertEquals(payslip.getMonthlyBasedSalary(), payslipResponse.getMonthlyBasedSalary());
        Assertions.assertEquals(payslip.getBonusPaiment(), payslipResponse.getBonusPaiment());
        Assertions.assertEquals(payslip.getMonthlyHoursWorked(), payslipResponse.getMonthlyHoursWorked());
        Assertions.assertEquals(payslip.getMonthlyNetSalary(), payslipResponse.getMonthlyNetSalary());
        Assertions.assertEquals(payslip.getPayrollId(), payslipResponse.getPayrollId());
    }

    @Test
    @DisplayName("Should get payslips by payroll ID")
    public void shouldGetPayslipsByPayrollId() {
        Long payrollId = 1L;
        List<Payslip> payslips = Arrays.asList(
                createPayslip(1L, new Date(), 2000.0, 500.0, 160.0, 2500.0, payrollId),
                createPayslip(2L, new Date(), 2500.0, 600.0, 180.0, 3000.0, payrollId)
        );

        Mockito.when(payslipRepository.findByPayrollId(payrollId)).thenReturn(payslips);

        List<PayslipResponse> payslipResponses = payslipService.getPayslipsByPayrollId(payrollId);

        Assertions.assertEquals(payslips.size(), payslipResponses.size());
        Assertions.assertEquals(payslips.get(0).getId(), payslipResponses.get(0).getId());
        Assertions.assertEquals(payslips.get(1).getPayslipDate(), payslipResponses.get(1).getPayslipDate());
        Assertions.assertEquals(payslips.get(0).getMonthlyBasedSalary(), payslipResponses.get(0).getMonthlyBasedSalary());
        Assertions.assertEquals(payslips.get(1).getBonusPaiment(), payslipResponses.get(1).getBonusPaiment());
        Assertions.assertEquals(payslips.get(0).getMonthlyHoursWorked(), payslipResponses.get(0).getMonthlyHoursWorked());
        Assertions.assertEquals(payslips.get(1).getMonthlyNetSalary(), payslipResponses.get(1).getMonthlyNetSalary());
        Assertions.assertEquals(payslips.get(0).getPayrollId(), payslipResponses.get(0).getPayrollId());
    }

    @Test
    @DisplayName("Should update a payslip")
    public void shouldUpdatePayslip() {
        Long payslipId = 1L;
        Long payrollId = 1L;
        Payslip payslip = createPayslip(payslipId, new Date(), 2000.0, 500.0, 160.0, 2500.0, payrollId);
        PayslipRequest payslipRequest = new PayslipRequest();
        payslipRequest.setPayslipDate(new Date());
        payslipRequest.setPayrollId(payrollId);

        PayrollResponse payrollResponse = new PayrollResponse();
        payrollResponse.setMonthlyBasedSalary(2500.0);
        payrollResponse.setBonusPaiment(600.0);
        payrollResponse.setMonthlyHoursWorked(180.0);
        payrollResponse.setMonthlyNetSalary(3000.0);

        Mockito.when(payrollGateway.getPayroll(payslipRequest.getPayrollId())).thenReturn(payrollResponse);
        Mockito.when(payslipRepository.findById(payslipId)).thenReturn(Optional.of(payslip));

        PayslipResponse updatedPayslipResponse = payslipService.updatePayslip(payslipId, payslipRequest);

        Assertions.assertEquals(payslipId, updatedPayslipResponse.getId());
        Assertions.assertEquals(payslipRequest.getPayslipDate(), updatedPayslipResponse.getPayslipDate());
        Assertions.assertEquals(payrollResponse.getMonthlyBasedSalary(), updatedPayslipResponse.getMonthlyBasedSalary());
        Assertions.assertEquals(payrollResponse.getBonusPaiment(), updatedPayslipResponse.getBonusPaiment());
        Assertions.assertEquals(payrollResponse.getMonthlyHoursWorked(), updatedPayslipResponse.getMonthlyHoursWorked());
        Assertions.assertEquals(payrollResponse.getMonthlyNetSalary(), updatedPayslipResponse.getMonthlyNetSalary());
        Assertions.assertEquals(payslipRequest.getPayrollId(), updatedPayslipResponse.getPayrollId());
    }

    @Test
    @DisplayName("Should delete a payslip")
    public void shouldDeletePayslip() {
        Long payslipId = 1L;
        Mockito.when(payslipRepository.existsById(payslipId)).thenReturn(true);

        payslipService.deletePayslip(payslipId);

        Mockito.verify(payslipRepository).deleteById(payslipId);
    }

    private Payslip createPayslip(Long id, Date payslipDate, Double monthlyBasedSalary, Double bonusPaiment, Double monthlyHoursWorked, Double monthlyNetSalary, Long payrollId) {
        return Payslip.builder()
                .id(id)
                .payslipDate(payslipDate)
                .monthlyBasedSalary(monthlyBasedSalary)
                .bonusPaiment(bonusPaiment)
                .monthlyHoursWorked(monthlyHoursWorked)
                .monthlyNetSalary(monthlyNetSalary)
                .payrollId(payrollId)
                .build();
    }
}