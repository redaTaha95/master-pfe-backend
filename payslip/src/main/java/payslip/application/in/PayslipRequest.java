package payslip.application.in;

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
public class PayslipRequest {

    @NotNull(message = "payslipDate is required")
    private Date payslipDate;

    private Double monthlyNetSalary;

    @NotNull(message = "monthlyBasedSalary is required")
    private Double monthlyBasedSalary;

    @NotNull(message = "hoursWorkesMonthly is required")
    private Double monthlyHoursWorked;

    private Double bonusPaiment = 0.0;

    @NotNull(message = "payrollId is required")
    private Long payrollId;
}