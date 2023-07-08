package paidTimeOff.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import paidTimeOff.application.out.http.pto.EmployeeResponse;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaidTimeOffResponse {
    private Long id;

    private String details;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private EmployeeResponse employee;
}
