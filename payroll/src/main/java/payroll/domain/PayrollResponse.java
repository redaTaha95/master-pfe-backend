package payroll.domain;

import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;

@Getter
@Setter
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
