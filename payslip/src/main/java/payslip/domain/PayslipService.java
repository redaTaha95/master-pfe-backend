package payslip.domain;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import payslip.application.in.PayslipRequest;
import payslip.application.out.http.payroll.PayrollGateway;
import payslip.application.out.http.payroll.PayrollResponse;
import payslip.domain.out.PayslipRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayslipService {

    private final PayslipRepository payslipRepository;
    private final PayrollGateway payrollGateway;

    @Transactional
    public PayslipResponse createPayslip(PayslipRequest payslipRequest) {
        PayrollResponse payroll = payrollGateway.getPayroll(payslipRequest.getPayrollId());

        Calendar dateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        Calendar currentDate = Calendar.getInstance();
        dateCalendar.setTimeInMillis(currentDate.getTimeInMillis());
        Date payslipDate = dateCalendar.getTime();
        if (payroll != null)
        {
            Payslip payslip = Payslip.builder()
                    .payslipDate(payslipDate)
                    .bonusPaiment(payroll.getBonusPaiment())
                    .monthlyBasedSalary(payroll.getMonthlyBasedSalary())
                    .monthlyNetSalary(payroll.getMonthlyNetSalary())
                    .monthlyHoursWorked(payroll.getMonthlyHoursWorked())
                    .payrollId(payslipRequest.getPayrollId())
                    .build();

            Payslip savedPayslip = payslipRepository.save(payslip);

            return  convertToResponse(savedPayslip);
        }
        else {
            throw new PayslipNotFoundException("payroll not found");
        }
    }

    public List<PayslipResponse> getAllPayslips() {
        List<Payslip> payslips = payslipRepository.findAll();
        return payslips.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public PayslipResponse getPayslipById(Long id) {
        Payslip payslip = getPayslipByIdIfExists(id);
        return convertToResponse(payslip);
    }

    public List<PayslipResponse>  getPayslipsByPayrollId(Long id) {
            List<Payslip> payslips = payslipRepository.findByPayrollId(id);
            return payslips.stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());
    }

    public PayslipResponse updatePayslip(Long id, PayslipRequest payslipRequest) {

        PayrollResponse payroll = payrollGateway.getPayroll(payslipRequest.getPayrollId());
        if (payroll != null)
        {
            Payslip payslip = getPayslipByIdIfExists(id);
            updatePayslipFromRequest(payslip, payslipRequest,payroll);
            payslipRepository.save(payslip);
            return convertToResponse(payslip);
        }
        else {
            throw new PayslipNotFoundException("Payroll not found");
        }

    }

    public void deletePayslip(Long id) {
        if (!payslipRepository.existsById(id)) {
            throw new PayslipNotFoundException("Payslip not found with id: " + id);
        }
        payslipRepository.deleteById(id);
    }

    private void updatePayslipFromRequest(Payslip payslip, PayslipRequest payslipRequest,PayrollResponse payrollResponse) {
        payslip.setMonthlyBasedSalary(payrollResponse.getMonthlyBasedSalary());
        payslip.setPayslipDate(payslipRequest.getPayslipDate());
        payslip.setPayrollId(payslipRequest.getPayrollId());
        payslip.setMonthlyNetSalary(payrollResponse.getMonthlyNetSalary());
        payslip.setBonusPaiment(payrollResponse.getBonusPaiment());
        payslip.setMonthlyHoursWorked(payrollResponse.getMonthlyHoursWorked());
    }

    private Payslip getPayslipByIdIfExists(Long id) {
        return payslipRepository.findById(id).orElseThrow(() -> new PayslipNotFoundException("Payslip not found"));
    }

    private PayslipResponse convertToResponse(Payslip payslip) {

        return PayslipResponse.builder()
                .id(payslip.getId())
                .payslipDate(payslip.getPayslipDate())
                .bonusPaiment(payslip.getBonusPaiment())
                .monthlyBasedSalary(payslip.getMonthlyBasedSalary())
                .monthlyHoursWorked(payslip.getMonthlyHoursWorked())
                .monthlyNetSalary(payslip.getMonthlyNetSalary())
                .payrollId(payslip.getPayrollId())
                .build();
    }
}
