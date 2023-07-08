package payslip.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payslip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date payslipDate;

    @Column(nullable = false)
    private Double monthlyBasedSalary;

    private Double  bonusPaiment;

    @Column(nullable = false)
    private Double monthlyHoursWorked;

    @Column(nullable = false)
    private Long payrollId;

    private Double monthlyNetSalary;
}