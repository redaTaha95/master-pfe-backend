package payroll.domain;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import payroll.application.in.PayrollRequest;
import payroll.application.out.http.payroll.EmployeeGateway;
import payroll.application.out.http.payroll.EmployeeResponse;
import payroll.domain.out.PayrollRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final EmployeeGateway employeeGateway;

    @Transactional
    public PayrollResponse createPayroll(PayrollRequest payrollRequest) {
        EmployeeResponse employee = employeeGateway.getEmployee(payrollRequest.getEmployeeId());

        Double salaryPerHour ;
        Double net_salary_monthly ;

        if (employee != null)
        {
            salaryPerHour = (payrollRequest.getMonthlyBasedSalary() / 24) / 8;
            net_salary_monthly = (salaryPerHour * payrollRequest.getMonthlyHoursWorked()) + payrollRequest.getBonusPaiment();

            Payroll payroll = Payroll.builder()
                    .payrollDate(payrollRequest.getPayrollDate())
                    .monthlyBasedSalary(payrollRequest.getMonthlyBasedSalary())
                    .bonusPaiment(payrollRequest.getBonusPaiment())
                    .monthlyHoursWorked(payrollRequest.getMonthlyHoursWorked())
                    .monthlyNetSalary(net_salary_monthly)
                    .employeeId(payrollRequest.getEmployeeId())
                    .build();

            Payroll savedPayroll = payrollRepository.save(payroll);

            return  convertToResponse(savedPayroll);
        }
        else {
            throw new EmployeeNotFoundException("employee not found");
        }
    }

    public List<PayrollResponse> getAllPayrolls() {
        List<Payroll> payrolls = payrollRepository.findAll();
        return payrolls.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public PayrollResponse getPayrollById(Long id) {
        Payroll payroll = getPayrollByIdIfExists(id);
        return convertToResponse(payroll);
    }

    public List<PayrollResponse>  getPayrollsByEmployeeId(Long id) {
        List<Payroll> tasks = payrollRepository.findByEmployeeId(id);
        return tasks.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public PayrollResponse updatePayroll(Long id, PayrollRequest payrollRequest) {

        EmployeeResponse employee = employeeGateway.getEmployee(payrollRequest.getEmployeeId());
        if (employee != null)
        {
            Payroll payroll = getPayrollByIdIfExists(id);
            updatePayrollFromRequest(payroll, payrollRequest);
            payrollRepository.save(payroll);
            return convertToResponse(payroll);
        }
        else {
            throw new EmployeeNotFoundException("Employee not found");
        }

    }

    public void deletePayroll(Long id) {
        if (!payrollRepository.existsById(id)) {
            throw new PayrollNotFoundException("Payroll not found with id: " + id);
        }
        payrollRepository.deleteById(id);
    }


    private void updatePayrollFromRequest(Payroll payroll, PayrollRequest payrollRequest) {
        Double salaryPerHour = (payrollRequest.getMonthlyBasedSalary() / 24) / 8;
        Double net_salary_monthly = (salaryPerHour * payrollRequest.getMonthlyHoursWorked()) + payrollRequest.getBonusPaiment();

        payroll.setMonthlyBasedSalary(payrollRequest.getMonthlyBasedSalary());
        payroll.setBonusPaiment(payrollRequest.getBonusPaiment());
        payroll.setPayrollDate(payrollRequest.getPayrollDate());
        payroll.setMonthlyHoursWorked(payrollRequest.getMonthlyHoursWorked());
        payroll.setEmployeeId(payrollRequest.getEmployeeId());
        payroll.setMonthlyNetSalary(net_salary_monthly);

    }

    private Payroll getPayrollByIdIfExists(Long id) {
        return payrollRepository.findById(id).orElseThrow(() -> new PayrollNotFoundException("Payroll not found"));
    }

    private PayrollResponse convertToResponse(Payroll payroll) {
        return PayrollResponse.builder()
                .id(payroll.getId())
                .monthlyBasedSalary(payroll.getMonthlyBasedSalary())
                .monthlyHoursWorked(payroll.getMonthlyHoursWorked())
                .monthlyNetSalary(payroll.getMonthlyNetSalary())
                .payrollDate(payroll.getPayrollDate())
                .bonusPaiment(payroll.getBonusPaiment())
                .employeeId(payroll.getEmployeeId())
                .build();
    }
}
