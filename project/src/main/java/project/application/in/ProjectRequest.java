package project.application.in;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {
    @NotBlank(message = "project name is required")
    private String name;

    @NotBlank(message = "description is required")
    private String description;

    @NotNull(message = "start date is required")
    private Date startDate;

    @NotNull(message = "end date is required")
    private Date endDate;
}
