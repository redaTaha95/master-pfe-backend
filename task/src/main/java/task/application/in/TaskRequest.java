package task.application.in;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {


    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "projectId is required")
    private Long projectId;

    @NotBlank(message = "startDate is required")
    private Date startDate;

    @NotBlank(message = "endDate is required")
    private Date endDate;
}
