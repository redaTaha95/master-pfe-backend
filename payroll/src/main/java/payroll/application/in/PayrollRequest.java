package payroll.application.in;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayrollRequest {
    @NotNull(message = "payrollDate is required")
    private Date payrollDate;

    private Double monthlyNetSalary;

    @NotNull(message = "monthlyBasedSalary is required")
    private Double monthlyBasedSalary;

    @NotNull(message = "hoursWorkesMonthly is required")
    private Double monthlyHoursWorked;

    private Double bonusPaiment = 0.0;

    @NotNull(message = "EmployeeId is required")
    private Long employeeId;
}
