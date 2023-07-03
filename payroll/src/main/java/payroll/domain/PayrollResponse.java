package payroll.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import lombok.*;
import payroll.application.out.http.payroll.EmployeeResponse;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayrollResponse {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date payrollDate;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private Double monthlyNetSalary;

    private Double monthlyBasedSalary;

    private Double monthlyHoursWorked;

    private Double  bonusPaiment;

    private EmployeeResponse employee;
}
