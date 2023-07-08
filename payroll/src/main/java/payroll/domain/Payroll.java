package payroll.domain;


import jakarta.persistence.*;
import lombok.*;
import java.text.DecimalFormat;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date payrollDate;

    @Column(nullable = false)
    private Double monthlyBasedSalary;

    private Double  bonusPaiment;

    @Column(nullable = false)
    private Double monthlyHoursWorked;

    @Column(nullable = false)
    private Long employeeId;

}
