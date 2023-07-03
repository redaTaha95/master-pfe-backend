package payslip.domain;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayslipResponse {


    private Long id;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date payslipDate;

    private Double monthlyNetSalary;

    private Double monthlyBasedSalary;

    private Double monthlyHoursWorked;

    private Double  bonusPaiment;

    private Long payrollId;
}

