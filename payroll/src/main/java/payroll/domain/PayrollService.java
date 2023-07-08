package payroll.domain;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import payroll.application.in.PayrollRequest;
import payroll.application.out.http.payroll.EmployeeGateway;
import payroll.application.out.http.payroll.EmployeeResponse;
import payroll.domain.out.PayrollRepository;

import java.util.ArrayList;
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

        if (employee != null)
        {

            Payroll payroll = Payroll.builder()
                    .payrollDate(payrollRequest.getPayrollDate())
                    .monthlyBasedSalary(payrollRequest.getMonthlyBasedSalary())
                    .bonusPaiment(payrollRequest.getBonusPaiment())
                    .monthlyHoursWorked(payrollRequest.getMonthlyHoursWorked())
                    .employeeId(payrollRequest.getEmployeeId())
                    .build();

            Payroll savedPayroll = payrollRepository.save(payroll);

            return  convertToResponse(savedPayroll,employee);
        }
        else {
            throw new EmployeeNotFoundException("employee not found");
        }
    }

    public List<PayrollResponse> getAllPayrolls() {
        List<Payroll> payrolls = payrollRepository.findAll();
        List<PayrollResponse> payrollResponses = new ArrayList<>();

        payrolls.forEach(payroll -> {
             EmployeeResponse employee = employeeGateway.getEmployee(payroll.getEmployeeId());
            PayrollResponse payrollResponse = convertToResponse(payroll, employee);
            payrollResponses.add(payrollResponse);
        });
        return payrollResponses;
    }

    public List<PayrollResponse> getAllEmployeesWithLatestPayroll() {
        List<EmployeeResponse> employees = employeeGateway.getAllEmployees();
        List<PayrollResponse> payrollResponses = new ArrayList<>();

        employees.forEach(employee -> {
            Payroll latestPayroll = payrollRepository.findFirstByEmployeeIdOrderByPayrollDateDesc(employee.getId());
            if (latestPayroll != null) {
                EmployeeResponse employeeResponse = employeeGateway.getEmployee(employee.getId());
                PayrollResponse payrollResponse = convertToResponse(latestPayroll,employeeResponse);
                payrollResponses.add(payrollResponse);
            }
        });

        return payrollResponses;
    }


    public PayrollResponse getPayrollById(Long id) {
        Payroll payroll = getPayrollByIdIfExists(id);
        EmployeeResponse employee = employeeGateway.getEmployee(payroll.getEmployeeId());
        if (employee != null) {
            return convertToResponse(payroll, employee);
        }
        else {
            throw new EmployeeNotFoundException("employee not found");
        }
    }

    public List<PayrollResponse>  getPayrollsByEmployeeId(Long id) {
        List<Payroll> payrolls = payrollRepository.findByEmployeeId(id);
        EmployeeResponse employee = employeeGateway.getEmployee(id);

        return payrolls.stream()
                .map(payroll -> convertToResponse(payroll, employee))
                .collect(Collectors.toList());
    }


    public PayrollResponse updatePayroll(Long id, PayrollRequest payrollRequest) {

        EmployeeResponse employee = employeeGateway.getEmployee(payrollRequest.getEmployeeId());
        if (employee != null)
        {
            Payroll payroll = getPayrollByIdIfExists(id);
            updatePayrollFromRequest(payroll, payrollRequest);
            payrollRepository.save(payroll);
            return convertToResponse(payroll,employee);
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

    }

    private Payroll getPayrollByIdIfExists(Long id) {
        return payrollRepository.findById(id).orElseThrow(() -> new PayrollNotFoundException("Payroll not found"));
    }

    private PayrollResponse convertToResponse(Payroll payroll, EmployeeResponse employeeResponse) {
        return PayrollResponse.builder()
                .id(payroll.getId())
                .monthlyBasedSalary(payroll.getMonthlyBasedSalary())
                .monthlyHoursWorked(payroll.getMonthlyHoursWorked())
                .monthlyNetSalary(getNetSalaryPerMonth(payroll))
                .payrollDate(payroll.getPayrollDate())
                .bonusPaiment(payroll.getBonusPaiment())
                .employee(employeeResponse)
                .build();
    }

    private Double getNetSalaryPerMonth(Payroll payroll)
    {
        Double salaryPerHour = (payroll.getMonthlyBasedSalary() / 24) / 8;
        return (salaryPerHour * payroll.getMonthlyHoursWorked()) + payroll.getBonusPaiment();
    }
}
