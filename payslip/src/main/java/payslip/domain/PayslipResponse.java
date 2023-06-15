package payslip.domain;



import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayslipResponse {


    private Long id;

    private Date payslipDate;

    private Double monthlyNetSalary;

    private Double monthlyBasedSalary;

    private Double monthlyHoursWorked;

    private Double  bonusPaiment;

    private Long payrollId;
}

