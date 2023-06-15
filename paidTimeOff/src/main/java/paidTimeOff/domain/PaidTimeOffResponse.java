package paidTimeOff.domain;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaidTimeOffResponse {
    private Long id;

    private String details;

    private Date startDate;

    private Date endDate;

    private Long employeeId;
}
