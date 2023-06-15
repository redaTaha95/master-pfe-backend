package payroll.application.in;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "monthlyBasedSalary is required")
    private Double monthlyBasedSalary;

    @NotBlank(message = "hoursWorkesMonthly is required")
    private Double monthlyHoursWorked;

    private Double bonusPaiment = 0.0;

    @NotBlank(message = "EmployeeId is required")
    private Long employeeId;
}
