package task.domain;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import task.application.out.http.employee.EmployeeResponse;

import java.util.Date;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    private Long id;


    private String name;


    private String description;


    private Long projectId;


    private TaskStatus status;


    private EmployeeResponse employee;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;
}
