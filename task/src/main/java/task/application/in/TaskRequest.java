package task.application.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "projectId is required")
    private Long projectId;

    @NotNull(message = "startDate is required")
    private Date startDate;

    @NotNull(message = "endDate is required")
    private Date endDate;
}
