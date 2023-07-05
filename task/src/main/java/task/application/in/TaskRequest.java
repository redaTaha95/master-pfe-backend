package task.application.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import task.domain.TaskStatus;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {


    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "description is required")
    private String description;

    @NotNull(message = "projectId is required")
    private Long projectId;

    @NotNull(message = "projectId is required")
    private TaskStatus status;

    @NotNull(message = "startDate is required")
    private Date startDate;

    @NotNull(message = "endDate is required")
    private Date endDate;
}
