package payslip.application.out.http.payroll;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayrollResponse {
    private Long id;

    private Date payrollDate;

    private Double monthlyNetSalary;

    private Double monthlyBasedSalary;

    private Double monthlyHoursWorked;

    private Double  bonusPaiment;

    private Long employeeId;
}
